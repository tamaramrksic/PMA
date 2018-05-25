package com.example.ftn.showbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.google.gson.Gson;

import okhttp3.ResponseBody;
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

        TextView clickableTextLink = (TextView)findViewById(R.id.registration);
        clickableTextLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        Button clickableButtonLogin = (Button)findViewById(R.id.login);
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
                            System.out.println("ovde sammmmmmm");
                            db.insertUser(response.body().getUsername(),response.body().getFirstName(),
                                    response.body().getLastName(), response.body().getAddress(),response.body().getLocation().getName().toString(), response.body().getMaxDistance(),
                                    response.body().getFacilityType().toString(), true);

                        }
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("drawerUsername", response.body().getUsername());
                        intent.putExtra("userPass", response.body().getPassword());
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

}
