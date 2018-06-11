package com.example.ftn.showbook;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ftn.showbook.model.Reservation;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Tab2Reserved  extends Fragment{

    private ArrayList<Reservation> reservations;
    private RecyclerView mRecyclerView;
    private TextView emptyView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2reserved, container, false);

        mRecyclerView = rootView.findViewById(R.id.tab2_recycler_view);
        emptyView = rootView.findViewById(R.id.no_reservations_view);


        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        //add divider between items
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                ((LinearLayoutManager) mLayoutManager).getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        System.out.println("uslo u rezervacije");
        Bundle args = this.getArguments();
        if(args.getString("parent").equals("main")) {
            getReservations();
        }
        else if(args.getString("parent").equals("repertoire")) {
            getFacilityReservations();
        }

        return rootView;
    }



    public void getReservations() {
        Intent intent = getActivity().getIntent();
        String username = intent.getStringExtra("drawerUsername");
        Call<List<Reservation>> call = ServiceUtils.pmaService.getUserReservations(username);
        call.enqueue(new Callback<List<Reservation>>() {
            @Override
            public void onResponse(Call<List<Reservation>> call, Response<List<Reservation>> response) {
                reservations = new ArrayList<>(response.body());

                if (reservations.isEmpty()) {
                    mRecyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }
                else {
                    RecyclerView.Adapter mAdapter = new ReservationListAdapter(getActivity(), reservations, "reserved");
                    mRecyclerView.setAdapter(mAdapter);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Reservation>> call, Throwable t) {
                Toast.makeText(getActivity(),getResources().getString(R.string.reservations_failure_message), Toast.LENGTH_LONG).show();
            }

        });
    }

    public void getFacilityReservations() {
        Intent intent = getActivity().getIntent();
        String username = intent.getStringExtra("drawerUsername");
        Long facilityId = Long.parseLong(intent.getStringExtra("FacilityId"));
        Call<List<Reservation>> call = ServiceUtils.pmaService.getUserReservationsByFacility(username, facilityId);
        call.enqueue(new Callback<List<Reservation>>() {
            @Override
            public void onResponse(Call<List<Reservation>> call, Response<List<Reservation>> response) {
                reservations = new ArrayList<>(response.body());

                if (reservations.isEmpty()) {
                    mRecyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                } else {
                    RecyclerView.Adapter mAdapter = new ReservationListAdapter(getActivity(), reservations, "reserved");
                    mRecyclerView.setAdapter(mAdapter);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Reservation>> call, Throwable t) {
                Toast.makeText(getActivity(), getResources().getString(R.string.reservations_failure_message), Toast.LENGTH_LONG).show();
            }
        });
    }


}
