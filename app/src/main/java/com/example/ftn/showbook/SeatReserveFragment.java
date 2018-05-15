package com.example.ftn.showbook;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

public class SeatReserveFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_seat_reserve, container, false);


        GridView gridview = rootView.findViewById(R.id.gridview);
        final ImageAdapter adapter = new ImageAdapter(getContext());
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
        return rootView;
    }

}
