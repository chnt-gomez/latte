package com.go.kchin.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.go.kchin.R;
import com.go.kchin.adapters.ProductListAdapter;
import com.go.kchin.database.ProductInPackageContract;
import com.go.kchin.models.Product;
import com.go.kchin.util.Util;

import java.util.List;

/**
 * Created by MAV1GA on 29/11/2016.
 */

public class PackageDetailFragment extends InventoryDetailFragment {

    private com.go.kchin.models.Package localPackage;
    private EditText edtPackageName;
    private Button btnSalePrice, btnAddProducts;
    List<Product> items;
    private ListView listView;
    ProductListAdapter adapter;

    public PackageDetailFragment(){}

    public static PackageDetailFragment newInstance(long packageId){
        PackageDetailFragment fragment = new PackageDetailFragment();
        Bundle args = new Bundle();
        args.putLong(OBJECT_ID, packageId);
        args.putInt(LAYOUT_RES, R.layout.fragment_package_detail);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            objectId = getArguments().getLong(OBJECT_ID);
        }
    }

    @Override
    protected void init() {
        super.init();
        localPackage = inventoryService.getPackage(objectId);
        items = inventoryService.getProductsFromPackage(objectId);
        listView = (ListView)findViewById(R.id.lv_products_in_package);
        adapter = new ProductListAdapter(getActivity(), R.layout.row_product_item, items);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                inventoryService.delete(ProductInPackageContract.TABLE_NAME, getArguments().getLong(OBJECT_ID),
                        adapter.getItem(position).getProductId());
                return true;
            }
        });
        edtPackageName = (EditText)findViewById(R.id.edt_package_name);
        btnSalePrice = (Button)findViewById(R.id.btn_sale_price);
        btnAddProducts = (Button)findViewById(R.id.btn_add_product);
        btnSalePrice.setText(Util.fromFloat(localPackage.getPrice()));
        edtPackageName.setText(localPackage.getName());
        addToClickListener(btnAddProducts, btnSalePrice);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btn_sale_price:
                Util.editAmountDialog("Change price", null, getActivity(), getActivity().getLayoutInflater(), new Util.UtilDialogEventListener() {
                    @Override
                    public void returnFloat(float arg) {
                        btnSalePrice.setText(Util.fromFloat(arg));
                    }

                    @Override
                    public void returnString(String arg) {

                    }

                    @Override
                    public void returnLong(long arg) {

                    }
                }).show();
                break;
            case R.id.btn_add_product:
                navigationService.moveToFragment(ProductListFragment.newInstanceForPakage(localPackage.getPackageId()), true);
                break;
        }
    }

    @Override
    protected void save() {
        float newPrice = Util.toFloat(btnSalePrice.getText().toString());
        String newPackageName = edtPackageName.getEditableText().toString();
        this.localPackage.setName(newPackageName);
        this.localPackage.setPrice(newPrice);
        inventoryService.updatePackage(objectId, localPackage);
        Snackbar.make(fragmentView, "Package saved", Snackbar.LENGTH_SHORT).show();
    }
}
