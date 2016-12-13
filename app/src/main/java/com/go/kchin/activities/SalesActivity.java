package com.go.kchin.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.go.kchin.R;
import com.go.kchin.database.DatabaseHelper;
import com.go.kchin.fragments.QuickSaleFragment;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MAV1GA on 22/11/2016.
 */

public class SalesActivity extends SearchableActivity implements SalesService, InventoryService, FragmentNavigationService, SearchService {

    private List<Sale> currentSale;
    private DatabaseHelper helper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);
        init();
    }

    private void init(){
        getSupportFragmentManager().beginTransaction().add(R.id.main_fragment_holder,
                QuickSaleFragment.newInstance()).commit();
        currentSale = new ArrayList<>();
        helper = new DatabaseHelper(getApplicationContext());
    }

    @Override
    public Material getMaterial(long id) {
        return null;
    }

    @Override
    public Operation addMaterial(Material material) {
        return null;
    }

    @Override
    public List<Material> getMaterials() {
        return null;
    }

    @Override
    public void updateMaterial(long id, Material material) {

    }

    @Override
    public Product getProduct(long id) {
        return null;
    }

    @Override
    public Operation addProduct(Product product) {
        return null;
    }

    @Override
    public List<Product> getProducts() {
        return helper.getProducts();
    }

    @Override
    public void updateProduct(long id, Product product) {

    }

    @Override
    public Department getDepartment(long id) {
        return null;
    }

    @Override
    public List<Department> getDepartments() {
        return null;
    }

    @Override
    public List<Department> getDepartments(String query) {
        return null;
    }

    @Override
    public long updateDepartment(long id, Department department) {
        return 0;
    }

    @Override
    public Operation addDepartment(Department department) {
        return null;
    }

    @Override
    public List<Product> getProductsFromMaterial(long materialId) {
        return null;
    }

    @Override
    public List<Material> getMaterialsFromProduct(long aLong) {
        return null;
    }

    @Override
    public List<Material> updateRecipe(long materialId, long productId) {
        return null;
    }

    @Override
    public List<Material> updateRecipe(long materialId, long productId, float amount) {
        return null;
    }

    @Override
    public List<Product> searchProducts(String query) {
        return null;
    }

    @Override
    public List<Department> searchDepartment(String args) {
        return null;
    }

    @Override
    public List<Material> searchMaterials(String query) {
        return null;
    }

    @Override
    public List<Package> getPackages() {
        return null;
    }

    @Override
    public List<Package> getPackages(long productId) {
        return null;
    }

    @Override
    public Package getPackage(long packageId) {
        return null;
    }

    @Override
    public Operation addPackage(Package arg) {
        return null;
    }

    @Override
    public void updatePackage(long packageId, Package aPackage) {

    }

    @Override
    public long addProductToPackage(long packageId, long productId) {
        return 0;
    }

    @Override
    public List<Product> getProductsFromPackage(long objectId) {
        return null;
    }

    @Override
    public void undo(String tableName, long primaryId, long secondaryId) {

    }

    @Override
    public void delete(String tableName, long primaryId, long secondaryId) {

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
    public void setActionBarTitle(String title) {

    }

    @Override
    public void hideActionBar() {

    }

    @Override
    public void showActionBar() {

    }

    @Override
    public void addToSale(Sale sale) {
        currentSale.add(sale);
    }

    @Override
    public List<Sale> getCurrentSale() {
        return this.currentSale;
    }

    @Override
    public void undoWithProductId(long productId) {
        for (int i=0; i<currentSale.size(); i++){
            if (currentSale.get(i).getObjectId() == productId && currentSale.get(i).isProduct()){
                currentSale.remove(i);
                break;
            }
        }
    }

    @Override
    public void undoWithPackageId(long packageId) {

    }

    @Override
    public void applySale() {
        Toast.makeText(this, "Sale successfull!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSearch(String resultItems) {

    }
}
