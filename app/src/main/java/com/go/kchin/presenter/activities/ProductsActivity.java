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

import com.go.kchin.R;
import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.model.database.Product;
import com.go.kchin.view.fragment.ProductListFragment;
import java.util.List;


/**
 * Created by MAV1GA on 06/01/2017.
 */

public class ProductsActivity extends BaseActivity implements MainMVP.ProductsPresenterOps,
    SearchView.OnQueryTextListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.products_list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        SearchManager searchManager =
                (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        if(searchView != null){
            searchView.setSearchableInfo(
                    searchManager.getSearchableInfo(getComponentName()));
            searchView.setSubmitButtonEnabled(true);
            searchView.setOnQueryTextListener(this);

        }else{
            Log.w(getClass().getSimpleName(), "Search view is null");
        }
        return true;
    }

    @Override
    public List<Product> getAllProducts() {
        return mModel.getAllProducts();
    }

    @Override
    protected void init() {
        super.init();
        setContentView(R.layout.activity_products);
        attachFragment(ProductListFragment.newInstance());
    }

    @Override
    public void newProduct(Product p) {
        mModel.addProduct(p);
    }

    @Override
    public Product findProduct(long productId) {
        return mModel.getProduct(productId);
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
