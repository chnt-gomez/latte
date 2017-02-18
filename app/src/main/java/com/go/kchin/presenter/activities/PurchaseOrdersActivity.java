package com.go.kchin.presenter.activities;

import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.model.PurchaseOrder;
import com.go.kchin.model.database.Material;
import com.go.kchin.model.database.Product;
import com.go.kchin.view.fragment.PurchasesFragment;

import java.util.List;

/**
 * Created by MAV1GA on 16/02/2017.
 */

public class PurchaseOrdersActivity extends BaseActivity implements MainMVP.PurchasesPresenterOps {

    @Override
    protected void init() {
        super.init();
        attachFragment(PurchasesFragment.newIstance());
    }

    @Override
    public List<PurchaseOrder> getDepletedProducts() {
        return mModel.getDepletedProducts();
    }

    @Override
    public List<PurchaseOrder> getDepletedMaterial() {
        return mModel.getDepletedMaterials();
    }

    @Override
    public List<PurchaseOrder> getAllDepletedArticles() {
        return mModel.getAllDepletedArticles();
    }

    @Override
    public void purchase(PurchaseOrder item, float arg) {
        if (item.getClassType() == Product.class){
            mModel.buyProduct(item.getPurchaseId(), arg);
            return;
        }
        if (item.getClassType() == Material.class){
            mModel.buyMaterial(item.getPurchaseId(), arg);
        }
    }
}
