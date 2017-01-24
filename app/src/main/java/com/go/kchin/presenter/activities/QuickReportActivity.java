package com.go.kchin.presenter.activities;

import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.model.database.Sale;
import com.go.kchin.model.database.SaleTicket;
import com.go.kchin.view.fragment.QuickReportFragment;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by MAV1GA on 23/01/2017.
 */

public class QuickReportActivity extends BaseActivity implements MainMVP.QuickSalePresenterOps {

    @Override
    protected void init() {
        super.init();
        attachFragment(QuickReportFragment.newInstance());
    }

    @Override
    public List<SaleTicket> getDaySaleTickets() {
        return mModel.getTicketsFromDate(DateTime.now());
    }

    @Override
    public List<Sale> getSalesInTicket(SaleTicket saleTicket) {
        return mModel.getSalesInTicket(saleTicket);
    }
}
