package com.go.kchin.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.go.kchin.R;
import com.go.kchin.adapters.PackageListAdapter;
import com.go.kchin.models.Operation;
import com.go.kchin.models.Package;
import com.go.kchin.util.Util;

import java.util.Collection;
import java.util.List;

/**
 * Created by MAV1GA on 28/11/2016.
 */

public class PackageListFragment extends InventoryListFragment implements Util.PackageDialogEventListener{


    public static final long ALL_PACKAGES = -1;
    public static final String PRODUCT_ID = "product_id";

    private List<com.go.kchin.models.Package> items;
    private PackageListAdapter adapter;

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
        adapter = new PackageListAdapter(getActivity(), R.layout.row_package_item, items);
        listView.setAdapter(adapter);
    }

    @Override
    protected void add() {
        Util.showNewPackageDialog("New package", null, getActivity(), this, getActivity().getLayoutInflater())
        .show();
    }

    @Override
    public Operation returnPackage(Package aPackage) {
        Operation operation = inventoryService.addPackage(aPackage);
        updateItemList(operation.getPackages());
        return null;
    }

    @Override
    protected void updateItemList(List<?> items) {
        this.adapter.clear();
        adapter.addAll((Collection<? extends Package>) items);
        listView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        navigationService.moveToFragment(PackageDetailFragment.newInstance(adapter.getItem(position).getPackageId()), true);
    }
}
