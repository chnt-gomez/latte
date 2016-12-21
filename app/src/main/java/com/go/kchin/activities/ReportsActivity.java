package com.go.kchin.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.go.kchin.R;
import com.go.kchin.fragments.ReportsFragment;
import com.go.kchin.interfaces.FragmentNavigationService;
import com.go.kchin.interfaces.ReportsService;

/**
 * Created by MAV1GA on 19/12/2016.
 */

public class ReportsActivity extends AppCompatActivity implements ReportsService, FragmentNavigationService {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        init();
    }

    private void init(){
        setTitle("Reports");
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_fragment_holder, ReportsFragment.newInstance(),
                        null).commit();
    }

    @Override
    public void moveToFragment(Fragment fragment, boolean addToBackStackTrace) {
        if (addToBackStackTrace){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_fragment_holder, fragment, null).addToBackStack(null).commit();
        }else{
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_fragment_holder, fragment, null).commit();
        }

    }

    @Override
    public void setActionBarTitle(String title) {

    }

    @Override
    public void hideActionBar() {

    }

    @Override
    public void showActionBar() {

    }
}
