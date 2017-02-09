package com.go.kchin.presenter.activities;

import android.os.Bundle;
import android.view.Menu;
import com.go.kchin.R;
import com.go.kchin.view.fragment.HomeFragment;



public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        super.init();
        attachFragment(HomeFragment.newInstance());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

/*
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()){
            case R.id.nav_products:
                startActivity(new Intent(HomeActivity.this, ProductsActivity.class));
                break;
            case R.id.nav_departments:
                startActivity(new Intent(HomeActivity.this, DepartmentsActivity.class));
                break;
            case R.id.nav_materials:
                startActivity(new Intent(HomeActivity.this, MaterialsActivity.class));
                break;

            case R.id.nav_quick_report:
                startActivity(new Intent(HomeActivity.this, QuickReportActivity.class));
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    */
}
