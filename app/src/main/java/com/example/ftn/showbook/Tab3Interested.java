package com.example.ftn.showbook;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class Tab3Interested extends Fragment {

    String[] movies = {"Kapetan Amerika", "Zec Petar"};
    Integer[] imgid={
            R.drawable.avengers,
            R.drawable.peter_rabbit
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3interested, container, false);
        ListView yourListView = rootView.findViewById(R.id.tab3_list);
        ListAdapter listadapter = new CustomListAdapter(getActivity(), movies, imgid);
        yourListView.setAdapter(listadapter);
        return rootView;
    }

}
