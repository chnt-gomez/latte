package com.go.kchin.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.go.kchin.R;
import com.go.kchin.interfaces.FragmentNavigationService;
import com.go.kchin.interfaces.InventoryService;
import com.go.kchin.models.Product;

/**
 * Created by MAV1GA on 11/11/2016.
 */

public class ProductDetailFragment extends Fragment {

    private static final String PRODUCT_ID = "productId";

    private View view;
    private long productId;
    private boolean isRestoreAvailable = false;

    private EditText edtProduct, edtProductUnit;
    private Button btnProductMake;
    private FloatingActionButton btnSave, btnRestore;
    private TextView txtProductSellPrice;
    private InventoryService inventoryService;
    private FragmentNavigationService navigationService;

    private Product product;

    public static ProductDetailFragment newInstance(long productId){
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle args = new Bundle();
        args.putLong(PRODUCT_ID, productId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.inventoryService = (InventoryService)context;
        this.navigationService = (FragmentNavigationService)context;
    }

    public ProductDetailFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            productId = getArguments().getLong(PRODUCT_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_product_detail, null);
        init();
        return view;
    }

    private void init() {
        product = inventoryService.getProduct(productId);
    }
}
