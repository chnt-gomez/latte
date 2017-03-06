package com.go.kchin.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by MAV1GA on 06/03/2017.
 */
public class IntroFragment extends Fragment{

    protected View view;
    protected final static String LAYOUT_RES_ID = "layout_res_id";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(getArguments().getInt(LAYOUT_RES_ID), null);
        init();
        return view;
    }

    protected void init() {
        if (view != null){
            ButterKnife.bind(this, view);
        }
    }

}
