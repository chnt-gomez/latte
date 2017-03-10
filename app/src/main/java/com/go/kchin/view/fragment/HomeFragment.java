package com.go.kchin.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.go.kchin.R;
import com.go.kchin.presenter.activities.QuickReportActivity;
import com.go.kchin.presenter.activities.SaleActivity;
import butterknife.OnClick;

/**
 * Created by MAV1GA on 08/02/2017.
 */

public class HomeFragment extends BaseFragment {

    public static HomeFragment newInstance(){
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt(LAYOUT_RES_ID, R.layout.fragment_home);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onShowTutorial() {
        super.onShowTutorial();
    }

    @Override
    protected void init() {
        super.init();
    }

    @OnClick(R.id.btn_sell)
    public void onSellClick(View v){
        mPresenter.moveToActivity(SaleActivity.class, null);
    }

    @OnClick(R.id.cv_sales)
    public void onSalesClick(View v){mPresenter.moveToActivity(SaleActivity.class, null);}

    @OnClick(R.id.cv_reports)
    public void onReportsClick(View v){mPresenter.moveToActivity(QuickReportActivity.class, null);}

    @OnClick(R.id.cv_inventory)
    public void onInventoryClick(View v){
        mPresenter.moveToFragment(InventoryFragment.newInstance());
    }

}
