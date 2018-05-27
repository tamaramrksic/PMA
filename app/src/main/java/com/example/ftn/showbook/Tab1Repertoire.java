package com.example.ftn.showbook;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class Tab1Repertoire extends Fragment {

    String[] movies = {"Osvetnici", "Zeka Rodzer", "Hari Poter"};
    Integer[] imgid={
            R.drawable.avengers,
            R.drawable.peter_rabbit,
            R.drawable.hari
    };
    String[] durations = {"186", "93", "124"};
    String[] ratings = {"4.5", "5", "4.8"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1repertoire, container, false);
        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.tab1_recycler_view);

        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        //add divider between items
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                ((LinearLayoutManager) mLayoutManager).getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        // set adapter
//        RecyclerView.Adapter mAdapter = new ShowListAdapter(getContext(), movies, imgid, durations, ratings);
//        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }
}