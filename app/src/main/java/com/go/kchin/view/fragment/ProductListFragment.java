package com.go.kchin.view.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.go.kchin.R;
import com.go.kchin.adapters.ProductListAdapter;
import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.util.dialog.Dialogs;

/**
 * Created by MAV1GA on 09/01/2017.
 */

public class ProductListFragment extends BaseFragment {


    private ListView listView;
    private ProductListAdapter adapter;
    private MainMVP.ProductsPresenterOps mProductsPresenter;

    public static ProductListFragment newInstance(){
        ProductListFragment fragment = new ProductListFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(LAYOUT_RES_ID, R.layout.fragment_inventory );
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mProductsPresenter = (MainMVP.ProductsPresenterOps)context;
    }

    @Override
    public void onDetach() {
        mProductsPresenter = null;
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btn_add:
                mProductsPresenter.newProduct();
                break;
        }
    }

    @Override
    protected void init() {
        super.init();
        listView = (ListView)view.findViewById(R.id.lv_inventory);
        adapter = new ProductListAdapter(getContext(), R.layout.row_product_item,
                mProductsPresenter.getAllProducts());
        try{
            updateListView();
        }catch (Exception e){
            Log.w(getClass().getSimpleName(), e.getMessage());
        }

        FloatingActionButton btnAdd = (FloatingActionButton) view.findViewById(R.id.btn_add);
        addToClickListener(btnAdd);
    }

    private void updateListView(){
        if (adapter != null && listView != null) {
            listView.setAdapter(adapter);
        }else{
            throw new RuntimeException("Either adapter or listview is not correctly initialized");
        }
    }
}
