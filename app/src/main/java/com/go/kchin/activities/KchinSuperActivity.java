package com.go.kchin.activities;

import android.app.SearchManager;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import com.go.kchin.R;
import com.go.kchin.database.DatabaseHelper;
import com.go.kchin.fragments.DepartmentListFragment;
import com.go.kchin.fragments.MaterialListFragment;
import com.go.kchin.fragments.PackageListFragment;
import com.go.kchin.fragments.ProductListFragment;
import com.go.kchin.interfaces.FragmentNavigationService;
import com.go.kchin.interfaces.InventoryService;
import com.go.kchin.interfaces.SalesService;
import com.go.kchin.interfaces.SearchService;
import com.go.kchin.models.Department;
import com.go.kchin.models.Material;
import com.go.kchin.models.Operation;
import com.go.kchin.models.Package;
import com.go.kchin.models.Product;
import com.go.kchin.models.Sale;

import java.util.List;

/**
 * Created by MAV1GA on 13/12/2016.
 */

public class KchinSuperActivity extends AppCompatActivity implements InventoryService,
        FragmentNavigationService, SalesService {

    protected SearchService searchService;
    protected DatabaseHelper helper;

    public void setSearchService(SearchService callback){
        this.searchService = callback;
    }

    protected void init(){
        helper = new DatabaseHelper(getApplicationContext());
    }

    protected void addFragment(Fragment fragment){
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
                searchService.onSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 0) {
                    if (newText.substring(newText.length() - 1, newText.length()).equals(" ")) {
                        searchService.onSearch(newText);
                        return true;
                    }
                    return false;
                }
                searchService.onSearch(null);
                return true;
            }
        });

        return true;
    }

    @Override
    public void moveToFragment(Fragment fragment, boolean addToBackStackTrace) {
        if (addToBackStackTrace){
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.main_fragment_holder, fragment).addToBackStack(null)
                    .commit();
        }else{
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.main_fragment_holder, fragment).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.menu_see_products:
                moveToFragment(ProductListFragment.newInstance(), false);
                break;
            case R.id.menu_see_materials:
                moveToFragment(MaterialListFragment.newInstance(MaterialListFragment.ALL_MATERIALS), false);
                break;
            case R.id.menu_see_departments:
                moveToFragment(DepartmentListFragment.newInstance(),false);
                break;
            case R.id.menu_see_package:
                moveToFragment(PackageListFragment.newInstance(PackageListFragment.ALL_PACKAGES),false);
                break;
        }
        return super.onOptionsItemSelected(item);
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

    @Override
    public Material getMaterial(long id) {
        return helper.getMaterialFromId(id);
    }

    @Override
    public Operation addMaterial(Material material) {
        return helper.addMaterial(material);
    }

    @Override
    public List<Material> getMaterials() {
        return helper.getMaterials();
    }

    @Override
    public void updateMaterial(long id, Material material) {
        helper.updateMaterial(id, material);
    }

    @Override
    public Product getProduct(long id) {
        return helper.getProduct(id);
    }

    @Override
    public Operation addProduct(Product product) {
        return helper.addProduct(product);
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
    public List<Department> getDepartments(String query) {
        return helper.getDepartments(query);
    }

    @Override
    public long updateDepartment(long id, Department department) {
        return helper.updateDepartment(id, department);
    }

    @Override
    public Operation addDepartment(Department department) {
        return helper.addDepartment(department);
    }

    @Override
    public List<Product> getProductsFromMaterial(long materialId) {
        return helper.getProductsFromMaterial(materialId);
    }

    @Override
    public List<Material> getMaterialsFromProduct(long aLong) {
        return helper.getRecipe(aLong);
    }

    @Override
    public List<Material> updateRecipe(long materialId, long productId) {
        return helper.updateRecipe(materialId, productId);
    }

    @Override
    public List<Material> updateRecipe(long materialId, long productId, float amount) {
        return helper.updateRecipe(materialId, productId, amount);
    }

    @Override
    public List<Product> searchProducts(String query) {
        return helper.getProducts(query);
    }

    @Override
    public List<Department> searchDepartment(String args) {
        return null;
    }

    @Override
    public List<Material> searchMaterials(String query) {
        return helper.getMaterials(query);
    }

    @Override
    public List<Package> getPackages() {
        return helper.getPackages();
    }

    @Override
    public List<Package> getPackages(long productId) {
        return null;
    }

    @Override
    public Package getPackage(long packageId) {
        return helper.getPackage(packageId);
    }

    @Override
    public Operation addPackage(Package arg) {
        return helper.addPackage(arg);
    }

    @Override
    public void updatePackage(long packageId, Package aPackage) {
        helper.updatePackage(packageId, aPackage);
    }

    @Override
    public long addProductToPackage(long packageId, long productId) {
        return helper.addProductToPackage(packageId, productId);
    }

    @Override
    public List<Product> getProductsFromPackage(long objectId) {
        return helper.getProductsInPackage(objectId);
    }

    @Override
    public void undo(String tableName, long primaryId, long secondaryId) {
        helper.undoTransaction(tableName, primaryId, secondaryId);
    }

    @Override
    public void delete(String tableName, long primaryId, long secondaryId) {
        helper.erase(tableName, primaryId, secondaryId);
    }

    @Override
    public void addToSale(Sale sale) {

    }

    @Override
    public void undoWithProductId(long productId) {

    }

    @Override
    public void returnSale(Sale sale) {

    }

    @Override
    public void applySale(List<Sale> sale) {

    }

    @Override
    public List<Sale> getCurrentSale() {
        return null;
    }

    @Override
    public void cancelSale() {

    }
}
