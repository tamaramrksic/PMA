package com.example.ftn.showbook;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

public class ChangeProfileFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setTitle(R.string.change_profile);

        builder.setView(inflater.inflate(R.layout.fragment_change_profile, null))
                .setPositiveButton(R.string.change_pass_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        ChangeProfileFragment.this.getDialog().cancel();
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
