package com.go.kchin.view.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.go.kchin.R;
import com.go.kchin.interfaces.LoaderRequiredOps;
import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.util.dialog.Dialogs;
import butterknife.ButterKnife;

/**
 * Created by MAV1GA on 06/01/2017.
 */

public class BaseFragment extends Fragment implements MainMVP.RequiredViewOps, View.OnClickListener,
        LoaderRequiredOps{

    protected View view;
    protected MainMVP.PresenterOps mPresenter;


    protected final static String LAYOUT_RES_ID = "layout_res_id";

    @Override
    public void onOperationSuccesfull(String message, @Nullable final long rowId) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).setAction(
                R.string.see, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onOperationResultClick(rowId);
                    }
                }
        ).show();
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
        Snackbar.make(view, getResources().getString(R.string.added_to_kart), Snackbar.LENGTH_SHORT).show();
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
    public void onResume() {
        super.onResume();
        mPresenter.setViewLayer(this);
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

    @Override
    public void onDetach() {
        mPresenter = null;
        Log.d(getClass().getSimpleName(), "Fragment was detached");
        super.onDetach();
    }

    @Override
    public void onClick(View v) {}

    @Override
    public void onPreLoad() {
        Dialogs.buildLoadingDialog(getContext(), "Loading...").show();
    }

    @Override
    public void onLoad() {}

    @Override
    public void onDoneLoading() {
        Dialogs.dismiss();
    }

}
