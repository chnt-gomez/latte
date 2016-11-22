package com.go.kchin.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.go.kchin.R;
import com.go.kchin.fragments.QuickSaleFragment;
import com.go.kchin.interfaces.SalesService;

/**
 * Created by MAV1GA on 22/11/2016.
 */

public class SalesActivity extends AppCompatActivity implements SalesService {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);
        init();
    }

    private void init(){
        getSupportFragmentManager().beginTransaction().add(R.id.main_fragment_holder,
                QuickSaleFragment.newInstance()).commit();
    }



}
