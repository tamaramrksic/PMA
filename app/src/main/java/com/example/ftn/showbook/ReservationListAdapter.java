package com.example.ftn.showbook;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ReservationListAdapter extends RecyclerView.Adapter<ReservationListAdapter.ViewHolder>{

    private String[] titles;
    private Integer[] images;
    private String[] dates;
    private String[] times;
    private String[] ratings;
    private LayoutInflater mInflater;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title, date, time, rating;
        public ImageView image;

        public ViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.show_reserved_poster);
            title = (TextView) view.findViewById(R.id.show_reserved_title);
            date = (TextView) view.findViewById(R.id.date_value);
            time = (TextView) view.findViewById(R.id.time_value);
            rating = (TextView) view.findViewById(R.id.show_reserved_rating);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, ReservedShowDetailsActivity.class);
                    context.startActivity(intent);
                }
            });

        }
    }

    public ReservationListAdapter(Context context, String[] titles, Integer[] images,
                                  String[] dates, String[] times, String[] ratings) {
        this.mInflater = LayoutInflater.from(context);
        this.titles = titles;
        this.images = images;
        this.dates = dates;
        this.times = times;
        this.ratings = ratings;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = mInflater.inflate(R.layout.reservation_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.image.setImageResource(images[position]);
        holder.title.setText(titles[position]);
        holder.date.setText(dates[position]);
        holder.time.setText(times[position]);
        holder.rating.setText(ratings[position]);
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}
