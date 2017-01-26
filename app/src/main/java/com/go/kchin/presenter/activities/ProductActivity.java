package com.go.kchin.presenter.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.model.database.Department;
import com.go.kchin.model.database.Material;
import com.go.kchin.model.database.Product;
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
    public long saveProduct(Product product) {
        return mModel.updateProduct(product);
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
    public List<Material> getRecipe(long aLong) {
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

}
