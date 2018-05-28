package com.example.ftn.showbook;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.os.Build.ID;

public class UploadImageTask extends AsyncTask<Long, Void, Bitmap> {
    private ShowListAdapter.ViewHolder mholder;

    public UploadImageTask (ShowListAdapter.ViewHolder holder){
        mholder = holder;

    }
    @Override
    protected Bitmap doInBackground(Long... numbers) {
        try {
            Long number = numbers[0];
            String name = "slika-"+ number;
            URL url = new URL("http://192.168.1.108:8080/"+name+".jpg");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        //ImageView imageView = (ImageView) mrootView.findViewById(R.id.show_repertoire_poster);
        //imageView.setImageBitmap(result);
        mholder.image.setImageBitmap(result);
    }
}
