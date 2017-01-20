package com.go.kchin.presenter.activities;

import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.model.database.Product;
import com.go.kchin.model.database.Sale;
import com.go.kchin.view.fragment.CurrentSaleFragment;
import com.go.kchin.view.fragment.SellProductFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MAV1GA on 18/01/2017.
 */

public class SaleActivity extends BaseActivity implements MainMVP.SalesPresenterOps{


    private List<Sale> currentSale;

    @Override
    public void onOperationSuccess(String message) {
        super.onOperationSuccess(message);
        currentSale.clear();
    }

    @Override
    protected void init() {
        super.init();
        attachFragment(CurrentSaleFragment.newInstance());
        currentSale = new ArrayList<>();
    }

    @Override
    public void sell(Sale sale) {
        currentSale.add(sale);
    }

    @Override
    public void sell(List<Sale> sales) {
        currentSale.addAll(sales);
    }

    @Override
    public List<Sale> getCurrentSale() {
        return currentSale;
    }

    @Override
    public void applyCurrentSale() {
        mModel.applySale(currentSale);
    }

    @Override
    public List<Product> getProducts() {
        return mModel.getAllProducts();
    }

    @Override
    public void pickProduct(Product product) {
        Sale sale = new Sale();
        sale.productAmount = 1;
        sale.product = product;
        sale.saleTotal = product.productSellPrice*sale.productAmount;
        currentSale.add(sale);
        mView.showMessage("Added to cart!");
    }
}
