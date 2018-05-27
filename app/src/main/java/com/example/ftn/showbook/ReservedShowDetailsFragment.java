package com.example.ftn.showbook;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ReservedShowDetailsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reserved_show_details, container, false);
        Bundle args = this.getArguments();
        ((TextView)rootView.findViewById(R.id.show_name_heading)).setText(args.getString("showName"));
        ((TextView)rootView.findViewById(R.id.show_duration_value)).setText(args.getString("showDuration"));
        ((TextView)rootView.findViewById(R.id.date_value)).setText(args.getString("date"));
        ((TextView)rootView.findViewById(R.id.time_value)).setText(args.getString("time"));
        ((TextView)rootView.findViewById(R.id.hall_value)).setText(args.getString("facilityHall"));
        ((TextView)rootView.findViewById(R.id.facility_value)).setText(args.getString("facility"));
        ((TextView)rootView.findViewById(R.id.num_of_seats_value)).setText(args.getString("numOfTickets"));
        ((TextView)rootView.findViewById(R.id.total_price_value)).setText(args.getString("totalPrice") + " rsd");

        return rootView;
    }
}
