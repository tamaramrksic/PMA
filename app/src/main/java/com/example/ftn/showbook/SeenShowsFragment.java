package com.example.ftn.showbook;

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


public class SeenShowsFragment  extends Fragment {

    Integer[] imgid={
            R.drawable.avengers,
            R.drawable.peter_rabbit
    };
    private ArrayList<Reservation> seenShows;
    private RecyclerView mRecyclerView;
    private TextView emptyView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_seen_shows, container, false);

        mRecyclerView = rootView.findViewById(R.id.seen_show_recycler_view);
        emptyView = rootView.findViewById(R.id.no_seen_shows_view);

        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        //add divider between items
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                ((LinearLayoutManager) mLayoutManager).getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        getSeenShows();

        return rootView;
    }

    public void getSeenShows() {
        Intent intent = getActivity().getIntent();
        String username = intent.getStringExtra("drawerUsername");
        Call<List<Reservation>> call = ServiceUtils.pmaService.getUserSeenShows(username);
        call.enqueue(new Callback<List<Reservation>>() {
            @Override
            public void onResponse(Call<List<Reservation>> call, Response<List<Reservation>> response) {
                seenShows = new ArrayList<>(response.body());

                if (seenShows.isEmpty()) {
                    mRecyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }
                else {
                    RecyclerView.Adapter mAdapter = new ReservationListAdapter(getActivity(), seenShows, imgid, "seen");
                    mRecyclerView.setAdapter(mAdapter);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Reservation>> call, Throwable t) {
                Toast.makeText(getActivity(),getResources().getString(R.string.seen_shows_failure_message), Toast.LENGTH_LONG).show();
            }

        });
    }
}
