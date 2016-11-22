package com.go.kchin.activities;

import android.app.SearchManager;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import com.go.kchin.R;
import com.go.kchin.database.DatabaseHelper;
import com.go.kchin.fragments.DepartmentListFragment;
import com.go.kchin.fragments.MaterialListFragment;
import com.go.kchin.fragments.ProductListFragment;
import com.go.kchin.interfaces.FragmentNavigationService;
import com.go.kchin.interfaces.InventoryService;
import com.go.kchin.models.Department;
import com.go.kchin.models.Material;
import com.go.kchin.models.Product;

import java.util.List;

public class InventoryActivity extends AppCompatActivity implements InventoryService, FragmentNavigationService {

    DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventary);
        init();
    }

    private void init() {
        helper = new DatabaseHelper(this);
        addFragment(ProductListFragment.newInstance(ProductListFragment.ALL_PRODUCTS));
    }

    private void addFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_fragment_holder, fragment ,fragment.getTag()).commit();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.menu_see_products:
                moveToFragment(ProductListFragment.newInstance(ProductListFragment.ALL_PRODUCTS));
                break;
            case R.id.menu_see_materials:
                moveToFragment(MaterialListFragment.newInstance(MaterialListFragment.ALL_MATERIALS));
                break;
            case R.id.menu_see_departments:
                moveToFragment(DepartmentListFragment.newInstance());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Material getMaterial(long id) {
        return helper.getMaterialFromId(id);
    }

    @Override
    public long addMaterial(Material material) {
       return helper.addMaterial(material);
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
    public void updateMaterial(long id, Material material) {
        log("UPDATED: "+helper.updateMaterial(id, material));
    }

    @Override
    public Product getProduct(long id) {
        return helper.getProduct(id);
    }

    @Override
    public void addProduct(Product product) {
        log(""+helper.addProduct(product));
    }

    @Override
    public List<Product> getProducts() {
        return helper.getProducts();
    }

    @Override
    public void updateProduct(long id, Product product) {
        helper.updateProduct(id, product);
    }

    @Override
    public Department getDepartment(long id) {
        return helper.getDepartment(id);
    }

    @Override
    public List<Department> getDepartments() {
        return helper.getDepartments();
    }

    @Override
    public long updateDepartment(long id, Department department) {
       return helper.updateDepartment(id, department);
    }

    @Override
    public void addDepartment(Department department) {
        log(String.valueOf(helper.addDepartment(department)));
    }

    @Override
    public List<Product> getProductsFromMaterial(long materialId) {
        return helper.getProductsFromMaterial(materialId);
    }

    @Override
    public List<Material> getMaterialsFromProduct(long productId) {
        return helper.getRecipe(productId);
    }

    @Override
    public void addMaterialToRecipe(long materialId, long productId) {
        helper.addMaterialToRecipe(materialId, productId);
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
