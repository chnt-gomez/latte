package com.go.kchin.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.go.kchin.R;
import com.go.kchin.adapters.ProductListAdapter;
import com.go.kchin.models.Product;
import com.go.kchin.util.Util;

import java.util.List;


public class ProductListFragment extends InventoryListFragment implements
        Util.ProductDialogEventListener{

    public static final long ALL_PRODUCTS = -1;
    private ProductListAdapter adapter;
    protected static final String MATERIAL_ID = "material_id";

    public ProductListFragment() {}

    public static ProductListFragment newInstance(long materialId) {
        ProductListFragment fragment = new ProductListFragment();
        Bundle args = new Bundle();
        args.putInt(LAYOUT_RES, R.layout.fragment_product_list);
        args.putInt(LIST_VIEW_ID, R.id.lv_products_list_view);
        if (materialId != -1) {
            args.putLong(MATERIAL_ID, materialId);
        }
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_product_list, container, false);
        init();
        return view;
    }

    protected void init() {
        super.init();
        final List<Product> items;
        if (getArguments().containsKey(MATERIAL_ID)){
            items = inventoryService.getProductsFromMaterial(getArguments().getLong(MATERIAL_ID));
        }else{
            items = inventoryService.getProducts();
        }
        adapter = new ProductListAdapter(getContext(), R.layout.row_product_item, items);
        listView.setAdapter(adapter);
        addToClickListener(btnAdd);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        navigationService.moveToFragment(ProductDetailFragment.newInstance(adapter.getItem(position).getProductId()));
    }

    private void addToClickListener(View... params) {
        for (View v : params){
            v.setOnClickListener(this);
        }
    }

    @Override
    protected void add() {
        Util.newProductDialog("New product", null, getActivity(), getActivity().getLayoutInflater(),
                this).show();
    }

    @Override
    public void returnProduct(Product product) {
        inventoryService.addProduct(product);
    }

}
