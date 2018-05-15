package com.example.ftn.showbook;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ShowDetailsFragment extends Fragment implements View.OnClickListener {

    private Button btn_timetable, btn_comment, btn_see_comments;
    private FragmentManager fragmentManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_show_details, container, false);

        btn_timetable = rootView.findViewById(R.id.button_see_timetable);
        btn_comment = rootView.findViewById(R.id.button_comment);
        btn_see_comments = rootView.findViewById(R.id.button_see_comments);

        btn_timetable.setOnClickListener(this);
        btn_comment.setOnClickListener(this);
        btn_see_comments.setOnClickListener(this);

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
            default:
                break;
        }
    }
}
