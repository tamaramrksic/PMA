package com.example.ftn.showbook;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ftn.showbook.model.Location;
import com.example.ftn.showbook.model.User;
import com.example.ftn.showbook.model.UserCredentials;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeProfileFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setTitle(R.string.change_profile);

        final Intent intent = getActivity().getIntent();
        UserCredentials userCredentials = new UserCredentials(intent.getStringExtra("drawerUsername"), "");
        Call<User> call = ServiceUtils.pmaService.getUser(userCredentials);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                EditText first_name = getDialog().findViewById(R.id.first_name);
                EditText last_name = getDialog().findViewById(R.id.last_name);
                EditText address = getDialog().findViewById(R.id.address);
                Spinner city = getDialog().findViewById(R.id.city);

                first_name.setText(response.body().getFirstName());
                last_name.setText(response.body().getLastName());
                address.setText(response.body().getAddress());
                Location location = response.body().getLocation();
                if(location.getName().equals("Novi Sad")) {
                    city.setSelection(0);
                } else if(location.getName().equals("Beograd")) {
                    city.setSelection(1);
                } else if(location.getName().equals("Subotica")) {
                    city.setSelection(2);
                }

                intent.putExtra("userId", response.body().getId());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(inflater.getContext(), R.string.fail_message, Toast.LENGTH_SHORT).show();
                ChangeProfileFragment.this.getDialog().cancel();
            }
        });

        builder.setView(inflater.inflate(R.layout.fragment_change_profile, null))
                .setPositiveButton(R.string.change_pass_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        User user = new User();

                        EditText first_name = getDialog().findViewById(R.id.first_name);
                        String first_name_new_value = first_name.getText().toString();
                        EditText last_name = getDialog().findViewById(R.id.last_name);
                        String last_name_new_value = last_name.getText().toString();
                        EditText address = getDialog().findViewById(R.id.address);
                        String address_new_value = address.getText().toString();
                        Spinner city = getDialog().findViewById(R.id.city);
                        String city_new_value = city.getSelectedItem().toString();

                        user.setFirstName(first_name_new_value);
                        user.setLastName(last_name_new_value);
                        user.setAddress(address_new_value);

                        Call<User> call = ServiceUtils.pmaService.updateUser(user, intent.getLongExtra("userId", -1L), city_new_value);
                        call.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                Toast.makeText(inflater.getContext(), R.string.success_message, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                Toast.makeText(inflater.getContext(), R.string.fail_message, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ChangeProfileFragment.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }
}
