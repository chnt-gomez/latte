package com.go.kchin.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.go.kchin.R;
import com.go.kchin.models.Department;
import com.go.kchin.models.Operation;
import com.go.kchin.models.Product;
import com.go.kchin.util.Util;

/**
 * Created by MAV1GA on 11/11/2016.
 */

public class ProductDetailFragment extends InventoryDetailFragment {

    private EditText edtProduct;
    private Spinner spnUnit;
    private Button btnProductMake, btnSalePrice, btnDepartment, btnRecipe, btnPackages;
    private TextView txtProductCost;
    private long temporaryDepartmentId = -1;

    private Product product;

    public static ProductDetailFragment newInstance(long productId){
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle args = new Bundle();
        args.putLong(OBJECT_ID, productId);
        args.putInt(LAYOUT_RES, R.layout.fragment_product_detail);
        fragment.setArguments(args);
        return fragment;
    }

    public ProductDetailFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            objectId = getArguments().getLong(OBJECT_ID);
        }
    }

    @Override
    protected void init() {
        super.init();
        product = inventoryService.getProduct(objectId);
        edtProduct = (EditText)findViewById(R.id.edt_product_name);
        spnUnit = (Spinner)findViewById(R.id.spn_product_unit);
        btnProductMake = (Button)findViewById(R.id.btn_product_amount);
        btnSalePrice = (Button)findViewById(R.id.btn_sale_price);
        txtProductCost = (TextView)findViewById(R.id.txt_product_cost);
        btnDepartment = (Button)findViewById(R.id.btn_product_department);
        btnRecipe = (Button)findViewById(R.id.btn_see_recipe);
        btnPackages = (Button)findViewById(R.id.btn_see_package);
        edtProduct.setText(product.getProductName());
        spnUnit.setSelection(Util.getSelectionIndexFromString(product.getProductUnit()));
        btnProductMake.setText(Util.fromFloat(product.getProductAmount()));
        btnSalePrice.setText(Util.fromFloat(product.getProductSalePrice()));
        txtProductCost.setText(Util.fromFloat(product.getProductPurchasePrice()));
        btnDepartment.setText(inventoryService.getDepartment(product.getProductDepartment()).getDepartmentName());
        addToClickListener(btnSalePrice, btnProductMake, btnDepartment, btnRecipe, btnPackages);
        addOnSpinnerSelectedListener(spnUnit);

    }

    @Override
    protected void save() {
        String newProductName = edtProduct.getText().toString();
        String newProductUnit = spnUnit.getSelectedItem().toString();
        float newProductAmount = Util.toFloat(btnProductMake.getText().toString());
        float newProductSalePrice = Util.toFloat(btnSalePrice.getText().toString());
        product.setProductName(newProductName);
        product.setProductUnit(newProductUnit);
        product.setProductSalePrice(newProductSalePrice);
        if (temporaryDepartmentId != -1)
            product.setProductDepartment(temporaryDepartmentId);
        product.setProductAmount(newProductAmount);
        inventoryService.updateProduct(objectId, product);
        Snackbar.make(fragmentView, "Product saved", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btn_sale_price:
                Util.newProductSalePriceDialog("Change price", product.getProductSalePrice(), getActivity(),
                        getActivity().getLayoutInflater(), new Util.UtilDialogEventListener() {
                            @Override
                            public void returnFloat(float arg) {
                                if(arg != product.getProductSalePrice()) {
                                    btnSalePrice.setText(Util.fromFloat(arg));
                                }
                            }

                            @Override
                            public void returnString(String arg) {
                                //Unused
                            }

                            @Override
                            public void returnLong(long arg) {

                            }
                        }).show();
                break;

            case R.id.btn_product_amount:
                Util.newBuyItemDialog("Buy " + product.getProductName(), null, getActivity(),
                        getActivity().getLayoutInflater(), new Util.UtilDialogEventListener() {
                            @Override
                            public void returnFloat(float arg) {
                                if (arg > 0){
                                    float total = Util.toFloat(btnProductMake.getText().toString()) + arg;
                                    btnProductMake.setText(Util.fromFloat(total));
                                }
                            }

                            @Override
                            public void returnString(String arg) {

                            }

                            @Override
                            public void returnLong(long arg) {

                            }
                        }).show();
                break;

            case R.id.btn_product_department:
                Util.newChooseDepartmentDialog("Select department", null, getActivity(), getActivity().getLayoutInflater(),
                        new Util.DepartmentDialogEventListener() {
                            @Override
                            public Operation returnDepartment(Department department) {
                                if (product.getProductDepartment() != department.getDepartmentId()) {
                                    btnDepartment.setText(department.getDepartmentName());
                                    temporaryDepartmentId = department.getDepartmentId();
                                }
                                return null;
                            }

                            @Override
                            public void editDepartment(long id, Department department) {
                                   //Not used
                            }
                        }, inventoryService.getDepartments())
                        .show();
                break;

            case R.id.btn_see_recipe:
                navigationService.moveToFragment(MaterialListFragment.newInstance(product.getProductId()), true);
                break;
            case R.id.btn_see_package:
                navigationService.moveToFragment(PackageListFragment.newInstance(product.getProductId()), true);
                break;
        }
    }
}
