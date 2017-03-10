package com.go.kchin.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.go.kchin.R;
import com.go.kchin.presenter.activities.QuickReportActivity;
import com.go.kchin.presenter.activities.SaleActivity;
import com.go.kchin.util.utilities.TutorialBuilder;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by MAV1GA on 08/02/2017.
 */

public class HomeFragment extends BaseFragment {

    private ShowcaseView sv;
    @BindView(R.id.cv_inventory)
    CardView cvInventory;

    private final static String TUTORIAL_COMPLETE = "hf_tutorial_complete";
    private final static String TUTORIAL_STEP = "hf_tutorial_step";

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
    protected void onTutorialRequired() {
        super.onTutorialRequired();
        mPresenter.hideActionBar();

        if (!getPrefs().getBoolean(TUTORIAL_COMPLETE, false)){
            TutorialBuilder tutorialBuilder = new TutorialBuilder(this);
            tutorialBuilder.start(getActivity());
        }

    }

    @Override
    public void onShowcaseViewHide(ShowcaseView showcaseView) {
        super.onShowcaseViewHide(showcaseView);
        mPresenter.restoreActionBar();
    }

    @Override
    protected void init() {
        super.init();

    }

    @Override
    public void onMoveToStep(int step, ShowcaseView showcaseView) {
        switch (step){
            case 0:
                sv.setContentTitle(getString(R.string.tutorial_hf_step0_title));
                sv.setContentText(getString(R.string.tutorial_hf_step0_summary));
                break;
            case 1:
                sv.setContentTitle(getString(R.string.tutorial_hf_step0_title));
                sv.setContentText(getString(R.string.tutorial_hf_step0_summary));
                sv.setTarget(new ViewTarget(R.id.cv_inventory, getActivity()));
                break;
        }
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
