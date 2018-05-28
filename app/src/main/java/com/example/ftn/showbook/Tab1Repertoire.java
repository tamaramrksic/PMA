package com.example.ftn.showbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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


public class Tab1Repertoire extends Fragment {

    Integer[] imgid={
            R.drawable.avengers,
            R.drawable.peter_rabbit,
            R.drawable.hari
    };
    private Intent intent;
    private ArrayList<Show> shows;
    private RecyclerView mRecyclerView;
    private TextView emptyView;
    private Long facilityId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1repertoire, container, false);
        mRecyclerView = rootView.findViewById(R.id.tab1_recycler_view);
        emptyView = rootView.findViewById(R.id.no_repertoire_view);
        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        //add divider between items
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                ((LinearLayoutManager) mLayoutManager).getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        intent = getActivity().getIntent();
        facilityId = Long.parseLong(intent.getStringExtra("FacilityId"));
        // set adapter

        System.out.println("KREIRAN TAB 1");
        getRepertoire();

        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // Refresh your fragment here
            System.out.println("na repertoaru je ");
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(this).attach(this).commit();

        }
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

    }


    public void getRepertoire() {
        Call<List<Show>> call = ServiceUtils.pmaService.getShowsByFacility(facilityId);
        call.enqueue(new Callback<List<Show>>() {
            @Override
            public void onResponse(Call<List<Show>> call, Response<List<Show>> response) {
                    shows = new ArrayList<>(response.body());
                if (shows.isEmpty()) {
                    mRecyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }
                else{
                    RecyclerView.Adapter mAdapter = new ShowListAdapter(getActivity(), shows, "repertoire");
                    mRecyclerView.setAdapter(mAdapter);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<List<Show>> call, Throwable t) {
                Toast.makeText(getActivity(),getResources().getString(R.string.repertoire_failure_message), Toast.LENGTH_LONG).show();
            }

        });
    }
}