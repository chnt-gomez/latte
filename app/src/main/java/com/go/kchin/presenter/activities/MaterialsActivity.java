package com.go.kchin.presenter.activities;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.go.kchin.R;
import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.model.database.Material;
import com.go.kchin.view.fragment.MaterialListFragment;

import java.util.List;

/**
 * Created by MAV1GA on 11/01/2017.
 */

public class MaterialsActivity extends BaseActivity implements MainMVP.MaterialsPresenterOps,
        SearchView.OnQueryTextListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        super.init();
        attachFragment(MaterialListFragment.newInstance());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView =
                (SearchView) MenuItemCompat.getActionView(searchItem);
        if(searchView != null) {
            searchView.setSearchableInfo(
                    searchManager.getSearchableInfo(getComponentName()));
            searchView.setSubmitButtonEnabled(true);
            searchView.setOnQueryTextListener(this);

        }else{
            Log.w(getClass().getSimpleName(), "SEARCH VIEW IS NULL");
        }

        menu.findItem(R.id.filter).setVisible(false);
        invalidateOptionsMenu();

        return true;
    }

    @Override
    public List<Material> getAllMaterials() {
        return mModel.getAllMaterials();
    }

    @Override
    public List<Material> getMaterials(String query) {
        return mModel.getMaterials(query);
    }

    @Override
    public void newMaterial(Material material) {
        mModel.addMaterial(material);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d(getClass().getSimpleName(), query);
        mView.search(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.length() >= 3 && newText.length() % 3 == 0){
            mView.search(newText);
        }

        if(newText.length() == 0){
            mView.search(null);
        }
        return true;
    }
}
