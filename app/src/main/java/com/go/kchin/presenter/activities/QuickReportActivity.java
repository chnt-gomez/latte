package com.go.kchin.presenter.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.go.kchin.R;
import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.model.database.PurchaseOperation;
import com.go.kchin.model.database.Sale;
import com.go.kchin.model.database.SaleTicket;
import com.go.kchin.view.fragment.QuickReportFragment;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by MAV1GA on 23/01/2017.
 */

public class QuickReportActivity extends BaseActivity implements MainMVP.DetailedReportPresenterOps,
        MainMVP.QuickReportPresenterOps {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_day_report);
    }

    @Override
    protected void init() {
        super.init();
        attachFragment(QuickReportFragment.newInstance());
    }

    @Override
    public List<SaleTicket> getDaySaleTickets(DateTime time) {
        return mModel.getTicketsFromDate(time);
    }

    @Override
    public List<Sale> getSalesInTicket(SaleTicket saleTicket) {
        return mModel.getSalesInTicket(saleTicket);
    }

    @Override
    public float getDaySaleTotal(DateTime date) {
        float total = 0.0f;
        for (SaleTicket s : mModel.getTicketsFromDate(date)){
            total += s.saleTotal;
        }
        return total;
    }

    @Override
    public float getDayPurchasesTotal(DateTime date) {
        float total = 0f;
        for (PurchaseOperation purchase : mModel.getPurchases(date)){
            total += purchase.purchaseAmount;
        }
        return total;
    }

    @Override
    public float getNetEarnings(DateTime date) {
        return getDaySaleTotal(date)-getDayPurchasesTotal(date);
    }

    @Override
    public long[] getRecordedTicketsIdRange(DateTime date) {
        List<SaleTicket> sales = getDaySaleTickets(date);
        if (sales.size() > 1){
            return new long[] {sales.get(0).getId(), sales.get(sales.size()-1).getId()};
        }else{
            if (sales.size() == 1)
                return new long []{sales.get(0).getId(), -1};
            else
                return new long[] {-1, -1};
        }
    }
}
