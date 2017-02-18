package com.go.kchin.view.fragment;

import android.os.Bundle;
import android.view.View;

import com.go.kchin.R;
import com.go.kchin.presenter.activities.DepartmentsActivity;
import com.go.kchin.presenter.activities.MaterialsActivity;
import com.go.kchin.presenter.activities.ProductsActivity;
import com.go.kchin.presenter.activities.PurchaseOrdersActivity;

import butterknife.OnClick;

/**
 * Created by MAV1GA on 09/02/2017.
 */

public class InventoryFragment extends BaseFragment {

    public static InventoryFragment newInstance(){
        InventoryFragment fragment = new InventoryFragment();
        Bundle args = new Bundle();
        args.putInt(LAYOUT_RES_ID, R.layout.fragment_inventory_options);
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick(R.id.btn_see_products)
    public void onSeeProductsClick(View v){
        mPresenter.moveToActivity(ProductsActivity.class, null);
    }

    @OnClick(R.id.btn_see_materials)
    public void onSeeMaterialsClick(View v){
        mPresenter.moveToActivity(MaterialsActivity.class, null);
    }

    @OnClick(R.id.btn_see_departments)
    public void onSeeDepartmentsClick(View v){
        mPresenter.moveToActivity(DepartmentsActivity.class, null);
    }

    @OnClick(R.id.btn_purchase_orders)
    public void onSeePurchasesClick(View v){
        mPresenter.moveToActivity(PurchaseOrdersActivity.class, null);
    }

}
