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

        Preference button = findPreference(getString(R.string.pref_change_profile));
        button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                DialogFragment dialog = new ChangePassFragment();
                dialog.show(getFragmentManager(), "ChangePassword");
                return true;
            }
        });
    }
}