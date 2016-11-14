package com.go.kchin.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.go.kchin.R;

/**
 * Created by MAV1GA on 14/11/2016.
 */

public class DepartmentListFragment extends Fragment {

    private ListView lv_list;
    private View view;

    public DepartmentListFragment(){}

    public DepartmentListFragment newInstance(){
        return new DepartmentListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_department_list, null);
        init();
        return view;
    }

    private void init() {


    }
}
