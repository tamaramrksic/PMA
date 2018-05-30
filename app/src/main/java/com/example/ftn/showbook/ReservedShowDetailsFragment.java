package com.example.ftn.showbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReservedShowDetailsFragment extends Fragment {

    private RatingBar ratingBar;
    private Intent intent;
    private String username;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reserved_show_details, container, false);
        final Bundle args = this.getArguments();
        intent = getActivity().getIntent();
         username = intent.getStringExtra("drawerUsername");
        ((ImageView)rootView.findViewById(R.id.show_image)).setImageResource(ShowListAdapter.getImage(args.getLong("showId")));
        ((TextView)rootView.findViewById(R.id.show_name_heading)).setText(args.getString("showName"));
        ((TextView)rootView.findViewById(R.id.show_duration_value)).setText(args.getString("showDuration") + " min");
        ((TextView)rootView.findViewById(R.id.date_value)).setText(args.getString("date"));
        ((TextView)rootView.findViewById(R.id.time_value)).setText(args.getString("time"));
        ((TextView)rootView.findViewById(R.id.hall_value)).setText(args.getString("facilityHall"));
        ((TextView)rootView.findViewById(R.id.facility_value)).setText(args.getString("facility"));
        ((TextView)rootView.findViewById(R.id.num_of_seats_value)).setText(args.getString("numOfTickets"));
        ((TextView)rootView.findViewById(R.id.total_price_value)).setText(args.getString("totalPrice") + " rsd");

        Button cancelReservationBtn = rootView.findViewById(R.id.button_cancel_reservation);
        ratingBar = rootView.findViewById(R.id.rating_bar);
        Button ratingButton = rootView.findViewById(R.id.button_rating);
        if (args.getString("fragmentName").equals("reserved")) {
            cancelReservationBtn.setVisibility(View.VISIBLE);
            ratingBar.setVisibility(View.INVISIBLE);

        } else if (args.getString("fragmentName").equals("seen")) {
            cancelReservationBtn.setVisibility(View.INVISIBLE);
            ratingBar.setVisibility(View.VISIBLE);
        }

        cancelReservationBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Call<ResponseBody> call = ServiceUtils.pmaService.cancelReservation(args.getLong("reservationId"));
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        ((AppCompatActivity)getContext()).getSupportFragmentManager().popBackStack();

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getActivity(),getResources().getString(R.string.cancel_reservation_failure_message), Toast.LENGTH_LONG).show();
                    }

                });



            }
        });

        ratingButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Float ratingFloat = ratingBar.getRating();
                Integer rating = Math.round(ratingFloat);


                Call<ResponseBody> call = ServiceUtils.pmaService.ratingReservation(args.getLong("reservationId"), username,rating);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        ((AppCompatActivity)getContext()).getSupportFragmentManager().popBackStack();

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getActivity(),getResources().getString(R.string.cancel_reservation_failure_message), Toast.LENGTH_LONG).show();
                    }

                });



            }
        });

        return rootView;
    }
}
