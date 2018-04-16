package com.example.ftn.showbook;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class ShowListAdapter extends RecyclerView.Adapter<ShowListAdapter.ViewHolder> {

    private String[] titles;
    private Integer[] images;
    private String[] durations;
    private String[] ratings;
    private LayoutInflater mInflater;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title, description, duration, rating;
        public ImageView image;

        public ViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.show_repertoire_poster);
            title = (TextView) view.findViewById(R.id.show_repertoire_title);
            description = (TextView) view.findViewById(R.id.show_repertoire_description);
            duration = (TextView) view.findViewById(R.id.show_repertoire_duration);
            rating = (TextView) view.findViewById(R.id.show_repertoire_rating);

        }
    }

    public ShowListAdapter(Context context, String[] titles, Integer[] images, String[] durations, String[] ratings) {
        this.mInflater = LayoutInflater.from(context);
        this.titles = titles;
        this.images = images;
        this.durations = durations;
        this.ratings = ratings;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = mInflater.inflate(R.layout.show_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.image.setImageResource(images[position]);
        holder.title.setText(titles[position]);
        holder.description.setText("opis filma");
        holder.duration.setText(durations[position]);
        holder.rating.setText(ratings[position]);
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

//    public ShowListAdapter(Activity activity, String[] itemname, Integer[] imgid) {
//        super(activity, R.layout.show_list, itemname);
//        this.activity=activity;
//        this.itemname=itemname;
//        this.imgid=imgid;
//    }
//    public View getView(int position, View view, ViewGroup parent) {
//        LayoutInflater inflater=activity.getLayoutInflater();
//        View rowView=inflater.inflate(R.layout.show_list, null,true);
//
//        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
//        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
//        TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);
//
//        txtTitle.setText(itemname[position]);
//        imageView.setImageResource(imgid[position]);
//        extratxt.setText("Description "+itemname[position]);
//        return rowView;
//    };
}