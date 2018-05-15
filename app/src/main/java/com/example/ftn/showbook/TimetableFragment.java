package com.example.ftn.showbook;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TimetableFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_timetable, container, false);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String currentDate = sdf.format(Calendar.getInstance().getTime());

        TextView col1 = rootView.findViewById(R.id.firstColumn);
        col1.setText(currentDate);
        TextView col2 = rootView.findViewById(R.id.secondColumn);
        col2.setText("15-04-2018");
        TextView col3 = rootView.findViewById(R.id.thirdColumn);
        col3.setText("16-04-2018");
        TextView col4 = rootView.findViewById(R.id.fourthColumn);
        col4.setText("17-04-2018");
        TextView col5 = rootView.findViewById(R.id.fifthColumn);
        col5.setText("18-04-2018");

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
}
