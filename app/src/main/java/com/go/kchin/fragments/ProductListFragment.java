package com.go.kchin.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.go.kchin.R;
import com.go.kchin.adapters.ProductListAdapter;
import com.go.kchin.interfaces.SearchService;
import com.go.kchin.models.Operation;
import com.go.kchin.models.Product;
import com.go.kchin.util.Util;

import java.util.Collection;
import java.util.List;


public class ProductListFragment extends InventoryListFragment implements
        Util.ProductDialogEventListener {

    public static final long ALL_PRODUCTS = -1;
    private ProductListAdapter adapter;
    protected static final String MATERIAL_ID = "material_id";

    public ProductListFragment() {}

    public static ProductListFragment newInstance(long materialId) {
        ProductListFragment fragment = new ProductListFragment();
        Bundle args = new Bundle();
        if (materialId != -1) {
            args.putLong(MATERIAL_ID, materialId);
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        navigationService.setActionBarTitle("Products");
    }

    protected void init() {
        super.init();
        final List<Product> items;
        if (getArguments().containsKey(MATERIAL_ID)){
            items = inventoryService.getProductsFromMaterial(getArguments().getLong(MATERIAL_ID));
            btnAdd.setVisibility(FloatingActionButton.GONE);
            listView.setOnItemClickListener(null);
        }else{
            items = inventoryService.getProducts();
        }
        adapter = new ProductListAdapter(getContext(), R.layout.row_product_item, items);
        listView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        navigationService.moveToFragment(ProductDetailFragment.newInstance(adapter.getItem(position).getProductId()));
    }

    @Override
    protected void add() {
        Util.newProductDialog("New product", null, getActivity(), getActivity().getLayoutInflater(),
                this).show();
    }

    @Override
    public Operation returnProduct(Product product) {
        Operation operation = inventoryService.addProduct(product);
        updateItemList(operation.getProducts());
        return operation;
    }

    @Override
    public void moveToProduct(long productId) {
        navigationService.moveToFragment(ProductDetailFragment.newInstance(productId));
    }

    @Override
    protected void updateItemList(List<?> items) {
        adapter.clear();
        adapter.addAll((Collection<? extends Product>) items);
        listView.setAdapter(adapter);
    }

    @Override
    public void onSearch(String query) {
        if (query == null)
            updateItemList(inventoryService.getProducts());
        else
            updateItemList(inventoryService.searchProducts(query));
    }
}
