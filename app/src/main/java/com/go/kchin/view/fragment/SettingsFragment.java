package com.go.kchin.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceGroup;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.go.kchin.R;
import com.go.kchin.interfaces.RequiredDialogOps;
import com.go.kchin.util.utilities.Dialogs;

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
        final Preference secureWiFiPref = (Preference) findPreference("secure_wifi_mac");
        secureWiFiPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

            Dialogs.newSecureWifiDialog(getActivity(), new RequiredDialogOps.RequiredSecureWifi() {
                @Override
                public void onSetMacAddress(String macAddress) {
                    secureWiFiPref.getEditor().putString("secure_wifi_mac", macAddress).apply();
                    Snackbar.make(getView(), getString(R.string.secure_wifi_set), Snackbar.LENGTH_LONG)
                            .show();
                }

                @Override
                public void onHelpClick() {
                    showSecureWifiHelp();
                }

                @Override
                public void onDeleteMacAddress() {
                    secureWiFiPref.getEditor().remove("secure_wifi_mac").apply();
                    Snackbar.make(getView(), getString(R.string.secure_wifi_deleted), Snackbar.LENGTH_LONG)
                            .show();
                }

                @Override
                public void onNoNetwork() {
                    Snackbar snack = Snackbar.make(getView(),
                            getString(R.string.please_connect_to_secure_wifi), Snackbar.LENGTH_LONG);
                    TextView tv = (TextView)snack.getView().findViewById((android.support.design.R.id.snackbar_text));
                    tv.setTextColor(Color.parseColor("#ff7f7f"));
                    snack.show();

                }
            }).show();

            return true;
            }
        });
    }

    private void showSecureWifiHelp(){
        getFragmentManager().beginTransaction().replace(R.id.fragment_holder,
                HelpWifiFragment.newInstance(), null).addToBackStack(null).commit();
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
        getPreferenceScreen().findPreference("secure_wifi_mac").setEnabled(isEnabled);
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
