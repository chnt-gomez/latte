package com.go.kchin.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.view.MotionEvent;
import android.view.View;
import com.go.kchin.R;
import com.go.kchin.presenter.activities.QuickReportActivity;
import com.go.kchin.presenter.activities.SaleActivity;

import butterknife.BindView;
import butterknife.OnClick;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;

/**
 * Created by MAV1GA on 08/02/2017.
 */

public class HomeFragment extends BaseFragment {

    public static final String HOME_FRAGMENT_STEP_TUTORIAL = "home_fragment_step_tutorial";

    @BindView(R.id.btn_sell)
    FloatingActionButton btnSell;

    @BindView(R.id.cv_sales)
    CardView cvSales;

    @BindView(R.id.cv_reports)
    CardView cvReports;

    @BindView(R.id.cv_inventory)
    CardView cvInventory;

    public static HomeFragment newInstance(){
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt(LAYOUT_RES_ID, R.layout.fragment_home);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onShowTutorial() {
        super.onShowTutorial();
        final int step = getPrefs().getInt(HOME_FRAGMENT_STEP_TUTORIAL, 0);
        switch (step){
            case 0:
                btnSell.setVisibility(View.GONE);
                cvSales.setVisibility(View.GONE);
                cvReports.setVisibility(View.GONE);
                buildSquareView(R.id.cv_inventory, R.string.welcome_home_activity).show(getActivity());
                break;
            case 1:
                btnSell.setVisibility(View.GONE);
                cvReports.setVisibility(View.GONE);
                buildSquareView(R.id.cv_sales, R.string.move_to_sell).show(getActivity());
                break;
            case 2:
                btnSell.setVisibility(View.GONE);
                buildSquareView(R.id.cv_reports, R.string.see_report).show(getActivity());
                break;
        }


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
    public void onSalesClick(View v){
        if(getPrefs().getInt(HOME_FRAGMENT_STEP_TUTORIAL, 1) <= 1)
            getPrefs().edit().putInt(HOME_FRAGMENT_STEP_TUTORIAL, 2).apply();
        mPresenter.moveToActivity(SaleActivity.class, null);}

    @OnClick(R.id.cv_reports)

    public void onReportsClick(View v){
        if(getPrefs().getInt(HOME_FRAGMENT_STEP_TUTORIAL, 2) <= 2)
            getPrefs().edit().putInt(HOME_FRAGMENT_STEP_TUTORIAL, 3).apply();
        mPresenter.moveToActivity(QuickReportActivity.class, null);}

    @OnClick(R.id.cv_inventory)
    public void onInventoryClick(View v){
        if (getPrefs().getInt(HOME_FRAGMENT_STEP_TUTORIAL, 0) <= 0)
                getPrefs().edit().putInt(HOME_FRAGMENT_STEP_TUTORIAL, 1).apply();
        mPresenter.moveToFragment(InventoryFragment.newInstance());
    }

}
