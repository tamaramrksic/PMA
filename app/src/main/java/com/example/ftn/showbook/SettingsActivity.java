package com.example.ftn.showbook;

import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.app.DialogFragment;
import android.os.Bundle;


public class SettingsActivity extends PreferenceActivity {

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        Preference buttonChangePassword = findPreference(getString(R.string.pref_change_password));
        buttonChangePassword.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                DialogFragment dialog = new ChangePassFragment();
                dialog.show(getFragmentManager(), "ChangePassword");
                return true;
            }
        });

        Preference buttonChangeProfile = findPreference(getString(R.string.pref_change_profile));
        buttonChangeProfile.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                DialogFragment dialog = new ChangeProfileFragment();
                dialog.show(getFragmentManager(), "ChangeProfile");
                return true;
            }
        });
    }
}