package com.example.ftn.showbook;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

public class ShowDetailsFragment extends Fragment implements View.OnClickListener {

    private FragmentManager fragmentManager;
    private Bundle args;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_show_details, container, false);
        args = this.getArguments();
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
                fragmentManager.beginTransaction()
                        .replace(R.id.main_container, new ShowCommentsFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            default:
                break;
        }
    }
}
