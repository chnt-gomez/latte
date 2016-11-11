package com.go.kchin.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.go.kchin.R;
import com.go.kchin.adapters.ProductListAdapter;
import com.go.kchin.interfaces.FragmentNavigationService;
import com.go.kchin.interfaces.InventoryService;
import com.go.kchin.models.Material;
import com.go.kchin.models.Product;
import com.go.kchin.util.Util;


public class ProductListFragment extends Fragment implements View.OnClickListener,
        Util.ProductDialogEventListener{

    private InventoryService inventoryService;
    private FragmentNavigationService navigationService;
    private ProductListAdapter adapter;
    private View view;
    private FloatingActionButton btnAdd;

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
        view = inflater.inflate(R.layout.fragment_product_list, container, false);
        init();
        return view;
    }

    private void init() {
        adapter = new ProductListAdapter(getContext(), R.layout.row_product_item, inventoryService.getProducts());
        ListView listView = (ListView) view.findViewById(R.id.lv_products_list_view);
        btnAdd = (FloatingActionButton)view.findViewById(R.id.btn_add);
        addToClickListener(btnAdd);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                navigationService.moveToFragment(ProductDetailFragment.
                        newInstance(adapter.getItem(position).getProductId()));
            }
        });
    }

    private void addToClickListener(View... params) {
        for (View v : params){
            v.setOnClickListener(this);
        }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add:
                Util.newProductDialog("New product", null, getActivity(), getActivity().getLayoutInflater(),
                        this).show();
                break;
        }
    }

    @Override
    public void returnProduct(Product product) {
        inventoryService.addProduct(product);
    }

}
