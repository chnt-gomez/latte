package com.go.kchin.presenter.activities;

import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.model.database.Product;
import com.go.kchin.model.database.Sale;
import com.go.kchin.view.fragment.SellProductFragment;

import java.util.List;

/**
 * Created by MAV1GA on 18/01/2017.
 */

public class SaleActivity extends BaseActivity implements MainMVP.SalesPresenterOps{


    @Override
    public void onOperationSuccess(String message) {
        super.onOperationSuccess(message);
    }

    @Override
    protected void init() {
        super.init();
        attachFragment(SellProductFragment.newInstance());
    }

    @Override
    public void applyCurrentSale(List<Sale> currentSale) {
        mModel.applySale(currentSale);
    }

    @Override
    public List<Product> getProducts() {
        return mModel.getAllProducts();
    }

}
