package com.go.kchin.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.go.kchin.R;
import com.go.kchin.interfaces.MainMVP;

import butterknife.ButterKnife;

/**
 * Created by MAV1GA on 06/01/2017.
 */

public class BaseFragment extends Fragment implements MainMVP.RequiredViewOps, View.OnClickListener{

    protected View view;
    protected MainMVP.PresenterOps mPresenter;


    protected final static String LAYOUT_RES_ID = "layout_res_id";

    @Override
    public void showOperationResult(String message, final long rowId) {
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
    public void showSnackBar(String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showAlert(String msg) {

    }

    protected void onOperationResultClick(long rowId){

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

    protected void init() {
        if (view != null){
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public void onDetach() {
        mPresenter = null;
        super.onDetach();
    }

    @Override
    public void onClick(View v) {

    }
}
