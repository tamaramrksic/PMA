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
import com.example.ftn.showbook.model.UserCredentials;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassFragment  extends DialogFragment {
    LayoutInflater inflater;
    private String new_password_value;
    Intent intent;
      @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        inflater = getActivity().getLayoutInflater();
        builder.setTitle(R.string.change_pass);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.fragment_change_pass, null))
                // Add action buttons
                .setPositiveButton(R.string.change_pass_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        intent = getActivity().getIntent();
                        String username = intent.getStringExtra("drawerUsername");
                        String password = intent.getStringExtra("drawerPass");
                        System.out.println("USERNAME JE : "+ username + " a password je : " + password);
                        EditText old_password = (EditText) getDialog().findViewById(R.id.old_password);
                        String old_password_value = old_password.getText().toString();
                        EditText new_password = (EditText) getDialog().findViewById(R.id.new_password);
                        new_password_value = new_password.getText().toString();

                        EditText conf_password = (EditText) getDialog().findViewById(R.id.conf_password);
                        String conf_password_value = conf_password.getText().toString();
                        System.out.println("PASS novi JE : "+ new_password_value + " conf pass je  " + conf_password_value);
                        if(password.equals(old_password_value)&& new_password_value.equals(conf_password_value) && new_password_value.length()>2 ) {
                            UserCredentials userCredentials = new UserCredentials(username, new_password_value);

                            Call<User> call = ServiceUtils.pmaService.changePass(userCredentials);
                            call.enqueue(new Callback<User>() {
                                @Override
                                public void onResponse(Call<User> call, Response<User> response) {
                                    Toast.makeText(inflater.getContext(), R.string.success_message, Toast.LENGTH_SHORT).show();
                                    intent.putExtra("drawerPass", new_password_value);

                                }

                                @Override
                                public void onFailure(Call<User> call, Throwable t) {
                                    Toast.makeText(inflater.getContext(), R.string.fail_message, Toast.LENGTH_SHORT).show();
                                }


                            });
                        }else {
                            Toast.makeText(inflater.getContext(), R.string.fail_pass_message, Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ChangePassFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
