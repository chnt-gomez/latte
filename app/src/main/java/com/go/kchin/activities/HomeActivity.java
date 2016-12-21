package com.go.kchin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.go.kchin.R;
import com.go.kchin.adapters.DrawerListAdapter;

/**
 * Created by MAV1GA on 18/11/2016.
 */

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private String mPanelItems[];
    private DrawerListAdapter adapter;
    private static final int INVENTORY = 0;
    private static final int SALES = 1;
    private static final int REPORTS = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);
        configureActionBarIcon();
        mPanelItems = getResources().getStringArray(R.array.drawer_items);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerList = (ListView)findViewById(R.id.lv_left_drawer);
        adapter = new DrawerListAdapter(this, R.layout.row_drawer_item, mPanelItems);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
                switch (position){
                   case INVENTORY:

                       startActivity(new Intent(HomeActivity.this, InventoryActivity.class));
                       break;
                   case SALES:
                       startActivity(new Intent(HomeActivity.this, SalesActivity.class));
                       break;
                   case REPORTS:
                       startActivity(new Intent(HomeActivity.this, ReportsActivity.class));
               }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)){
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                }else {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void configureActionBarIcon() {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        }
    }
}
