package com.go.kchin.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.go.kchin.R;
import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.interfaces.RequiredDialogOps;
import com.go.kchin.model.database.Product;
import com.go.kchin.util.dialog.Dialogs;
import com.go.kchin.util.dialog.MeasurePicker;
import com.go.kchin.util.dialog.loader.Loader;
import com.go.kchin.util.dialog.number.Number;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by MAV1GA on 09/01/2017.
 */

public class ProductDetailFragment extends BaseFragment{

    private final static String PRODUCT_ID = "product_id";
    private Product product;
    private MainMVP.ProductPresenterOps mProductPresenter;

    @BindView(R.id.edt_product_name) EditText edtProductName;
    @BindView(R.id.spn_product_unit)Spinner spnProductMeasure;
    @BindView(R.id.btn_product_amount)Button btnProductRemaining;
    @BindView(R.id.btn_product_department)Button btnProductDepartment;
    @BindView(R.id.btn_sale_price)Button btnSellPrice;
    @BindView(R.id.btn_see_recipe)Button btnRecipe;
    @BindView(R.id.btn_see_package)Button btnPackages;
    @BindView(R.id.btn_save)FloatingActionButton btnSave;

    public static ProductDetailFragment newInstance(long productId){
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(LAYOUT_RES_ID, R.layout.fragment_product_detail);
        arguments.putLong(PRODUCT_ID, productId);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onPause() {
        save(null);
        super.onPause();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mProductPresenter = (MainMVP.ProductPresenterOps) context;
    }

    private void reload(){
        Loader loader = new Loader(this);
        loader.execute();
    }

    @Override
    public void onLoad() {
        super.onLoad();
        product = mProductPresenter.getProduct(getArguments().getLong(PRODUCT_ID));
    }

    @Override
    public void onDoneLoading() {
        super.onDoneLoading();
        if (product != null){
            edtProductName.setText(product.productName);
            btnProductRemaining.setText(Number.floatToStringAsNumber(product.productRemaining));
            btnProductDepartment.setText(product.getDepartmentName());
            if (product.department != null)
                btnProductDepartment.setText(mProductPresenter.getDepartmentNameFromProduct(
                        product.department.getId()));
            btnSellPrice.setText(Number.floatToStringAsPrice(product.productSellPrice, false));
            spnProductMeasure.setSelection(product.productMeasureUnit);
        }
    }

    @OnClick(R.id.btn_save)
    public void save(View view){
        product.productName = edtProductName.getText().toString();
        mProductPresenter.saveProduct(product);
    }

    @OnClick(R.id.btn_product_department)
    public void pickDepartment(View view){
        mPresenter.moveToFragment(PickDepartmentFragment.newInstance(product.getId()));
    }

    @OnClick(R.id.btn_see_recipe)
    public void seeRecipe(View view){
        mPresenter.moveToFragment(RecipeListFragment.newInstance(product.getId()));
    }

    @OnClick(R.id.btn_see_package)
    public void seePackages(View view){
        showMessage(R.string.coming_soon);
    }

    @OnClick(R.id.btn_sale_price)
    public void editPrice(View view){
        Dialogs.newFloatDialog(getContext(), getString(R.string.edit_price), null, new RequiredDialogOps.NewFloatOps() {
            @Override
            public void onNewFloat(float arg) {
                product.setProductSellPrice(arg);
                btnSellPrice.setText(Number.floatToStringAsPrice(arg, false));
            }
        }).show();
    }

    @OnClick(R.id.btn_product_amount)
    public void buyMore(View view){
        Dialogs.newFloatDialog(getContext(), getString(R.string.buy_more), null, new RequiredDialogOps.NewFloatOps(){
            @Override
            public void onNewFloat(float arg) {
                product.productRemaining += arg;
                btnProductRemaining.setText(Number.floatToStringAsNumber(product.productRemaining));
            }
        }).show();
    }

    @Override
    protected void init() {
        super.init();
        reload();
        spnProductMeasure.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,
                MeasurePicker.getEntries(getResources())));
    }
}
