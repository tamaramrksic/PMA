package com.example.ftn.showbook;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Tab2Reserved  extends Fragment {

    String[] movies = {"Osvetnici", "Hari Poter"};
    Integer[] imgid={
            R.drawable.avengers,
            R.drawable.hari
    };
    String[] dates = {"20.04.2018", "22.04.2018"};
    String[] times = {"20:00", "21:00"};
    String[] ratings = {"5", "4.8"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2reserved, container, false);

        RecyclerView mRecyclerView = rootView.findViewById(R.id.tab2_recycler_view);

        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        //add divider between items
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                ((LinearLayoutManager) mLayoutManager).getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        // set adapter
        RecyclerView.Adapter mAdapter = new ReservationListAdapter(getActivity(), movies, imgid, dates, times, ratings);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }
}
