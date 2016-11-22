package com.go.kchin.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.go.kchin.R;
import com.go.kchin.interfaces.SalesService;

/**
 * Created by MAV1GA on 22/11/2016.
 */

public class QuickSaleFragment extends Fragment {

    private View view;
    private SalesService salesService;

    public QuickSaleFragment(){}

    public static QuickSaleFragment newInstance(){
        return new QuickSaleFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_quick_sale, null);
        init();
        return view;
    }

    private void init() {

    }

    private View findViewById(int res){
        return view.findViewById(res);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.salesService = (SalesService)context;
    }
}
