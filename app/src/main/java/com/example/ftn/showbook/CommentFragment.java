package com.example.ftn.showbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ftn.showbook.model.Comment;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentFragment extends Fragment implements View.OnClickListener {

    private EditText commentEditText;
    private Bundle args;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_comment, container, false);
        args = this.getArguments();

//        getActivity().setTitle(getResources().getString(R.string.leave_comment_title) + args.getString("showName"));

        ((TextView)rootView.findViewById(R.id.show_name_heading)).setText(args.getString("showName"));
        commentEditText = rootView.findViewById(R.id.write_comment);
        Button sendCommentBtn = rootView.findViewById(R.id.button_send_comment);
        sendCommentBtn.setOnClickListener(this);


        return rootView;
    }

    @Override
    public void onClick(View view) {
        Comment comment = new Comment();
        comment.setText(commentEditText.getText().toString());

        Call<Comment> call = ServiceUtils.pmaService.commentShow(comment, args.getLong("showId"), getActivity().getIntent().getStringExtra("drawerUsername"));
        call.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                FragmentManager manager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
                manager.popBackStack();
                manager.beginTransaction()
                        .replace(R.id.main_container, new ShowCommentsFragment())
                        .addToBackStack(null)
                        .commit();

            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                Toast.makeText(getActivity(),getResources().getString(R.string.send_comment_failure), Toast.LENGTH_LONG).show();
            }

        });
    }
}
