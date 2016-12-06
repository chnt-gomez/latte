package com.go.kchin.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.go.kchin.R;
import com.go.kchin.adapters.ProductListAdapter;
import com.go.kchin.database.ProductContract;
import com.go.kchin.database.ProductInPackageContract;
import com.go.kchin.interfaces.SearchService;
import com.go.kchin.models.Operation;
import com.go.kchin.models.Product;
import com.go.kchin.util.Util;

import java.util.Collection;
import java.util.List;


public class ProductListFragment extends InventoryListFragment implements
        Util.ProductDialogEventListener {

    public static final int ALL_PRODUCTS = -1;
    private static final String OBJECT_ID = "object_id";
    private ProductListAdapter adapter;
    protected static final int FOR_PACKAGE = 1;
    protected static final int FROM_MATERIAL = 2;

    protected static final String FOR_RESULT = "for_result";

    public ProductListFragment() {}

    public static ProductListFragment newInstance() {
        ProductListFragment fragment = new ProductListFragment();
        Bundle args = new Bundle();
        args.putInt(FOR_RESULT, ALL_PRODUCTS);
        fragment.setArguments(args);
        return fragment;
    }

    public static ProductListFragment newInstanceFromMaterial(long materialId){
        ProductListFragment fragment = new ProductListFragment();
        Bundle args = new Bundle();
        args.putInt(FOR_RESULT, FROM_MATERIAL);
        args.putLong(OBJECT_ID, materialId);
        fragment.setArguments(args);
        return fragment;
    }

    public static ProductListFragment newInstanceForPakage(long packageId){
        ProductListFragment fragment = new ProductListFragment();
        Bundle args = new Bundle();
        args.putInt(FOR_RESULT, FOR_PACKAGE);
        args.putLong(OBJECT_ID, packageId);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onResume() {
        super.onResume();
        switch (getArguments().getInt(FOR_RESULT)){
            case FOR_PACKAGE:
                navigationService.setActionBarTitle("Add to Package");
                break;
            case FROM_MATERIAL:
                navigationService.setActionBarTitle("Products with Material");
                break;
            case ALL_PRODUCTS:
                navigationService.setActionBarTitle("Products");
                break;
        }
    }

    protected void init() {
        super.init();
        final List<Product> items;
        AdapterView.OnItemClickListener listener;
        switch (getArguments().getInt(FOR_RESULT)) {
            case FOR_PACKAGE:
                items = inventoryService.getProducts();
                btnAdd.setVisibility(View.GONE);
                listener = new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        inventoryService.addProductToPackage(getArguments().getLong(OBJECT_ID),
                                adapter.getItem(position).getProductId());
                        makeSnackBar("Added to package", "Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                inventoryService.undo(ProductInPackageContract.TABLE_NAME, getArguments().getLong(OBJECT_ID),
                                        adapter.getItem(position).getProductId());
                            }
                        });
                    }

                };
                listView.setOnItemClickListener(listener);
                break;
            case FROM_MATERIAL:
                items = inventoryService.getProductsFromMaterial(getArguments().getLong(OBJECT_ID));
                btnAdd.setVisibility(View.GONE);
                break;
            case ALL_PRODUCTS:
                items = inventoryService.getProducts();
                listener = new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        moveToProduct(adapter.getItem(position).getProductId());
                    }
                };
                listView.setOnItemClickListener(listener);
            break;
            default:
                items = inventoryService.getProducts();
            break;
        }
        adapter = new ProductListAdapter(getContext(), R.layout.row_product_item, items);
        listView.setAdapter(adapter);
    }

    @Override
    protected void add() {
        Util.newProductDialog("New product", null, getActivity(), getActivity().getLayoutInflater(),
                this).show();

    }

    @Override
    public Operation returnProduct(Product product) {
        final Operation operation = inventoryService.addProduct(product);
        updateItemList(operation.getProducts());
        makeSnackBar("Product added", "Undo", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inventoryService.undo(ProductContract.TABLE_NAME, operation.getInsertionId(), -1);
            }
        });
        return operation;
    }

    @Override
    public void moveToProduct(long productId) {
        navigationService.moveToFragment(ProductDetailFragment.newInstance(productId), true);
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
