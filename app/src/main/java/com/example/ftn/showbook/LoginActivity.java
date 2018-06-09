package com.example.ftn.showbook;


import android.content.Intent;
import android.os.AsyncTask;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ftn.showbook.database.DatabaseHelper;
import com.example.ftn.showbook.database.UserDB;
import com.example.ftn.showbook.model.User;
import com.example.ftn.showbook.model.UserCredentials;
import com.google.firebase.iid.FirebaseInstanceId;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private DatabaseHelper db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        db = new DatabaseHelper(this);
        new GetFacilitiesTask(getApplicationContext()).execute();

        TextView clickableTextLink = (TextView) findViewById(R.id.registration);
        clickableTextLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        Button clickableButtonLogin = (Button) findViewById(R.id.login);
        clickableButtonLogin.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 EditText username = findViewById(R.id.username);
                 EditText password = findViewById(R.id.password);
                 UserCredentials userCredentials = new UserCredentials(username.getText().toString(), password.getText().toString());

                 Call<User> call = ServiceUtils.pmaService.login(userCredentials);
                 call.enqueue(new Callback<User>() {
                     @Override
                     public void onResponse(Call<User> call, Response<User> response) {
                         UserDB userDB = db.getUserByUsername(response.body().getUsername());
                         if (userDB == null) {
                             db.insertUser(response.body().getUsername(), response.body().getFirstName(),
                                     response.body().getLastName(), response.body().getAddress(), response.body().getLocation().getName().toString(), response.body().getMaxDistance(),
                                     response.body().getFacilityType().toString(), response.body().isCommentNotification());

                         }
                         new GenerateToken().execute(response.body().getUsername());

                         Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                         intent.putExtra("drawerUsername", response.body().getUsername());
                         intent.putExtra("userPass", response.body().getPassword());
                         intent.putExtra("numLoc", "0");
                         intent.putExtra("numPerLoc", "0");
                         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // clears all previous activities task
                         finish();
                         startActivity(intent);

                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.invalid_login_message), Toast.LENGTH_SHORT).show();
                        }

                    });


                }
            });




    }

    public class GenerateToken extends AsyncTask<String,Void,Void>
    {


        @Override
        protected Void doInBackground(String... strings) {
            String tkn = FirebaseInstanceId.getInstance().getToken();
            sendUserToken(strings[0], tkn);
            return null;
        }
    }

    public void sendUserToken(String username, String token) {

        Call<User> call = ServiceUtils.pmaService.setUserToken(username,token);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                System.out.println("Uspesno sacuvan token!");

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println("Neuspesno sacuvan token!");

            }

        });
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isNetworkAvailable()) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.internet_disabled), Toast.LENGTH_SHORT).show();
        }
    }

}
