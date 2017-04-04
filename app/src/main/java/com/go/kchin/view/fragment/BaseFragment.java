package com.go.kchin.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.go.kchin.R;
import com.go.kchin.interfaces.LoaderRequiredOps;
import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.interfaces.RequiredDialogOps;
import com.go.kchin.util.utilities.Dialogs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;

/**
 * Created by MAV1GA on 06/01/2017.
 */

public class BaseFragment extends Fragment implements MainMVP.RequiredViewOps, View.OnClickListener,
        LoaderRequiredOps, RequiredDialogOps.RequiredPasswordOps{

    protected View view;
    protected MainMVP.PresenterOps mPresenter;
    private List<View> editListenerItems;
    protected MaterialShowcaseView showCaseView;

    protected static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;

    protected final static String LAYOUT_RES_ID = "layout_res_id";

    @Override
    public void onOperationSuccesfull(String message, final long rowId) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).setAction(
                R.string.see, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onOperationResultClick(rowId);
                    }
                }
        ).show();
    }

    protected boolean hasPermission(String permission){
        return ContextCompat.checkSelfPermission(getActivity(),
                permission)
                == PackageManager.PERMISSION_GRANTED;
    }

    protected void requestPermission(String permission, int requestCode){
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{permission},
                requestCode);
    }

    protected SharedPreferences getPrefs(){
        return PreferenceManager.getDefaultSharedPreferences(getContext());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            onPermissionGranted();
        }else{
            onPermissionDenied();
        }
    }

    protected void onRequestEdit(){
        if (isPasswordProtected()) {
            Dialogs.newPasswordDialog(getContext(), getString(R.string.password),
                    getString(R.string.content_protected_summary), this ).show();
        }else{
            enableEditMode();
        }
    }

    protected void enableEditMode() {
        for (View v : editListenerItems){
            v.setEnabled(true);
        }
    }

    @Override
    public void onPause() {
        if (showCaseView != null && showCaseView.isShown())
            showCaseView.hide();
        super.onPause();
    }



    @Override
    public void onOperationSuccesfull(String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showMessage(String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(int resourceString) {
        Snackbar.make(view, getResources().getString(resourceString), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String msg) {
        Snackbar snack = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT);
        TextView tv = (TextView)snack.getView().findViewById((android.support.design.R.id.snackbar_text));
        tv.setTextColor(Color.parseColor("#ff7f7f"));
        snack.show();
    }

    @Override
    public void onOperationError(String msg, final long rowId) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).setAction(
                R.string.see, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onOperationResultClick(rowId);
                    }
                }
        ).show();
    }

    @Override
    public void search(String query) {

    }

    @Override
    public void onShowTutorial() {
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.setViewLayer(this);
        onShowTutorial();
    }

    protected void onOperationResultClick(long rowId){}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPresenter = (MainMVP.PresenterOps)context;
    }

    protected void onOperationErrorResultClick(long rowId){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(getArguments().getInt(LAYOUT_RES_ID), null);
        init();
        return view;
    }

    protected void addToClickListener(View ... params){
        for (View v : params){
            v.setOnClickListener(this);
        }
    }

    protected void init() {
        if (view != null){
            ButterKnife.bind(this, view);
        }
    }

    protected MaterialShowcaseView buildView(int viewId,
                                             int stringRes){
        showCaseView = new MaterialShowcaseView.Builder(getActivity())
                .setTarget(view.findViewById(viewId))
                .setContentText(getString(stringRes))
                .setDismissOnTouch(true)
                .setMaskColour(ContextCompat.getColor(getContext(),
                        R.color.colorDarkGrayBlue))
                .build();
        return showCaseView;
    }

    protected MaterialShowcaseView buildSquareView(int viewId,
                                             int stringRes){
        showCaseView = new MaterialShowcaseView.Builder(getActivity())
                .setTarget(view.findViewById(viewId))
                .setContentText(getString(stringRes))
                .withRectangleShape()
                .setDismissOnTouch(true)
                .setMaskColour(ContextCompat.getColor(getContext(),
                        R.color.colorDarkGrayBlue))
                .build();
        return showCaseView;
    }

    protected MaterialShowcaseView buildSquareView(View view,
                                                   int stringRes){
        showCaseView = new MaterialShowcaseView.Builder(getActivity())
                .setTarget(view)
                .setContentText(getString(stringRes))
                .withRectangleShape()
                .setDismissOnTouch(true)
                .setMaskColour(ContextCompat.getColor(getContext(),
                        R.color.colorDarkGrayBlue))
                .build();
        return showCaseView;
    }

    protected MaterialShowcaseView buildView(View view,
                                             int stringRes){

        showCaseView = new MaterialShowcaseView.Builder(getActivity())
                .setTarget(view)
                .setContentText(getString(stringRes))
                .setDismissOnTouch(true)
                .setMaskColour(ContextCompat.getColor(getContext(),
                        R.color.colorDarkGrayBlue))
                .build();
        return showCaseView;
    }

    @Override
    public void onDetach() {
        mPresenter = null;
        super.onDetach();
    }

    @Override
    public void onClick(View v) {}

    @Override
    public void onPreLoad() {
        Dialogs.buildLoadingDialog(getContext(), getResources().getString(R.string.loading)).show();
    }

    @Override
    public void onLoad() {}

    @Override
    public void onDoneLoading() {
        Dialogs.dismiss();
    }

    @Override
    public void onSearch(String query) {

    }

    protected void addToEditListener(View ... v){
        if (editListenerItems == null){
            editListenerItems = new ArrayList<>();
        }
        Collections.addAll(editListenerItems, v);
    }

    protected boolean isPasswordProtected() {
        return !isWiFiSecure() && (PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean("protect_with_password", false));
    }

    public boolean isWiFiSecure() {
        if (PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getString("secure_wifi_mac", null) != null){
            //There is a secure wifi connection
            WifiManager wifiManager = (WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            String currentConnection = wifiInfo.getBSSID();
            return (currentConnection.equals(PreferenceManager.getDefaultSharedPreferences(getActivity())
                    .getString("secure_wifi_mac", "undefined")));
        }
        return false;
    }

    protected boolean isAllowingDepletedStokSales() {
        return PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getBoolean("allow_depleted_sales", false);
    }

    protected boolean isAllowingDepletedProduction() {
        return PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getBoolean("allow_depleted_production", false);
    }

    protected boolean isActiveTracking() {
        return PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getBoolean("active_tracking", false);
    }

    protected String getBusinessName() {
        return PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getString("business_name", null);
    }

    protected String getAdministratorName() {
        return PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getString("username", null);
    }

    protected  boolean authorize(String password) {
        return false;
    }

    @Override
    public void isAuthorized(boolean isAuthorized) {
        if (isAuthorized) {
            enableEditMode();
        }else{
            showError(getString(R.string.unauthorized));
        }
    }

    @Override
    public void recoverPassword() {
        showMessage(getString(R.string.recover_password));
    }

    public void onPermissionDenied() {

    }

    public void onPermissionGranted(){

    }

}
