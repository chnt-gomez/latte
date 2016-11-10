package com.go.kchin.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.go.kchin.R;
import com.go.kchin.interfaces.FragmentNavigationService;
import com.go.kchin.interfaces.InventoryService;


public class ProductListFragment extends Fragment {

    private InventoryService inventoryService;
    private FragmentNavigationService navigationService;

    private View view;

    public ProductListFragment() {
        // Required empty public constructor
    }

    public static ProductListFragment newInstance() {
        ProductListFragment fragment = new ProductListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_products, container, false);
        init();
        return view;
    }

    private void init() {
        inventoryService.getProducts();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.inventoryService = (InventoryService)context;
        this.navigationService = (FragmentNavigationService)context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        inventoryService = null;
        navigationService = null;
    }
}
