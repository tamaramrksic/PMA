package com.example.ftn.showbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ftn.showbook.model.Show;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Objects;


public class Tab3Interested extends Fragment {

    private ArrayList<Show> interestedShows;
    private RecyclerView mRecyclerView;
    private TextView emptyView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3interested, container, false);

        mRecyclerView = rootView.findViewById(R.id.tab3_recycler_view);
        emptyView = rootView.findViewById(R.id.no_interested_shows_view);

        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        //add divider between items
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                ((LinearLayoutManager) mLayoutManager).getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        Bundle args = this.getArguments();
        if(args.getString("parent").equals("main")) {
            getInterestedShows();
        }
        else if(args.getString("parent").equals("repertoire")) {
            getFacilityInterestedShows();
        }

        return rootView;
    }

    public void getInterestedShows() {
        Intent intent = getActivity().getIntent();
        String username = intent.getStringExtra("drawerUsername");
        Call<List<Show>> call = ServiceUtils.pmaService.getUserInterestedShows(username);
        call.enqueue(new Callback<List<Show>>() {
            @Override
            public void onResponse(Call<List<Show>> call, Response<List<Show>> response) {
                interestedShows = new ArrayList<>(response.body());

                if (interestedShows.isEmpty()) {
                    mRecyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }
                else {
                    RecyclerView.Adapter mAdapter = new ShowListAdapter(getActivity(), interestedShows,"interested_main");
                    mRecyclerView.setAdapter(mAdapter);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Show>> call, Throwable t) {
                Toast.makeText(getActivity(),getResources().getString(R.string.interested_shows_failure_message), Toast.LENGTH_LONG).show();
            }

        });
    }

    public void getFacilityInterestedShows() {
        Intent intent = getActivity().getIntent();
        String username = intent.getStringExtra("drawerUsername");
        Long facilityId = Long.parseLong(intent.getStringExtra("FacilityId"));
        Call<List<Show>> call = ServiceUtils.pmaService.getFacilityInterestedShows(username, facilityId);
        call.enqueue(new Callback<List<Show>>() {
            @Override
            public void onResponse(Call<List<Show>> call, Response<List<Show>> response) {
                interestedShows = new ArrayList<>(response.body());

                if (interestedShows.isEmpty()) {
                    mRecyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }
                else {
                    RecyclerView.Adapter mAdapter = new ShowListAdapter(getActivity(), interestedShows, "repertoire");
                    mRecyclerView.setAdapter(mAdapter);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Show>> call, Throwable t) {
                Toast.makeText(getActivity(),getResources().getString(R.string.interested_shows_failure_message), Toast.LENGTH_LONG).show();
            }

        });
    }
}
