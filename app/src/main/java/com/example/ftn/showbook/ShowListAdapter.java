package com.example.ftn.showbook;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ftn.showbook.model.Show;

import java.util.ArrayList;


public class ShowListAdapter extends RecyclerView.Adapter<ShowListAdapter.ViewHolder> {

    private Integer[] images;
    private LayoutInflater mInflater;

    private ArrayList<Show> shows;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title, description, duration, rating;
        public ImageView image;
        private Context context;

        public ViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.show_repertoire_poster);
            title = view.findViewById(R.id.show_repertoire_title);
            description = view.findViewById(R.id.show_repertoire_description);
            duration = view.findViewById(R.id.show_repertoire_duration);
            rating = view.findViewById(R.id.show_repertoire_rating);

            context = view.getContext();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_container, new ShowDetailsFragment())
                            .addToBackStack(null)
                            .commit();
                }
            });

        }
    }

    public ShowListAdapter(Context context, ArrayList<Show> shows, Integer[] images) {
        this.mInflater = LayoutInflater.from(context);
        this.shows = shows;
        this.images = images;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = mInflater.inflate(R.layout.show_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.image.setImageResource(images[position]);
        holder.title.setText(shows.get(position).getName());
        holder.description.setText(shows.get(position).getDescription());
        holder.duration.setText(String.valueOf(shows.get(position).getDuration()));
        if(shows.get(position).getRating() != null) {
            holder.rating.setText(String.valueOf(shows.get(position).getRating()));
        } else {
            holder.rating.setText("0.0");
        }

    }

    @Override
    public int getItemCount() {
        if(shows != null) {
            return shows.size();
        }
        return 0;
    }
}