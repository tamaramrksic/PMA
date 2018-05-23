package com.example.ftn.showbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ftn.showbook.model.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView clickableTextLink = (TextView)findViewById(R.id.login);
        clickableTextLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        Button clickableButtonRegistr = (Button)findViewById(R.id.button_registration);
        clickableButtonRegistr.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                User user = new User();
                EditText first_name = (EditText)findViewById(R.id.first_name);
                String first_name_string = first_name.getText().toString();
                EditText last_name = (EditText)findViewById(R.id.last_name);
                String last_name_string = last_name.getText().toString();
                EditText username = (EditText)findViewById(R.id.username);
                String username_string = username.getText().toString();
                EditText password = (EditText)findViewById(R.id.password);
                String password_string = password.getText().toString();
                EditText address = (EditText)findViewById(R.id.address);
                String address_string = address.getText().toString();
                Spinner city=(Spinner) findViewById(R.id.city);
                String city_value = city.getSelectedItem().toString();
                Long city_number = 0L;
                if(city_value.equals("Novi Sad")) {
                    city_number = 1L;
                }else if(city_value.equals("Beograd")){
                    city_number = 2L;
                }else {
                    city_number = 3L;
                }
                user.setFirstName(first_name_string);
                user.setLastName(last_name_string);
                user.setUsername(username_string);
                user.setPassword(password_string);
                user.setAddress(address_string);
                Call<ResponseBody> call = ServiceUtils.pmaService.registr(user, city_number);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.success_message), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        System.out.println("Error!");
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.fail_message), Toast.LENGTH_SHORT).show();
                    }

                });

            }
        });

    }
}
