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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowDetailsFragment extends Fragment implements View.OnClickListener {

    private FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_show_details, container, false);
        Bundle args = this.getArguments();
        ((TextView)rootView.findViewById(R.id.show_name)).setText(args.getString("showName"));
        ((TextView)rootView.findViewById(R.id.show_directors)).setText(args.getString("showDirectors"));
        ((TextView)rootView.findViewById(R.id.show_performers)).setText(args.getString("showPerformers"));
        ((TextView)rootView.findViewById(R.id.show_genre)).setText(args.getString("showGenre"));
        ((TextView)rootView.findViewById(R.id.show_duration)).setText(args.getString("showDuration"));
        ((TextView)rootView.findViewById(R.id.show_description)).setText(args.getString("showDescription") + " min");
        ((RatingBar)rootView.findViewById(R.id.rating_bar)).setRating(args.getFloat("showRating"));

        rootView.findViewById(R.id.button_see_timetable).setOnClickListener(this);
        rootView.findViewById(R.id.button_comment).setOnClickListener(this);
        rootView.findViewById(R.id.button_see_comments).setOnClickListener(this);

        final Button interestedBtn = rootView.findViewById(R.id.button_interested_in);
        interestedBtn.setOnClickListener(this);

        if (args.getString("fragmentName").equals("interested")) {
            interestedBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_remove,0,0,0);
            interestedBtn.setTag("add");
        } else if(args.getString("fragmentName").equals("repertoire")) {
            //interestedBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_remove,0,0,0);
            System.out.println("USERNAME  " + getActivity().getIntent().getStringExtra("drawerUsername"));
            Call<Boolean> call = ServiceUtils.pmaService.isInterestedShow(getActivity().getIntent().getStringExtra("drawerUsername"), args.getLong("showId"));
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    System.out.println("RESPONSE " + response.body());
//                    if(true)
//                        interestedBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_remove,0,0,0);
//                        interestedBtn.setTag("remove");
//                    else
//                        interestedBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_add,0,0,0);
//                        interestedBtn.setTag("add");
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Toast.makeText(getActivity(), R.string.fail_message, Toast.LENGTH_SHORT).show();
                }

            });
        }

        fragmentManager = getActivity().getSupportFragmentManager();
        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_see_timetable:
                fragmentManager.beginTransaction()
                        .replace(R.id.main_container, new TimetableFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.button_comment:
                fragmentManager.beginTransaction()
                        .replace(R.id.main_container, new CommentFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.button_see_comments:
                fragmentManager.beginTransaction()
                        .replace(R.id.main_container, new ShowCommentsFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.button_interested_in:
                Button btn = view.findViewById(R.id.button_interested_in);
                if(btn.getTag().equals("add")) {
                    System.out.println("dodaj");
                } else if(btn.getTag().equals("remove")) {
                    System.out.println("obrisi");
                }
                break;
            default:
                break;
        }
    }
}
