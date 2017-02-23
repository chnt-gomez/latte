package com.go.kchin.presenter.activities;

import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.model.DepletedItem;
import com.go.kchin.model.database.Material;
import com.go.kchin.model.database.Product;
import com.go.kchin.view.fragment.LowInventoryFragment;

import java.util.List;

/**
 * Created by MAV1GA on 16/02/2017.
 */

public class LowInventoryActivity extends BaseActivity implements MainMVP.LowInventoryOps {

    @Override
    protected void init() {
        super.init();
        attachFragment(LowInventoryFragment.newIstance());
    }

    @Override
    public List<DepletedItem> getDepletedProducts() {
        return mModel.getDepletedProducts();
    }

    @Override
    public List<DepletedItem> getDepletedMaterial() {
        return mModel.getDepletedMaterials();
    }

    @Override
    public List<DepletedItem> getAllDepletedArticles() {
        return mModel.getAllDepletedArticles();
    }

    @Override
    public void purchase(DepletedItem item, float arg, float cost) {
        if (item.getClassType() == Product.class){
            mModel.buyProduct(item.getPurchaseId(), arg, cost);
            return;
        }
        if (item.getClassType() == Material.class){
            mModel.buyMaterial(item.getPurchaseId(), arg, cost);
        }
    }
}
