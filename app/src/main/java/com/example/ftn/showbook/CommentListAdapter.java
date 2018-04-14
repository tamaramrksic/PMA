package com.example.ftn.showbook;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.ViewHolder> {

    private String[] mComments;
    private String[] mUsernames;
    private LayoutInflater mInflater;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tvComment;
        public TextView tvUsername;
        public ImageView icon;

        public ViewHolder(View view) {
            super(view);
            tvComment = view.findViewById(R.id.comment_item);
            tvUsername = view.findViewById(R.id.username_item);
            icon = view.findViewById(R.id.icon);
        }

    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public CommentListAdapter(Context context, String[] comments, String[] usernames) {
        mInflater = LayoutInflater.from(context);
        mComments = comments;
        mUsernames = usernames;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CommentListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View view = mInflater.inflate(R.layout.content_show_comments, parent, false);
        return new ViewHolder(view);
    }




    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.tvComment.setText(mComments[position]);
        holder.tvUsername.setText(mUsernames[position]);
        holder.icon.setImageResource(R.drawable.if_comment_user_36887);

    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mComments.length;
    }
}


