package com.example.ftn.showbook;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ftn.showbook.model.Show;

import java.util.ArrayList;


public class Tab3Interested extends Fragment {

    String[] movies = {"Kapetan Amerika", "Zec Petar"};
    Integer[] imgid={
            R.drawable.avengers,
            R.drawable.peter_rabbit
    };
    String[] durations = {"152", "93"};
    String[] ratings = {"4.1", "5"};
    ArrayList<Show> shows;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3interested, container, false);

        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.tab3_recycler_view);
        //ListAdapter listadapter = new ShowListAdapter(getActivity(), movies, imgid);

        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        //add divider between items
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                ((LinearLayoutManager) mLayoutManager).getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        // set adapter
        RecyclerView.Adapter mAdapter = new ShowListAdapter(getActivity(), shows, imgid);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

}
