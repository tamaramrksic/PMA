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

    String[] movies = {"Osvetnici", "Zeka Rodzer"};
    Integer[] imgid={
            R.drawable.avengers,
            R.drawable.peter_rabbit
    };
    String[] durations = {"186", "93"};
    String[] ratings = {"4.5", "5"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2reserved, container, false);

        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.tab2_recycler_view);
        //ListAdapter listadapter = new ShowListAdapter(getActivity(), movies, imgid);

        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        //add divider between items
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                ((LinearLayoutManager) mLayoutManager).getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        // set adapter
        RecyclerView.Adapter mAdapter = new ShowListAdapter(getActivity(), movies, imgid, durations, ratings);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }
}
