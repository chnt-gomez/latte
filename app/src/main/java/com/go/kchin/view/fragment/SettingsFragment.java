package com.go.kchin.view.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceGroup;
import android.preference.PreferenceManager;

import com.go.kchin.R;

/**
 * Created by MAV1GA on 14/02/2017.
 */

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener{

    public static SettingsFragment newInstance(){
        return new SettingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.app_settings);
        init(getPreferenceScreen());
    }

    private void init(PreferenceGroup pg){
        boolean isEnabled = getPreferenceScreen().getSharedPreferences().getBoolean("protect_with_password", false);
        getPreferenceScreen().findPreference("password").setEnabled(isEnabled);
        for (int i=0; i<pg.getPreferenceCount(); ++i){
            Preference p = pg.getPreference(i);
            if (p instanceof PreferenceGroup){
                init((PreferenceGroup)p);
            }else{
                setSummary(p);
            }
        }

    }

    public void setSummary(Preference pref) {
        if(pref instanceof ListPreference){
            ListPreference lPref = (ListPreference) pref;
            pref.setSummary(lPref.getEntry());
        }
        if (pref instanceof EditTextPreference){
            EditTextPreference edtPref = (EditTextPreference) pref;
            pref.setSummary(edtPref.getText());
        }
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        setSummary(findPreference(key));
        boolean isEnabled = sharedPreferences.getBoolean("protect_with_password", false);
        getPreferenceScreen().findPreference("password").setEnabled(isEnabled);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}
