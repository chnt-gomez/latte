package com.go.kchin.view.fragment;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.go.kchin.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by MAV1GA on 04/04/2017.
 */

public class HelpWifiFragment extends Fragment {

    private View fragmentView;

    public static HelpWifiFragment newInstance(){
        HelpWifiFragment fragment = new HelpWifiFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_help_wifi, null);
        if (fragmentView != null){
            ButterKnife.bind(this, fragmentView);
        }
        return fragmentView;
    }

    @OnClick(R.id.btn_set)
    public void onSet(View v){

        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null && activeNetwork.getType() == ConnectivityManager.TYPE_WIFI
                && activeNetwork.isConnected()) {

            WifiManager wifiMan = (WifiManager) getActivity().getSystemService(
                    Context.WIFI_SERVICE);
            WifiInfo wifiInf = wifiMan.getConnectionInfo();
            String macAddr = wifiInf.getBSSID();
            PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().
                    putString("secure_wifi_mac", macAddr).apply();
            Snackbar.make(fragmentView, getString(R.string.secure_wifi_set), Snackbar.LENGTH_LONG)
                    .show();
        }else{

            Snackbar snack = Snackbar.make(fragmentView, getString(R.string.please_connect_to_secure_wifi), Snackbar.LENGTH_LONG);
            TextView tv = (TextView)snack.getView().findViewById((android.support.design.R.id.snackbar_text));
            tv.setTextColor(Color.parseColor("#ff7f7f"));
            snack.show();
        }


    }

}
