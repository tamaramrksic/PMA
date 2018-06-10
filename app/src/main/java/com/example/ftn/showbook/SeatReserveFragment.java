package com.example.ftn.showbook;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ftn.showbook.model.SeatAvailability;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeatReserveFragment extends Fragment {

    private Bundle args;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_seat_reserve, container, false);

        args = this.getArguments();

        ((TextView)rootView.findViewById(R.id.show_name_heading)).setText(args.getString("showName"));
        ((TextView)rootView.findViewById(R.id.date_value)).setText(args.getString("date"));
        ((TextView)rootView.findViewById(R.id.time_value)).setText(args.getString("time"));
        ((TextView)rootView.findViewById(R.id.hall_value)).setText(args.getString("facilityHallName"));

        loadEventSeats(rootView);
        return rootView;
    }

    public void loadEventSeats(final View rootView) {
        Call<List<SeatAvailability>> call = ServiceUtils.pmaService.getEventSeats(args.getLong("showId"), args.getLong("facilityId"),
                args.getString("facilityHallName"), args.getString("date"), args.getString("time"));
        call.enqueue(new Callback<List<SeatAvailability>>() {
            @Override
            public void onResponse(Call<List<SeatAvailability>> call, Response<List<SeatAvailability>> response) {
                Long[] numbers = new Long[response.body().size()];
                for (int i=0; i < response.body().size(); i++) {
                    numbers[i] = response.body().get(i).getId();
                }

                final ImageAdapter adapter = new ImageAdapter(getContext(), numbers, response.body());
                GridView gridview = rootView.findViewById(R.id.gridview);

                if (response.body().size() % 3 == 0) {
                    gridview.setNumColumns(response.body().size() / 3);
                } else if (response.body().size() % 4 == 0) {
                    gridview.setNumColumns(response.body().size() / 4);
                } else if (response.body().size() % 5 == 0) {
                    gridview.setNumColumns(response.body().size() / 5);
                }

                gridview.setAdapter(adapter);


                gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View v,
                                            int position, long id) {

                        int selectedIndex = adapter.selectedPositions.indexOf(position);
                        if (selectedIndex > -1) {
                            adapter.selectedPositions.remove(selectedIndex);
                            //((ImageView)v).display(false);
                            (v).setBackgroundColor( Color.WHITE);
                        } else {
                            adapter.selectedPositions.add(position);
                            //((ImageView)v).display(true);

                            (v).setBackgroundColor( Color.GRAY);
                        }

                    }
                });
            }

            @Override
            public void onFailure(Call<List<SeatAvailability>> call, Throwable t) {
                Toast.makeText(getActivity(),getResources().getString(R.string.hall_seats_failure_message), Toast.LENGTH_LONG).show();
            }
        });
    }

}
