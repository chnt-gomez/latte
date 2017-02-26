package com.go.kchin.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.go.kchin.R;
import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.interfaces.RequiredDialogOps;
import com.go.kchin.model.SimplePurchase;
import com.go.kchin.model.database.Product;
import com.go.kchin.model.database.PurchaseOperation;
import com.go.kchin.util.utilities.Dialogs;
import com.go.kchin.util.utilities.Loader;
import com.go.kchin.util.utilities.MeasurePicker;
import com.go.kchin.util.utilities.NFormatter;

import org.w3c.dom.Text;

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
    @BindView(R.id.chk_is_made_on_sale)Switch swProductType;
    @BindView(R.id.btn_edit) FloatingActionButton btnEdit;
    @BindView(R.id.btn_inventory_adjustments) Button btnInventoryAdjustements;
    @BindView(R.id.txt_product_type)TextView txtProductType;
    @BindView(R.id.txt_product_type_summary) TextView txtProductTypeSummary;

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
        save();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.setActivityTitle(getString(R.string.title_product_details));
        reload();
    }

    @OnClick(R.id.btn_edit)
    public void onEditClick(View v){
        onRequestEdit();
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
    public void onOperationSuccesfull(String message) {
        super.onOperationSuccesfull(message);
        reload();
    }

    @Override
    public void onDoneLoading() {
        if (product != null){
            btnSellPrice.setText(NFormatter.floatToStringAsPrice(product.productSellPrice, false));
            edtProductName.setText(product.productName);
            btnProductRemaining.setText(NFormatter.floatToStringAsNumber(product.productRemaining));
            btnProductDepartment.setText(product.getDepartmentName());
            if (product.department != null)
                btnProductDepartment.setText(mProductPresenter.getDepartmentNameFromProduct(
                        product.department.getId()));
            spnProductMeasure.setSelection(product.productMeasureUnit);
            if (product.productType == Product.PRODUCT_TYPE_STORED){
                swProductType.setChecked(true);
                txtProductTypeSummary.setText(getString(R.string.product_type_stored_summary));

            }else{
                btnProductRemaining.setEnabled(false);
                swProductType.setChecked(false);
                txtProductTypeSummary.setText(getString(R.string.product_type_not_stored_summary));
                btnProductRemaining.setText(R.string.without_inv);
            }
        }
        super.onDoneLoading();
    }

    public void save(){
        product.productName = edtProductName.getText().toString();
        product.productMeasureUnit = spnProductMeasure.getSelectedItemPosition();
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
                btnSellPrice.setText(NFormatter.floatToStringAsPrice(arg, false));
            }
        }).show();
    }

    @OnClick(R.id.btn_product_amount)
    public void buyMore(View view){
        Dialogs.newPurchaseDialog(getContext(), getString(R.string.buy_more), null, new RequiredDialogOps.RequiredNewPurchaseOps(){
            @Override
            public void onNewPurchase(SimplePurchase arg) {
                mProductPresenter.buyMore(product.getId(), arg.getPurchasedItems(), arg.getPurchaseAmount());
            }
        }).show();
    }

    @Override
    protected void enableEditMode() {
        super.enableEditMode();
        if (product.productType == Product.PRODUCT_TYPE_MADE_ON_SALE) {
            btnProductRemaining.setEnabled(false);
        }
        btnEdit.setVisibility(View.GONE);
    }

    @Override
    protected void init() {
        super.init();
        spnProductMeasure.setEnabled(false);
        addToEditListener(edtProductName, spnProductMeasure, btnProductDepartment, btnRecipe,
                btnSellPrice, swProductType, btnInventoryAdjustements);

       swProductType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked){
                txtProductTypeSummary.setText(R.string.product_type_stored_summary);
                product.productType = Product.PRODUCT_TYPE_STORED;
                btnProductRemaining.setEnabled(true);
                btnProductRemaining.setText(NFormatter.floatToStringAsNumber(product.productRemaining));

            }else{
                product.productType = Product.PRODUCT_TYPE_MADE_ON_SALE;
                txtProductTypeSummary.setText(R.string.product_type_not_stored_summary);
                btnProductRemaining.setEnabled(false);
                btnProductRemaining.setText(getString(R.string.without_inv));
            }
               save();
           }
       });
        spnProductMeasure.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,
                MeasurePicker.getEntries(getResources())));
    }

    @OnClick(R.id.btn_inventory_adjustments)
    public void onAdjustmentsClick(View v){
        Dialogs.newFloatDialog(getContext(), getString(R.string.set_new_amount), getString(R.string.administration_amount_change), new RequiredDialogOps.NewFloatOps() {
            @Override
            public void onNewFloat(float arg) {
                product.productRemaining = arg;
                save();
            }
        }).show();
    }
}
