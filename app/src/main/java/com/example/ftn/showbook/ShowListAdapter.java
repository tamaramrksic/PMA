package com.example.ftn.showbook;

import android.content.Context;
import android.os.Bundle;
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

    private LayoutInflater mInflater;
    private static String fragmentName;

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
            description = view.findViewById(R.id.show_repertoire_description);
            rating = view.findViewById(R.id.show_repertoire_rating);


            context = view.getContext();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<Show> shows = (ArrayList<Show>) itemView.getTag();
                    ShowDetailsFragment fragment = new ShowDetailsFragment();

                    Bundle args = new Bundle();

                    args.putString("fragmentName", fragmentName);

                    args.putLong("showId", shows.get(getAdapterPosition()).getId());
                    args.putString("showName", shows.get(getAdapterPosition()).getName());
                    args.putString("showDirectors", shows.get(getAdapterPosition()).getDirectors());
                    args.putString("showPerformers", shows.get(getAdapterPosition()).getPerformers());
                    args.putString("showGenre", shows.get(getAdapterPosition()).getGenre());
                    args.putString("showDuration", Double.toString(shows.get(getAdapterPosition()).getDuration()));
                    args.putString("showDescription", shows.get(getAdapterPosition()).getDescription());
                    if(shows.get(getAdapterPosition()).getRating() != null) {
                        args.putFloat("showRating", shows.get(getAdapterPosition()).getRating().floatValue());
                    } else {
                        args.putFloat("showRating", 0);
                    }

                    fragment.setArguments(args);

                    ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_container, fragment)
                            .addToBackStack(null)
                            .commit();
                }
            });
        }
    }




    public ShowListAdapter(Context context, ArrayList<Show> shows, String fragmentName) {
        this.mInflater = LayoutInflater.from(context);
        this.shows = shows;
        ShowListAdapter.fragmentName = fragmentName;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = mInflater.inflate(R.layout.show_list, parent, false);
        view.setTag(shows);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.image.setImageResource(getImage(shows.get(position).getId()));
        holder.title.setText(shows.get(position).getName());
        holder.description.setText(shows.get(position).getDescription());
        holder.duration.setText(shows.get(position).getDuration().toString());
        holder.duration.setText(String.valueOf(shows.get(position).getDuration()));
        if(shows.get(position).getRating() != null) {
            holder.rating.setText(String.valueOf(shows.get(position).getRating()));
        } else {
            holder.rating.setText("0.0");
        }

    }

    @Override
    public int getItemCount() {
        if (shows != null) {
            return shows.size();
        }
        return 0;
    }

    public static Integer getImage(Long id) {
        Integer result = 0;
        if(id.equals(1L)){
            result = R.drawable.slika_1;
        }else if(id.equals(2L)){
            result = R.drawable.slika_2;
        }else if(id.equals(3L)){
            result = R.drawable.slika_3;
        }else if(id.equals(4L)){
            result = R.drawable.slika_4;
        }else if(id.equals(5L)){
            result = R.drawable.slika_5;
        }else if(id.equals(6L)){
            result = R.drawable.slika_6;
        }else if(id.equals(7L)){
            result = R.drawable.slika_7;
        }
        return result;
    }

}