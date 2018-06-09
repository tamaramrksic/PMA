package com.example.ftn.showbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimetableFragment extends Fragment {

    private Button nextButton;
    private Bundle srfArgs;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_timetable, container, false);
        nextButton = rootView.findViewById(R.id.timetable_button);
        srfArgs = new Bundle();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SeatReserveFragment fragment = new SeatReserveFragment();
                fragment.setArguments(srfArgs);

                ((AppCompatActivity)getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        Bundle args = this.getArguments();
        ((TextView)rootView.findViewById(R.id.timetable_title)).setText(args.getString("showName"));


        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        String date1 = sdf.format(calendar.getTime());
        calendar.add(Calendar.DATE, 1);
        String date2 = sdf.format(calendar.getTime());
        calendar.add(Calendar.DATE, 1);
        String date3 = sdf.format(calendar.getTime());
        calendar.add(Calendar.DATE, 1);
        String date4 = sdf.format(calendar.getTime());
        calendar.add(Calendar.DATE, 1);
        String date5 = sdf.format(calendar.getTime());


        String [] values = {date1, date2, date3, date4, date5 };
        final Spinner eventDates = rootView.findViewById(R.id.timetable_date);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        eventDates.setAdapter(adapter);

        Intent intent = getActivity().getIntent();
        final Long showId = args.getLong("showId");
        final Long facilityId = Long.parseLong(intent.getStringExtra("FacilityId"));
        srfArgs.putLong("showId", showId);
        srfArgs.putLong("facilityId", facilityId);
        srfArgs.putString("showName", args.getString("showName"));

        eventDates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                loadTimes(eventDates.getSelectedItem().toString(), showId, facilityId, rootView);
                srfArgs.putString("date",eventDates.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });

        final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if( actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(false);
        }

        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(getActivity());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadTimes(final String date, final Long showId, final Long facilityId, final View rootView) {
        Call<List<String>> call = ServiceUtils.pmaService.getEventTimes(showId, facilityId, date);
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                final Spinner eventTimes= rootView.findViewById(R.id.timetable_time);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, response.body());
                eventTimes.setAdapter(adapter);

                if(eventTimes.getSelectedItem() == null) {
                    final Spinner eventHalls = rootView.findViewById(R.id.timetable_hall);
                    eventHalls.setAdapter(null);
                    nextButton.setEnabled(false);
                } else {
                    nextButton.setEnabled(true);
                }

                eventTimes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        loadFacilityHalls(eventTimes.getSelectedItem().toString(), date, showId, facilityId, rootView);
                        srfArgs.putString("time", eventTimes.getSelectedItem().toString());

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                    }
                });
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Toast.makeText(getActivity(),getResources().getString(R.string.timetable_times_failure_message), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void loadFacilityHalls(String time, final String date, final Long showId, final Long facilityId, final View rootView) {
        Call<List<String>> call = ServiceUtils.pmaService.getEventHalls(showId, facilityId, date, time);
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                final Spinner eventHalls= rootView.findViewById(R.id.timetable_hall);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, response.body());
                eventHalls.setAdapter(adapter);

                if(eventHalls.getSelectedItem() == null) {
                    nextButton.setEnabled(false);
                } else {
                    srfArgs.putString("facilityHallName", eventHalls.getSelectedItem().toString());
                    nextButton.setEnabled(true);
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Toast.makeText(getActivity(),getResources().getString(R.string.timetable_halls_failure_message), Toast.LENGTH_LONG).show();
            }
        });
    }

}
