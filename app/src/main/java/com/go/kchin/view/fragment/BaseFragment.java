package com.go.kchin.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.go.kchin.interfaces.MainMVP;

/**
 * Created by MAV1GA on 06/01/2017.
 */

public class BaseFragment extends Fragment implements MainMVP.RequiredViewOps, View.OnClickListener {

    protected View view;
    protected MainMVP.PresenterOps mPresenter;


    protected final static String LAYOUT_RES_ID = "layout_res_id";

    @Override
    public void showSnackBar(String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showAlert(String msg) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPresenter = (MainMVP.PresenterOps)context;
    }

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

    protected void init() {}

    @Override
    public void onDetach() {
        mPresenter = null;
        super.onDetach();
    }

    @Override
    public void onClick(View v) {

    }
}
