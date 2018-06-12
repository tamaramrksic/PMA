package com.example.ftn.showbook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.example.ftn.showbook.database.DatabaseHelper;
import com.example.ftn.showbook.database.UserDB;
import com.example.ftn.showbook.model.User;
import com.example.ftn.showbook.model.UserPreferences;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    private Intent intent;
    private DatabaseHelper db;
    private UserDB userDB;
    private UserPreferences userPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
        intent = getActivity().getIntent();

        getActivity().setTitle(getResources().getString(R.string.nav_settings_label));

        db = new DatabaseHelper(getActivity());
        String username = intent.getStringExtra("drawerUsername");
        userDB = db.getUserByUsername(username);

        ListPreference maxDistance = (ListPreference)findPreference("pref_distance");
        maxDistance.setValue(userDB.getMaxDistance().toString());
        ListPreference facilityType = (ListPreference )findPreference("pref_facility_type");
        facilityType.setValue(userDB.getFacilityType());
        CheckBoxPreference showNotification =(CheckBoxPreference) findPreference(getString(R.string.pref_notification_key));
        showNotification.setChecked(userDB.isComment_notification());

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

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

    }
    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if (s.equals("pref_distance")) {
            String distance = sharedPreferences.getString(s,"");
            db.updatePreferences(userDB.getUsername(),Integer.parseInt(distance), null, null);
            UserPreferences userPreferences = new UserPreferences(Integer.parseInt(distance), null, null);
            Call<ResponseBody> call = ServiceUtils.pmaService.changePrefernces(userPreferences, userDB.getUsername());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                }

            });
        }else if(s.equals("pref_facility_type")) {
            String facilityType = sharedPreferences.getString(s,"");
            User.FacilityType  facilityType1 = User.FacilityType.ALL;
            if(facilityType.equals("ALL")){
                facilityType1 = User.FacilityType.ALL;
            }else if(facilityType.equals("CINEMA")){
                facilityType1 = User.FacilityType.CINEMA;
            }else if(facilityType.equals("THEATER")){
                facilityType1 = User.FacilityType.THEATER;
            }
            db.updatePreferences(userDB.getUsername(),null, facilityType, null);
            UserPreferences userPreferences = new UserPreferences(null, null,facilityType1);
            Call<ResponseBody> call = ServiceUtils.pmaService.changePrefernces(userPreferences, userDB.getUsername());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                }

            });
        }else if(s.equals(getString(R.string.pref_notification_key))){
            Boolean comment = sharedPreferences.getBoolean(s, false);
            db.updatePreferences(userDB.getUsername(),null, null,comment);
            UserPreferences userPreferences = new UserPreferences(null, comment,null);
            Call<ResponseBody> call = ServiceUtils.pmaService.changePrefernces(userPreferences, userDB.getUsername());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                }

            });
        }
    }
}
