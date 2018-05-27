package com.example.ftn.showbook;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ftn.showbook.model.Show;

import java.util.ArrayList;


public class ShowListAdapter extends RecyclerView.Adapter<ShowListAdapter.ViewHolder> {

    private Integer[] images;
    private LayoutInflater mInflater;

    private ArrayList<Show> shows;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title, description, duration, rating;
        public ImageView image;
        private Context context;

        public ViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.show_repertoire_poster);
            title = (TextView) view.findViewById(R.id.show_repertoire_title);
            description = (TextView) view.findViewById(R.id.show_repertoire_description);
            duration = (TextView) view.findViewById(R.id.show_repertoire_duration);
            description =(TextView) view.findViewById(R.id.show_repertoire_description);
            rating = (TextView) view.findViewById(R.id.show_repertoire_rating);

            context = view.getContext();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_container, new ShowDetailsFragment())
                            .addToBackStack(null)
                            .commit();
                }
            });

        }
    }

    public ShowListAdapter(Context context, ArrayList<Show> shows, Integer[] images) {
        this.mInflater = LayoutInflater.from(context);
        this.images = images;
        this.shows = shows;
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
        holder.title.setText(shows.get(position).getName());
        holder.description.setText(shows.get(position).getDescription());
        holder.duration.setText(shows.get(position).getDuration().toString());
        holder.rating.setText(shows.get(position).getRating().toString());
    }

    @Override
    public int getItemCount() {
        if (shows != null) {
            return shows.size();
        }
        return 0;
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