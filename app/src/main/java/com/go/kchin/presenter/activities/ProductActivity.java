package com.go.kchin.presenter.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.go.kchin.R;
import com.go.kchin.view.fragment.ProductDetailFragment;

/**
 * Created by MAV1GA on 09/01/2017.
 */

public class ProductActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        attachFragment(ProductDetailFragment.newInstance());
    }
}
