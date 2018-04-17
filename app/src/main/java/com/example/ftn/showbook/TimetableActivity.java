package com.example.ftn.showbook;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TimetableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String currentDate = sdf.format(Calendar.getInstance().getTime());

        TextView col1 = (TextView)findViewById(R.id.firstColumn);
        col1.setText(currentDate);
        TextView col2 = (TextView)findViewById(R.id.secondColumn);
        col2.setText("15-04-2018");
        TextView col3 = (TextView)findViewById(R.id.thirdColumn);
        col3.setText("16-04-2018");
        TextView col4 = (TextView)findViewById(R.id.fourthColumn);
        col4.setText("17-04-2018");
        TextView col5 = (TextView)findViewById(R.id.fifthColumn);
        col5.setText("18-04-2018");

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }

//        TableLayout table = (TableLayout)findViewById(R.id.timetable);
//        table.setColumnStretchable(0,true);
//
//        TableRow newRow = new TableRow(this);
//        TextView first = new TextView(this);
//        first.setText("12:00");
//        first.setGravity(Gravity.CENTER);
//        newRow.addView(first);
    }

    public void timeClicked(View view) {
        TextView textView = (TextView)view;
//        Toast.makeText(this, textView.getText(), Toast.LENGTH_LONG).show();

        Intent intent = new Intent(getApplicationContext(), SeatReserveActivity.class);
        intent.putExtra("TIME", textView.getText());
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
