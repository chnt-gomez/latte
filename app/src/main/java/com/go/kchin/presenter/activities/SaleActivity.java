package com.go.kchin.presenter.activities;

import android.app.SearchManager;
import android.content.Context;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.go.kchin.R;
import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.model.database.Product;
import com.go.kchin.model.database.Sale;
import com.go.kchin.view.fragment.SellProductFragment;

import java.util.List;

/**
 * Created by MAV1GA on 18/01/2017.
 */

public class SaleActivity extends BaseActivity implements MainMVP.SalesPresenterOps, SearchView.OnQueryTextListener{


    @Override
    public void onOperationSuccess(String message) {
        super.onOperationSuccess(message);
    }

    @Override
    protected void init() {
        super.init();
        attachFragment(SellProductFragment.newInstance());
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
        if (searchView != null) {
            searchView.setSearchableInfo(
                    searchManager.getSearchableInfo(getComponentName()));
            searchView.setSubmitButtonEnabled(true);
            searchView.setOnQueryTextListener(this);

        } else {
            Log.w(getClass().getSimpleName(), "SEARCH VIEW IS NULL");
        }

        menu.findItem(R.id.filter).setVisible(false);
        invalidateOptionsMenu();

        return true;
    }

    @Override
    public void applyCurrentSale(List<Sale> currentSale) {
        mModel.applySale(currentSale);
    }

    @Override
    public List<Product> getProducts() {
        return mModel.getAllProducts();
    }

    @Override
    public List<Product> getAllProducts(String query) {
        return mModel.getProducts(query);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
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
