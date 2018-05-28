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

import com.example.ftn.showbook.model.Comment;
import com.example.ftn.showbook.model.Show;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowCommentsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

   // private String[] comments = {"Odlican film!", "Sjajno! Sve preporuke. :)"};
    private String[] usernames = {"user1", "user2"};
    private ArrayList<Comment> comments;
    private TextView emptyView;
    private Intent intent;
    private Bundle args;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_show_comments, container, false);
        args = this.getArguments();
        mRecyclerView = rootView.findViewById(R.id.comments_recycler_view);
        emptyView = rootView.findViewById(R.id.no_comments_view);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        //add divider between items
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                ((LinearLayoutManager)mLayoutManager).getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        intent = getActivity().getIntent();
        // set adapter
       // mAdapter = new CommentListAdapter(getContext(), comments, usernames);
       // mRecyclerView.setAdapter(mAdapter);
        getCommentsForShow();
        return rootView;
    }

    public void getCommentsForShow() {
        Call<List<Comment>> call = ServiceUtils.pmaService.getCommentsByShow(args.getLong("showId"));
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                comments = new ArrayList<>(response.body());
                if (comments.isEmpty()) {
                    mRecyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }
                else{
                    RecyclerView.Adapter mAdapter = new CommentListAdapter(getActivity(), comments);
                    mRecyclerView.setAdapter(mAdapter);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Toast.makeText(getActivity(),getResources().getString(R.string.repertoire_failure_message), Toast.LENGTH_LONG).show();
            }

        });
    }
}
