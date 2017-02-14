package com.go.kchin.view.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.go.kchin.R;

/**
 * Created by MAV1GA on 14/02/2017.
 */

public class SettingsFragment extends PreferenceFragment {

    public static SettingsFragment newInstance(){
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.app_settings);
        init();
    }

    private void init(){

    }



}
