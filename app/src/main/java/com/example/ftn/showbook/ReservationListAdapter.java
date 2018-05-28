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

import com.example.ftn.showbook.model.Reservation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReservationListAdapter extends RecyclerView.Adapter<ReservationListAdapter.ViewHolder>{

    private Integer[] images;
    private LayoutInflater mInflater;
    private static String fragmentName;

    private ArrayList<Reservation> reservations;

    public ArrayList<Reservation> getReservations() {
        return reservations;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title, date, time, rating;
        public ImageView image;
        private Context context;

        public ViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.show_reserved_poster);
            title = (TextView) view.findViewById(R.id.show_reserved_title);
            date = (TextView) view.findViewById(R.id.date_value);
            time = (TextView) view.findViewById(R.id.time_value);
            rating = (TextView) view.findViewById(R.id.show_reserved_rating);

            context = view.getContext();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ArrayList<Reservation> reservations = (ArrayList<Reservation>) itemView.getTag();
                    ReservedShowDetailsFragment fragment = new ReservedShowDetailsFragment();
                    Bundle args = new Bundle();
                    args.putString("fragmentName", fragmentName);
                    args.putLong("reservationId", reservations.get(getAdapterPosition()).getId());
                    args.putString("showName", reservations.get(getAdapterPosition()).getEvent().getShow().getName());
                    args.putString("showDuration", reservations.get(getAdapterPosition()).getEvent().getShow().getDuration().toString());
                    args.putString("date", getDateOrTime(reservations.get(getAdapterPosition()).getEvent().getStart(), "date"));
                    args.putString("time", getDateOrTime(reservations.get(getAdapterPosition()).getEvent().getStart(), "time"));
                    args.putString("facilityHall", reservations.get(getAdapterPosition()).getEvent().getFacilityHall().getName());
                    args.putString("facility", reservations.get(getAdapterPosition()).getEvent().getFacilityHall().getFacility().getName());
                    args.putString("numOfTickets", Integer.toString(reservations.get(getAdapterPosition()).getSeats().size()));
                    args.putString("totalPrice", reservations.get(getAdapterPosition()).getTotalPrice().toString());
                    fragment.setArguments(args);
                    ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_container, fragment)
                            .addToBackStack(null)
                            .commit();
                }
            });

        }
    }

    public ReservationListAdapter(Context context, ArrayList<Reservation> reservations, Integer[] images, String fragmentName) {
        this.mInflater = LayoutInflater.from(context);
        this.reservations = reservations;
        this.images = images;
        ReservationListAdapter.fragmentName = fragmentName;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = mInflater.inflate(R.layout.reservation_list, parent, false);
        view.setTag(reservations);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.image.setImageResource(images[position]);
        holder.title.setText(reservations.get(position).getEvent().getShow().getName());
        holder.date.setText(getDateOrTime(reservations.get(position).getEvent().getStart(),"date"));
        holder.time.setText(getDateOrTime(reservations.get(position).getEvent().getStart(),"time"));
        if (reservations.get(position).getRating() != null) {
            holder.rating.setText(reservations.get(position).getRating().getRate());
        } else {
            holder.rating.setText("0.0");
        }
    }

    @Override
    public int getItemCount() {
        if (reservations != null) {
            return reservations.size();
        }
        return 0;
    }

    public static String getDateOrTime(Date date, String datePart) {
        SimpleDateFormat simpleDateFormat;

        if (datePart.equals("date")) {
            simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        } else {
            simpleDateFormat = new SimpleDateFormat("HH:mm");
        }

        return simpleDateFormat.format(date);
    }
}
