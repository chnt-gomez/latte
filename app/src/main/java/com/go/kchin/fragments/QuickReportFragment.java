package com.go.kchin.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.go.kchin.R;
import com.go.kchin.adapters.ReportAdapter;
import com.go.kchin.interfaces.ReportsService;

/**
 * Created by MAV1GA on 19/12/2016.
 */

public class QuickReportFragment extends Fragment {


    private View fragmentLayout;
    private ReportsService reportsService;
    private ReportAdapter adapter;
    private ListView listView;

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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        reportsService = (ReportsService)context;
    }

    @Override
    public void onDetach() {
        reportsService = null;
        super.onDetach();
    }

    private class ReportLoader extends AsyncTask<Void, Integer, ReportAdapter>{

        @Override
        protected ReportAdapter doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(ReportAdapter reportAdapter) {
            super.onPostExecute(reportAdapter);
            adapter = reportAdapter;
            listView.setAdapter(adapter);
        }
    }
}
