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
import com.go.kchin.util.Util;

/**
 * Created by MAV1GA on 11/11/2016.
 */

public class ProductDetailFragment extends Fragment {

    private static final String PRODUCT_ID = "productId";

    private View view;
    private long productId;
    private boolean isRestoreAvailable = false;

    private EditText edtProduct, edtProductUnit;
    private Button btnProductMake, btnSalePrice, btnDepartment, btnRecipe, btnPackages;
    private FloatingActionButton btnSave, btnRestore;
    private TextView txtProductCost;
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

        edtProduct = (EditText)view.findViewById(R.id.edt_product_name);
        edtProductUnit = (EditText)view.findViewById(R.id.edt_product_unit);
        btnProductMake = (Button)view.findViewById(R.id.btn_product_amount);
        btnSalePrice = (Button)view.findViewById(R.id.btn_sale_price);
        txtProductCost = (TextView) view.findViewById(R.id.txt_product_cost);
        btnDepartment = (Button) view.findViewById(R.id.btn_product_department);
        btnRecipe = (Button)view.findViewById(R.id.btn_see_recipe);
        btnPackages = (Button)view.findViewById(R.id.btn_see_package);

        edtProduct.setText(product.getProductName());
        edtProductUnit.setText(product.getProductUnit());
        btnProductMake.setText(Util.fromFloat(product.getProductAmount()));
        btnSalePrice.setText(Util.fromFloat(product.getProductSalePrice()));
        txtProductCost.setText(Util.fromFloat(product.getProductPurchasePrice()));
        //btnDepartment.setText(product.getProductDepartment());

    }
}
