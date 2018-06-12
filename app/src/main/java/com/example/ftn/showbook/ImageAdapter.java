package com.example.ftn.showbook;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.ftn.showbook.model.SeatAvailability;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    List<Integer> selectedPositions = new ArrayList<>();
    private Long[] numbers;

    private List<SeatAvailability> seats;

    public ImageAdapter(Context c, Long[] numbers, List<SeatAvailability> seats) {
        this.mContext = c;
        this.numbers = numbers;
        this.seats = seats;
    }
    public int getCount() {
        return numbers.length;
    }

    public Object getItem(int position) {
        return numbers[position];
    }

    public long getItemId(int position) {
        return position;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
           // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(75, 75));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(5, 5, 5, 5);

            if(seats.get(position).getStatus().toString().equals("FREE")) {
                imageView.setColorFilter(ContextCompat.getColor(mContext, R.color.colorFree));
            } else if (seats.get(position).getStatus().toString().equals("RESERVED")) {
                imageView.setColorFilter(ContextCompat.getColor(mContext, R.color.colorBusy));
                imageView.setEnabled(false);
                imageView.setClickable(false);
                imageView.setOnClickListener(null);
            }
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(R.drawable.ic_action_seat);
        imageView.setBackgroundColor(selectedPositions.contains(position)? Color.GRAY : Color.WHITE);
        return imageView;
    }

}
/*
TextView textView;
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.seat_item, null);
        }
        final ImageView imageView = (ImageView)convertView.findViewById(R.id.seat);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(70, 70));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setPadding(5, 5, 5, 5);
        if(position %2 == 0) {
            imageView.setColorFilter(ContextCompat.getColor(mContext, R.color.colorFree));
        }else {
            imageView.setColorFilter(ContextCompat.getColor(mContext, R.color.colorBusy));
        }
        imageView.setImageResource(R.drawable.ic_action_seat);
        textView = (TextView)convertView.findViewById(R.id.seat_number);
        //String text = String.valueOf(position %5 + 1);
       // textView.setText(text);
        return convertView;
 */