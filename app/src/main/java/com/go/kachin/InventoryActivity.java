package com.go.kachin;

import android.app.SearchManager;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;

import com.go.kachin.database.DatabaseHelper;
import com.go.kachin.fragments.InventoryFragment;
import com.go.kachin.interfaces.FragmentNavigationService;
import com.go.kachin.interfaces.InventoryService;
import com.go.kachin.models.Material;

import java.util.EventListener;
import java.util.List;

public class InventoryActivity extends AppCompatActivity implements InventoryService, FragmentNavigationService {

    DatabaseHelper helper;
    EventListener fragmentEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    private void init() {
        helper = new DatabaseHelper(this);
        addFragment(InventoryFragment.newInstance());
    }

    private void addFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_fragment_holder, fragment ,fragment.getTag()).commit();;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_inventory, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //TODO: passive search
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() >= 3){
                    //TODO: active search!
                    return true;
                }
                return false;

            }
        });

        return true;
    }

    /*
    Materials and Inventory methods
     */

    @Override
    public Material getMaterial(long id) {
        return helper.getMaterialFromId(id);
    }

    @Override
    public void addMaterial(Material material) {
       helper.addMaterial(material);
    }

    @Override
    public List<Material> getMaterials() {
        return helper.getMaterials();
    }

    @Override
    public void moveToFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().
                replace(R.id.main_fragment_holder, fragment).addToBackStack(null)
                .commit();
    }

    @Override
    public void setActionBarTitle(String title) {
        setTitle(title);
    }

    @Override
    public void hideActionBar() {
        if(getSupportActionBar() != null)
            getSupportActionBar().hide();
    }

    @Override
    public void showActionBar() {
        if (getSupportActionBar() != null)
            getSupportActionBar().show();
    }

    private void log(String message){
        Log.d(getClass().getSimpleName(), message);
    }

}
