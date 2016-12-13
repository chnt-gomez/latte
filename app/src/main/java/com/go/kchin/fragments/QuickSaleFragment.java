package com.go.kchin.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.go.kchin.R;
import com.go.kchin.adapters.SaleAdapter;
import com.go.kchin.interfaces.FragmentNavigationService;
import com.go.kchin.interfaces.SalesService;


public class QuickSaleFragment extends Fragment implements View.OnClickListener, View.OnLongClickListener {

    private View view;
    private SalesService salesService;
    private FragmentNavigationService navigationService;
    private SaleAdapter adapter;
    private FloatingActionButton sellButton;
    private ListView listView;

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

    @Override
    public void onResume() {
        super.onResume();
        listView.setAdapter(adapter);

    }

    private void init() {
        sellButton = (FloatingActionButton)findViewById(R.id.btn_sell);
        listView = (ListView)findViewById(R.id.lv_sale);
        sellButton.setOnClickListener(this);
        sellButton.setOnLongClickListener(this);
        adapter = new SaleAdapter(getActivity(), R.layout.row_sell_item, salesService.getCurrentSale());
        listView.setAdapter(adapter);

    }

    private View findViewById(int res){
        return view.findViewById(res);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.salesService = (SalesService)context;
        this.navigationService = (FragmentNavigationService)context;
    }

    @Override
    public void onDetach() {
        this.salesService = null;
        this.salesService = null;
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_sell:
                navigationService.moveToFragment(ProductListFragment.newInstanceForSale(), true);
        }
    }


    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()){
            case R.id.btn_sell:
                salesService.applySale();
                break;
        }
        return true;
    }
}
