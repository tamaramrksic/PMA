package com.example.ftn.showbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ftn.showbook.database.DatabaseHelper;
import com.example.ftn.showbook.database.FacilityDB;
import com.example.ftn.showbook.model.Facility;
import com.example.ftn.showbook.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity{

    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DatabaseHelper(this);

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

                EditText first_name = (EditText) findViewById(R.id.first_name);
                String first_name_string = first_name.getText().toString();
                EditText last_name = (EditText) findViewById(R.id.last_name);
                String last_name_string = last_name.getText().toString();
                EditText username = (EditText) findViewById(R.id.username);
                String username_string = username.getText().toString();
                EditText password = (EditText) findViewById(R.id.password);
                String password_string = password.getText().toString();
                EditText email = (EditText) findViewById(R.id.email);
                String email_string = email.getText().toString();
                EditText address = (EditText) findViewById(R.id.address);
                String address_string = address.getText().toString();
                Spinner city = (Spinner) findViewById(R.id.city);
                String city_value = city.getSelectedItem().toString();
                Long city_number = 0L;
                if (city_value.equals("Novi Sad")) {
                    city_number = 1L;
                } else if (city_value.equals("Beograd")) {
                    city_number = 2L;
                } else if (city_value.equals("Subotica")) {
                    city_number = 3L;
                } else {
                    city_number = 0L;
                }

                if (first_name_string.length() == 0) {
                    first_name.setError(getResources().getString(R.string.first_name_required));
                }
                if (last_name_string.length() == 0) {
                    last_name.setError(getResources().getString(R.string.last_name_required));
                }
                if (username_string.length() == 0) {
                    username.setError(getResources().getString(R.string.username_required));
                }
                if (password_string.length() == 0) {
                    password.setError(getResources().getString(R.string.password_required));
                }
                if (address_string.length() == 0) {
                    address.setError(getResources().getString(R.string.address_required));
                }
                if(email_string.length()==0) {
                    email.setError(getResources().getString(R.string.email_required));
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(email_string).matches()) {
                    email.setError(getResources().getString(R.string.email_required_form));
                }
                if (city_number.equals(0L)) {
                   /* TextView errorText = (TextView)city.getSelectedView();
                    System.out.println("TextView jeeee " + errorText.toString());
                    errorText.setError("huhu");
                    errorText.setTextColor(getResources().getColor(R.color.colorBusy));//just to highlight that this is an error
                    errorText.setText("my actual error text");*/
                    View view = city.getSelectedView();

                    // Set TextView in Secondary Unit spinner to be in error so that red (!) icon
                    // appears, and then shake control if in error
                    TextView tvListItem = (TextView) view;

                    // Set fake TextView to be in error so that the error message appears
                    TextView tvInvisibleError = (TextView) findViewById(R.id.tvInvisibleError);

                    // Shake and set error if in error state, otherwise clear error
                    tvListItem.setError(getResources().getString(R.string.city_required));
                   // tvListItem.requestFocus();

                    // Shake the spinner to highlight that current selection
                    // is invalid -- SEE COMMENT BELOW
                   // Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                   // city.startAnimation(shake);

                    //tvInvisibleError.requestFocus();
                    tvInvisibleError.setError(getResources().getString(R.string.city_required));



                }
                if(first_name_string.length() != 0 && last_name_string.length() != 0 &&
                        username_string.length() != 0 && password_string.length() != 0 &&
                        address_string.length() != 0 && !city_number.equals(0L) && email_string.length()!=0
                        && Patterns.EMAIL_ADDRESS.matcher(email_string).matches()) {
                    User user = new User();
                    user.setFirstName(first_name_string);
                    user.setLastName(last_name_string);
                    user.setUsername(username_string);
                    user.setPassword(password_string);
                    user.setAddress(address_string);
                    user.setEmail(email_string);
                    Call<User> call = ServiceUtils.pmaService.registr(user, city_number);
                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            Long id = db.insertUser(response.body().getUsername(),response.body().getFirstName(),
                                    response.body().getLastName(), response.body().getAddress(),response.body().getLocation().getName().toString(), response.body().getMaxDistance(),
                                    response.body().getFacilityType().toString(), true);
                           // System.out.println("id " + id);
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.success_message), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            //System.out.println("Error!");
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.fail_message), Toast.LENGTH_SHORT).show();
                        }

                    });

                }
            }
        });



    }
    private void setSpinnerError(Spinner spinner, String error){
        View selectedView = spinner.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {
            spinner.requestFocus();
            TextView selectedTextView = (TextView) selectedView;
            selectedTextView.setError("error"); // any name of the error will do
            selectedTextView.setTextColor(getResources().getColor(R.color.colorBusy)); //text color in which you want your error message to be displayed
            selectedTextView.setText(error); // actual error message
            spinner.performClick(); // to open the spinner list if error is found.

        }
    }


}
