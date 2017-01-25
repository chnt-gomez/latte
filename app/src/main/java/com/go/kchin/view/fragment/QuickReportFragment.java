package com.go.kchin.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.go.kchin.R;
import com.go.kchin.adapters.QuickSaleAdapter;
import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.presenter.activities.QuickReportActivity;
import com.go.kchin.util.dialog.loader.Loader;
import com.go.kchin.util.dialog.number.Number;

import org.joda.time.DateTime;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by MAV1GA on 24/01/2017.
 */

public class QuickReportFragment extends BaseFragment {

    @BindView(R.id.txt_sale_total)
    TextView txtSaleTotal;

    @BindView(R.id.txt_total_purchases)
    TextView txtPurchases;

    @BindView(R.id.txt_total_earnings)
    TextView txtTotalEarnings;

    @BindView(R.id.txt_sale_ticket)
    TextView txtSaleTickets;

    @BindView(R.id.btn_date)
    Button btnDate;

    @BindView(R.id.btn_see_details)
    Button btnSeeDetails;

    private MainMVP.QuickReportPresenterOps mReportPresenter;
    private QuickSaleAdapter adapter;

    public static QuickReportFragment newInstance(){
        QuickReportFragment fragment = new QuickReportFragment();
        Bundle args = new Bundle();
        args.putInt(LAYOUT_RES_ID, R.layout.fragment_quick_report);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void init() {
        super.init();
        adapter = new QuickSaleAdapter();
        reload();
    }

    @Override
    public void onLoad() {
        super.onLoad();

        adapter.setTotalSales(Number.floatToStringAsPrice(
                mReportPresenter.getDaySaleTotal(DateTime.now()),false));
        adapter.setTotalPurchases(Number.floatToStringAsPrice(
                mReportPresenter.getDayPurchasesTotal(DateTime.now()),false));
        adapter.setTotalEarnings(Number.floatToStringAsPrice(
                mReportPresenter.getNetEarnings(DateTime.now()), false));
        adapter.setTicketIds(mReportPresenter.getRecordedTicketsIdRange(DateTime.now()));
        adapter.setDate(DateTime.now());

    }

    @Override
    public void onDoneLoading() {
        super.onDoneLoading();
        updateView();
    }

    @OnClick(R.id.btn_see_details)
    public void moveToDetail(View view){
       mPresenter.moveToFragment(DetailedQuickReport.newInstance());
    }

    private void updateView() {
        txtSaleTotal.setText(adapter.getTotalSales());
        txtPurchases.setText(adapter.getTotalPurchases());
        btnDate.setText(adapter.getFormattedDate());
        txtSaleTickets.setText(adapter.getFormattedTickets());
        txtTotalEarnings.setText(adapter.getTotalEarnings());
    }

    private void reload() {
        final Loader loader = new Loader(this);
        loader.execute();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mReportPresenter = (MainMVP.QuickReportPresenterOps)context;
    }

    @Override
    public void onDetach() {
        mReportPresenter = null;
        super.onDetach();
    }
}
