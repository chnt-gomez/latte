package com.go.kchin.presenter.activities;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.go.kchin.R;
import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.model.DatabaseEngine;
import com.go.kchin.view.fragment.BaseFragment;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.target.ViewTarget;

/**
 * Created by MAV1GA on 06/01/2017.
 */

public class BaseActivity extends AppCompatActivity implements MainMVP.RequiredPresenterOps,
        MainMVP.PresenterOps, MainMVP.PreferenceAccess{

    protected MainMVP.ModelOps mModel;
    protected MainMVP.RequiredViewOps mView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.simple_menu, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mModel != null){
           mModel.onConfigurationChanged(this, this);
        }

    }

    @Override
    public void onOperationSuccess(String message, long rowId) {
        mView.onOperationSuccesfull(message, rowId);
    }

    @Override
    public void onOperationSuccess(String message) {
        mView.onOperationSuccesfull(message);
    }

    @Override
    public void onOperationSuccess(int resource) {
        mView.onOperationSuccesfull(getStringResource(resource));
    }

    @Override
    public void onOperationError(String message) {
        mView.showError(message);
    }

    @Override
    public String getStringResource(int stringResource) {
        return getResources().getString(stringResource);
    }

    @Override
    public SharedPreferences getSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(this);
    }

    protected void init() {
        if (mModel == null) {
            mModel = DatabaseEngine.getInstance(this);
        }
    }

    protected void onNeedTutorial(){

    }


    protected void attachFragment(BaseFragment fragment){
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_holder, fragment, null).commit();
    }

    protected void addFragment(BaseFragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder,
                fragment, null).addToBackStack(null).commit();
    }

    @Override
    public void moveToActivity(Class type, Bundle args) {
        Intent intent = new Intent(this, type);
        startActivity(intent);
    }

    @Override
    public void moveToFragment(BaseFragment fragment) {
        addFragment(fragment);
    }

    @Override
    public void setViewLayer(MainMVP.RequiredViewOps fragment) {
        mView = fragment;
    }

    @Override
    public void setActivityTitle(String title) {
        setTitle(title);
    }

    @Override
    public void hideActionBar() {
        if(getSupportActionBar() != null)
            getSupportActionBar().hide();
    }

    @Override
    public void restoreActionBar() {
        if(getSupportActionBar() != null)
            getSupportActionBar().show();
    }


    @Override
    public boolean ifPasswordProtected() {
        return !isWiFiSecure() && (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("protect_with_password", false));

    }

    @Override
    public boolean isAllowingDepletedStokSales() {
        return PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("allow_depleted_sales", false);
    }

    @Override
    public boolean isAllowingDepletedProduction() {
        return PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("allow_depleted_production", false);
    }

    @Override
    public String getBusinessName() {
        return PreferenceManager.getDefaultSharedPreferences(this)
                .getString("business_name", null);
    }

    @Override
    public String getAdministratorName() {
        return PreferenceManager.getDefaultSharedPreferences(this)
                .getString("username", null);
    }

    @Override
    public boolean authorize(String password) {
        return false;
    }

    @Override
    public boolean isWiFiSecure() {
        if (PreferenceManager.getDefaultSharedPreferences(this)
                .getString("secure_wifi_mac", null) != null){
            //There is a secure wifi connection
            WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            String currentConnection = wifiInfo.getBSSID();
            Log.d("Current connection: ", currentConnection);
            Log.d("Actual secure WiFi: ", PreferenceManager.getDefaultSharedPreferences(this)
                    .getString("secure_wifi_mac", "undefined"));
            return (currentConnection.equals(PreferenceManager.getDefaultSharedPreferences(this)
                    .getString("secure_wifi_mac", "undefined")));
        }
        return false;
    }


}
