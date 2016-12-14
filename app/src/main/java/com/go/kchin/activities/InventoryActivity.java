package com.go.kchin.activities;

import android.os.Bundle;
import com.go.kchin.R;
import com.go.kchin.fragments.ProductListFragment;

public class InventoryActivity extends KchinSuperActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        init();
    }

    protected void init() {
        super.init();
        addFragment(ProductListFragment.newInstance());
    }

}
