package com.example.ftn.showbook;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.ViewManager;
import android.widget.Button;

import android.widget.ImageView;

import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ftn.showbook.model.Show;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowDetailsFragment extends Fragment implements View.OnClickListener {

    private FragmentManager fragmentManager;
    private Bundle args;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_show_details, container, false);
        args = this.getArguments();
        ((ImageView)rootView.findViewById(R.id.show_image)).setImageResource(ShowListAdapter.getImage(args.getLong("showId")));
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

        if (args.getString("fragmentName").equals("interested_main")) {
            interestedBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_remove,0,0,0);
            interestedBtn.setTag("add");
            final Button timetableBtn = rootView.findViewById(R.id.button_see_timetable);
            ((ViewManager)timetableBtn.getParent()).removeView(timetableBtn);
        } else if(args.getString("fragmentName").equals("interested_repertoire")) {
            Call<Boolean> call = ServiceUtils.pmaService.isInterestedShow(getActivity().getIntent().getStringExtra("drawerUsername"), args.getLong("showId"));
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if(response.body()) {
                        interestedBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_remove,0,0,0);
                        interestedBtn.setTag("remove");
                    } else {
                        interestedBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_add,0,0,0);
                        interestedBtn.setTag("add");
                    }
                }
                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Toast.makeText(getActivity(), R.string.is_interested_failure_message, Toast.LENGTH_SHORT).show();
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
                Fragment timetableFragment = new TimetableFragment();
                Bundle timetableArgs = new Bundle();
                timetableArgs.putLong("showId", args.getLong("showId"));
                timetableArgs.putString("showName", args.getString("showName"));
                timetableFragment.setArguments(timetableArgs);
                fragmentManager.beginTransaction()
                        .replace(R.id.main_container, timetableFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.button_comment:
                Fragment commentFragment = new CommentFragment();

                Bundle commentArgs = new Bundle();
                commentArgs.putLong("showId", args.getLong("showId"));
                commentArgs.putString("showName", args.getString("showName"));
                commentFragment.setArguments(commentArgs);
                fragmentManager.beginTransaction()
                        .replace(R.id.main_container, commentFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.button_see_comments:
                Fragment commentsFragment = new ShowCommentsFragment();
                Bundle commentsArgs = new Bundle();
                commentsArgs.putLong("showId", args.getLong("showId"));
                commentsArgs.putString("showName", args.getString("showName"));
                commentsFragment.setArguments(commentsArgs);
                fragmentManager.beginTransaction()
                        .replace(R.id.main_container, commentsFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.button_interested_in:
                Bundle args = this.getArguments();
                final Button interestedBtn = view.findViewById(R.id.button_interested_in);
                if(interestedBtn.getTag().equals("add")) {
                    Call<Show> call = ServiceUtils.pmaService.addInterestedShow(getActivity().getIntent().getStringExtra("drawerUsername"), args.getLong("showId"));
                    call.enqueue(new Callback<Show>() {
                        @Override
                        public void onResponse(Call<Show> call, Response<Show> response) {
                            interestedBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_remove,0,0,0);
                            interestedBtn.setTag("remove");
                        }

                        @Override
                        public void onFailure(Call<Show> call, Throwable t) {
                            Toast.makeText(getActivity(), R.string.add_interested_show_failure_message, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else if(interestedBtn.getTag().equals("remove")) {
                    Call<Show> call = ServiceUtils.pmaService.removeInterestedShow(getActivity().getIntent().getStringExtra("drawerUsername"), args.getLong("showId"));
                    call.enqueue(new Callback<Show>() {
                        @Override
                        public void onResponse(Call<Show> call, Response<Show> response) {
                            interestedBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_add,0,0,0);
                            interestedBtn.setTag("add");
                        }

                        @Override
                        public void onFailure(Call<Show> call, Throwable t) {
                            Toast.makeText(getActivity(), R.string.remove_interested_show_failure_message, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
            default:
                break;
        }
    }

}
