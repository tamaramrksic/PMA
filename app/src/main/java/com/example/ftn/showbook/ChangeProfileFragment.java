package com.example.ftn.showbook;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ftn.showbook.model.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeProfileFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setTitle(R.string.change_profile);





        Intent intent = getActivity().getIntent();
        //Call<User> call = ServiceUtils.pmaService.getUser(intent.getStringExtra("drawerUsername"));
        Call<User> call = ServiceUtils.pmaService.getUser("Ana");
        call.enqueue(new Callback<User>() {
                         @Override
                         public void onResponse(Call<User> call, Response<User> response) {
                             System.out.println("Uspeh!");
                             EditText first_name = getDialog().findViewById(R.id.first_name);
                             EditText last_name = getDialog().findViewById(R.id.last_name);
                             EditText username = getDialog().findViewById(R.id.username);
                             EditText address = getDialog().findViewById(R.id.address);

                             System.out.println(response.body().getFirstName());
                             System.out.println(response.body().getLastName());

                             first_name.setText(response.body().getFirstName());
                             last_name.setText(response.body().getLastName());
                             username.setText(response.body().getUsername());
                             address.setText(response.body().getAddress());
                         }

                         @Override
                         public void onFailure(Call<User> call, Throwable t) {
                             System.out.println(t.getCause());
                             System.out.println(t.getStackTrace());
                             System.out.println(t.getMessage());
                             System.out.println("Error!");
                             Toast.makeText(inflater.getContext(), getResources().getString(R.string.fail_message), Toast.LENGTH_SHORT).show();
                             ChangeProfileFragment.this.getDialog().cancel();
                         }
                     });



//
//        String username_value = intent.getStringExtra("drawerUsername");
//        String first_name_value = intent.getStringExtra("userFirstName");
//        String last_name_value = intent.getStringExtra("userLastName");
//        String address_value = intent.getStringExtra("userAddress");


//        EditText first_name = getDialog().findViewById(R.id.first_name);
//        String first_name_value = first_name.getText().toString();
//        EditText last_name = getDialog().findViewById(R.id.last_name);
//        String last_name_value = last_name.getText().toString();
//        EditText address = getDialog().findViewById(R.id.address);
//        String address_value = address.getText().toString();


        builder.setView(inflater.inflate(R.layout.fragment_change_profile, null))
                .setPositiveButton(R.string.change_pass_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        ChangeProfileFragment.this.getDialog().cancel();
//                        User user = new User();
//                        EditText first_name = getDialog().findViewById(R.id.first_name);
//                        String first_name_new_value = first_name.getText().toString();
//                        EditText last_name = getDialog().findViewById(R.id.last_name);
//                        String last_name_new_value = last_name.getText().toString();
//                        EditText username = getDialog().findViewById(R.id.username);
//                        String username_new_value = last_name.getText().toString();
//                        EditText address = getDialog().findViewById(R.id.address);
//                        String address_new_value = address.getText().toString();
//
//                        user.setFirstName(first_name_new_value);
//                        user.setLastName(last_name_new_value);
//                        user.setUsername(username_new_value);
//                        user.setAddress(address_new_value);
//
//
//                        Call<ResponseBody> call = ServiceUtils.pmaService.registr(user, city_number);
//                        call.enqueue(new Callback<ResponseBody>() {
//                            @Override
//                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.success_message), Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                                startActivity(intent);
//                            }
//
//                            @Override
//                            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                                System.out.println("Error!");
//                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.fail_message), Toast.LENGTH_SHORT).show();
//                            }
//
                    }

                        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ChangeProfileFragment.this.getDialog().cancel();
                    }
        });
        return builder.create();
    }
}
