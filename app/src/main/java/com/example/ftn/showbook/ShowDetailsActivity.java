package com.example.ftn.showbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ShowDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_timetable, btn_comment, btn_see_comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);

        btn_timetable = findViewById(R.id.button_see_timetable);
        btn_comment = findViewById(R.id.button_comment);
        btn_see_comments = findViewById(R.id.button_see_comments);

        btn_timetable.setOnClickListener(this);
        btn_comment.setOnClickListener(this);
        btn_see_comments.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_see_timetable:
                Intent intentTimetable = new Intent(this, TimetableActivity.class);
                startActivity(intentTimetable);
                break;
            case R.id.button_comment:
                Intent intentComment = new Intent(this, CommentActivity.class);
                startActivity(intentComment);
                break;
            case R.id.button_see_comments:
                Intent intentSeeComments = new Intent(this, ShowCommentsActivity.class);
                startActivity(intentSeeComments);
                break;
            default:
                break;
        }
    }
}
