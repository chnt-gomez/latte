package com.go.kchin.presenter.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.go.kchin.R;
import com.go.kchin.interfaces.LoaderRequiredOps;
import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.model.database.Product;
import com.go.kchin.util.dialog.loader.Loader;
import com.go.kchin.view.fragment.ProductListFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by MAV1GA on 06/01/2017.
 */

public class ProductsActivity extends BaseActivity implements MainMVP.ProductsPresenterOps {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        attachFragment(ProductListFragment.newInstance());
    }

    @Override
    public List<Product> getAllProducts() {

        final List<Product> params = new ArrayList<>();

        Loader loader = new Loader(ProductsActivity.this, new LoaderRequiredOps() {
            @Override
            public void onLoad() {
                params.addAll(mModel.getAllProducts());
            }
        }, "Loading products...");

        loader.execute();

        return params;
    }

    @Override
    public void newProduct(Product p) {
        mModel.addProduct(p);
    }

    @Override
    public void moveToProduct(long productId) {

    }


}
