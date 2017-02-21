package com.go.kchin.presenter.activities;

import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.model.database.PurchaseOperation;
import com.go.kchin.view.fragment.PurchaseListFragment;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by MAV1GA on 21/02/2017.
 */

public class PurchasesReportActivity extends BaseActivity implements MainMVP.PurchasesPresenterOps{


    @Override
    protected void init() {
        super.init();
        attachFragment(PurchaseListFragment.newInstance());
    }

    @Override
    public List<PurchaseOperation> getPurchases(DateTime dateTime) {
        return mModel.getPurchases(dateTime);
    }
}
