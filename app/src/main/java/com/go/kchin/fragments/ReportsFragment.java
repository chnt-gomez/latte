package com.go.kchin.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.go.kchin.R;
import com.go.kchin.interfaces.FragmentNavigationService;
import com.go.kchin.interfaces.ReportsService;


/**
 * Created by MAV1GA on 19/12/2016.
 */

public class ReportsFragment extends Fragment implements View.OnClickListener{

    private ReportsService reportsService;
    private FragmentNavigationService navigationService;
    private View fragmentLayout;
    private Button btnQuickReport;

    public static ReportsFragment newInstance(){
        ReportsFragment fragment = new ReportsFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentLayout = inflater.inflate(R.layout.fragment_reports, null);
        init();
        return fragmentLayout;

    }

    private void addToClickListener (View ... params){
        for (View v : params){
            v.setOnClickListener(this);
        }
    }

    private void init(){
        btnQuickReport = (Button)fragmentLayout.findViewById(R.id.btn_quick_report);
        addToClickListener(btnQuickReport);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        reportsService = (ReportsService)context;
        navigationService = (FragmentNavigationService)context;

    }

    @Override
    public void onDetach() {
        reportsService = null;
        navigationService = null;
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_quick_report:
                navigationService.moveToFragment(QuickReportFragment.newIntance(), true);

        }
    }
}
