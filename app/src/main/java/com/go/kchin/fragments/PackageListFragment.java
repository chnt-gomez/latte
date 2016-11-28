package com.go.kchin.fragments;

import android.os.Bundle;

import java.util.List;

/**
 * Created by MAV1GA on 28/11/2016.
 */

public class PackageListFragment extends InventoryListFragment {


    public static final long ALL_PACKAGES = -1;
    public static final String PRODUCT_ID = "product_id";

    private List<com.go.kchin.models.Package> items;

    public static PackageListFragment newInstance(long productId){
        PackageListFragment fragment = new PackageListFragment();
        Bundle args = new Bundle();
        if (productId != ALL_PACKAGES)
            args.putLong(PRODUCT_ID, productId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        navigationService.setActionBarTitle("Packages");
    }

    @Override
    protected void init() {
        super.init();
        items = inventoryService.getPackages();

    }


}
