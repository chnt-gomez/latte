package com.go.kchin.presenter.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;

import com.go.kchin.R;
import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.model.database.Department;
import com.go.kchin.model.database.Material;
import com.go.kchin.model.database.Product;
import com.go.kchin.model.database.Recipe;
import com.go.kchin.view.fragment.ProductDetailFragment;

import java.util.List;


/**
 * Created by MAV1GA on 09/01/2017.
 */

public class ProductActivity extends BaseActivity implements MainMVP.ProductPresenterOps{

    public static final String PRODUCT_ID = "product_id";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_product_details);
    }

    @Override
    protected void init() {
        super.init();
        if (getIntent().getExtras().containsKey(PRODUCT_ID)){
            attachFragment(ProductDetailFragment.newInstance(getIntent().getExtras().getLong(PRODUCT_ID)));
        }
    }


    @Override
    public Product getProduct(final long id) {
        return mModel.getProduct(id);
    }

    @Override
    public String getDepartmentNameFromProduct(long departmentId) {
        Department department = Department.findById(Department.class, departmentId);
        return department.departmentName;
    }

    @Override
    public void saveProduct(Product product) {
        mModel.updateProduct(product);
    }

    @Override
    public List<Department> getAllDepartments() {
        return mModel.getAllDepartments();
    }

    @Override
    public void pickDepartment(long aLong, Department item) {
        mModel.setProductDepartment(aLong, item);
    }

    @Override
    public List<Recipe> getRecipe(long aLong) {
        return mModel.getRecipeFromProduct(aLong);
    }

    @Override
    public List<Material> getAllMaterials() {
        return mModel.getAllMaterials();
    }

    @Override
    public void addMaterialToProductRecipe(long aLong, Material item) {
        mModel.addMaterialToRecipe(aLong, item);
    }

    @Override
    public void addMaterialToProductRecipe(long productId, long materialId) {
        mModel.addMaterialToRecipe(productId, materialId);
    }

    @Override
    public void buyMore(long productId, float arg, float cost) {
        mModel.buyProduct(productId, arg, cost);
    }

    @Override
    public void setRecipeMaterialAmount(long recipeId, float amount) {
        mModel.updateRecipe(recipeId, amount);
    }

    @Override
    public void setInventory(long productId, float arg) {
        mModel.setProductInventory(productId, arg);
    }

    @Override
    public void addMaterial(Material newMaterial) {
        mModel.addMaterial(newMaterial);
    }

    @Override
    public void addDepartment(Department department) {
        mModel.addDepartment(department);
    }
}
