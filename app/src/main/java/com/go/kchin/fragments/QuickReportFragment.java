package com.go.kchin.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.go.kchin.R;

/**
 * Created by MAV1GA on 19/12/2016.
 */

public class QuickReportFragment extends Fragment {


    private View fragmentLayout;

    public static QuickReportFragment newIntance(){
        return new QuickReportFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentLayout = inflater.inflate(R.layout.fragment_quick_report,null);
        init();


        return fragmentLayout;

    }

    private void init(){

    }

}
