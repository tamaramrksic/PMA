package com.example.ftn.showbook;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;



public class Tab1Repertoire extends Fragment {

    String[] movies = {"Osvetnici", "Zeka Rodzer", "Hari Poter"};
    Integer[] imgid={
            R.drawable.avengers,
            R.drawable.peter_rabbit,
            R.drawable.hari
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1repertoire, container, false);
        ListView yourListView = (ListView)rootView.findViewById(R.id.tab1_list);
        ListAdapter listadapter = new CustomListAdapter(getActivity(), movies, imgid);
        yourListView.setAdapter(listadapter);
        return rootView;
    }
}