package com.go.kchin.presenter.activities;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.go.kchin.R;
import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.model.database.PurchaseOperation;
import com.go.kchin.util.dialog.Dialogs;
import com.go.kchin.view.fragment.PurchaseListFragment;

import org.joda.time.DateTime;

import java.util.Date;
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
        if (dateTime == null)
            dateTime = DateTime.now();
        return mModel.getPurchases(dateTime);
    }

    @Override
    public float getTotalPurchases(DateTime dateTime) {
        if (dateTime == null)
            dateTime = DateTime.now();
        float total = 0f;
        for (PurchaseOperation p : getPurchases(dateTime))
        {
            total =+ p.purchaseAmount;
        }
        return total;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.purchases_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }


}
