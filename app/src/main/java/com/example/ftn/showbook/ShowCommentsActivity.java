package com.example.ftn.showbook;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class ShowCommentsActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private String[] comments = {"Odlican film!", "Sjajno! Sve preporuke. :)"};
    private String[] usernames = {"user1", "user2"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_comments);
        mRecyclerView = (RecyclerView) findViewById(R.id.comments_recycler_view);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //add divider between items
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                ((LinearLayoutManager)mLayoutManager).getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        // set adapter
        mAdapter = new CommentListAdapter(this, comments, usernames);
        mRecyclerView.setAdapter(mAdapter);


    }

}
