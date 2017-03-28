package com.go.kchin.view.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.go.kchin.R;
import com.go.kchin.presenter.activities.DepartmentsActivity;
import com.go.kchin.presenter.activities.LowInventoryActivity;
import com.go.kchin.presenter.activities.MaterialsActivity;
import com.go.kchin.presenter.activities.ProductsActivity;
import com.go.kchin.presenter.activities.PurchasesReportActivity;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by MAV1GA on 09/02/2017.
 */

public class InventoryFragment extends BaseFragment {

    private static final String INVENTORY_FRAGMENT_TUTORIAL_STEP = "inventory_fragment_tutorial_step";

    @BindView(R.id.btn_see_materials)
    Button btnMaterials;

    @BindView(R.id.btn_see_departments)
    Button btnDepartments;

    @BindView(R.id.btn_low_inventory)
    Button btnLowInventory;

    @BindView(R.id.btn_purchases_report)
    Button btnPurchases;

    public static InventoryFragment newInstance(){
        InventoryFragment fragment = new InventoryFragment();
        Bundle args = new Bundle();
        args.putInt(LAYOUT_RES_ID, R.layout.fragment_inventory_options);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onShowTutorial() {
        super.onShowTutorial();
        int step = getPrefs().getInt(INVENTORY_FRAGMENT_TUTORIAL_STEP, 0);
        switch (step){
            case 0:
                btnMaterials.setVisibility(View.GONE);
                btnDepartments.setVisibility(View.GONE);
                btnLowInventory.setVisibility(View.GONE);
                btnPurchases.setVisibility(View.GONE);
                buildSquareView(R.id.btn_see_products, R.string.create_product).show(getActivity());
                break;
            case 1:
                btnMaterials.setVisibility(View.VISIBLE);
                btnDepartments.setVisibility(View.GONE);
                btnLowInventory.setVisibility(View.GONE);
                btnPurchases.setVisibility(View.GONE);
                buildSquareView(R.id.btn_see_materials, R.string.create_materials).show(getActivity());
                break;
            case 2:
                btnDepartments.setVisibility(View.VISIBLE);
                btnLowInventory.setVisibility(View.GONE);
                btnPurchases.setVisibility(View.GONE);
                buildSquareView(R.id.btn_see_departments, R.string.create_departments).show(getActivity());
                break;
            case 3:
                btnLowInventory.setVisibility(View.VISIBLE);
                btnPurchases.setVisibility(View.GONE);
                buildSquareView(R.id.btn_low_inventory, R.string.see_low_inventory).show(getActivity());
                break;
            case 4:
                btnPurchases.setVisibility(View.VISIBLE);
                buildSquareView(R.id.btn_purchases_report, R.string.see_purchases_report).show(getActivity());
                break;
        }
    }

    @OnClick(R.id.btn_see_products)
    public void onSeeProductsClick(View v){
        if (getPrefs().getInt(INVENTORY_FRAGMENT_TUTORIAL_STEP, 0) <= 0)
            getPrefs().edit().putInt(INVENTORY_FRAGMENT_TUTORIAL_STEP, 1).apply();
        mPresenter.moveToActivity(ProductsActivity.class, null);
    }

    @OnClick(R.id.btn_see_materials)
    public void onSeeMaterialsClick(View v){
        if (getPrefs().getInt(INVENTORY_FRAGMENT_TUTORIAL_STEP, 1) <= 1)
            getPrefs().edit().putInt(INVENTORY_FRAGMENT_TUTORIAL_STEP, 2).apply();
        mPresenter.moveToActivity(MaterialsActivity.class, null);
    }

    @OnClick(R.id.btn_see_departments)
    public void onSeeDepartmentsClick(View v){
        if (getPrefs().getInt(INVENTORY_FRAGMENT_TUTORIAL_STEP, 2) <= 2)
            getPrefs().edit().putInt(INVENTORY_FRAGMENT_TUTORIAL_STEP, 3).apply();
        mPresenter.moveToActivity(DepartmentsActivity.class, null);
    }

    @OnClick(R.id.btn_low_inventory)
    public void onSeeLowInventory(View v){
        if (getPrefs().getInt(INVENTORY_FRAGMENT_TUTORIAL_STEP, 3) <= 3)
            getPrefs().edit().putInt(INVENTORY_FRAGMENT_TUTORIAL_STEP, 4).apply();
        mPresenter.moveToActivity(LowInventoryActivity.class, null);
    }

    @OnClick(R.id.btn_purchases_report)
    public void onSeePurchasesClick(View v){
        if (getPrefs().getInt(INVENTORY_FRAGMENT_TUTORIAL_STEP, 4) <= 4)
            getPrefs().edit().putInt(INVENTORY_FRAGMENT_TUTORIAL_STEP, 5).apply();
        mPresenter.moveToActivity(PurchasesReportActivity.class, null);
    }
}
