package com.example.ftn.showbook;

import android.content.Context;
import android.os.AsyncTask;

import com.example.ftn.showbook.database.DatabaseHelper;
import com.example.ftn.showbook.database.FacilityDB;
import com.example.ftn.showbook.model.Facility;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetFacilitiesTask extends AsyncTask<Void, Void,Void> {
    private DatabaseHelper db;
    private Context mContext;

    public GetFacilitiesTask (Context context){
        mContext = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        db = new DatabaseHelper(mContext);

        if(db.getAllFacilities().size() == 0 ) {
            Call<List<Facility>> call = ServiceUtils.pmaService.getAllFacilities();
            call.enqueue(new Callback<List<Facility>>() {
                @Override
                public void onResponse(Call<List<Facility>> call, Response<List<Facility>> response) {
                    List<FacilityDB> facilities = new ArrayList<>();


                    for (int i = 0; i < response.body().size(); i++) {
                        Long id = db.insertFacility(response.body().get(i).getName(), response.body().get(i).getType().toString(),
                                response.body().get(i).getAddress(), response.body().get(i).getLocation().getName().toString(),
                                response.body().get(i).getLatitude(), response.body().get(i).getLongitude());
                        System.out.println("dodat facility a id je " + id);
                    }
                    facilities = db.getAllFacilities();
                    System.out.println("dodat facility a velicina je " + facilities.size());

                }

                @Override
                public void onFailure(Call<List<Facility>> call, Throwable t) {
                    System.out.println("Greska prilikom ucitavanja facilitia!");
                }

            });

        }
        return null;
    }

}
