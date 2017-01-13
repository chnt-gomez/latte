package com.go.kchin.model;

import android.util.Log;

import com.go.kchin.R;
import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.model.database.Combo;
import com.go.kchin.model.database.Department;
import com.go.kchin.model.database.Material;
import com.go.kchin.model.database.Product;
import com.go.kchin.model.database.ProductsInCombo;
import com.go.kchin.model.database.Recipe;

import java.util.List;

/**
 * Created by MAV1GA on 06/01/2017.
 */

public class DatabaseEngine implements MainMVP.ModelOps{

    private static DatabaseEngine instance;

    private static MainMVP.RequiredPresenterOps mPresenter;

    public static DatabaseEngine getInstance(MainMVP.RequiredPresenterOps presenter){
        if (instance == null){
            instance = new DatabaseEngine();
        }
        instance.setPresenter(presenter);
        return instance;

    }

    private void setPresenter(MainMVP.RequiredPresenterOps presenter){
        mPresenter = presenter;
    }

    private DatabaseEngine (){}


    @Override
    public void addMaterial(Material newMaterial) {
        final long operationId = newMaterial.save();
        mPresenter.onOperationSuccess(mPresenter.getStringResource(R.string.material_saved),
                operationId);
    }

    @Override
    public List<Material> getAllMaterials() {
        return Material.listAll(Material.class);
    }

    @Override
    public Material getMaterialFromId(long materialId) {
        return Material.findById(Material.class, materialId);
    }

    @Override
    public void updateMaterial(long materialId, Material newMaterialParams) {
        Material material = Material.findById(Material.class, materialId);
        material.materialMeasure = newMaterialParams.materialMeasure;
        material.materialName = newMaterialParams.materialName;
        material.materialPurchaseCost = newMaterialParams.materialPurchaseCost;
        material.materialRemaining = newMaterialParams.materialRemaining;
        material.materialStatus = newMaterialParams.materialStatus;
        mPresenter.onOperationSuccess(mPresenter.getStringResource(R.string.saved));
    }

    @Override
    public void activateMaterial(long materialId) {
        Material material = Material.findById(Material.class, materialId);
        material.materialStatus = 1;
        material.save();
        mPresenter.onOperationSuccess(mPresenter.getStringResource(R.string.active));
    }

    @Override
    public void deactivateMaterial(long materialId) {
        Material material = Material.findById(Material.class, materialId);
        material.materialStatus = 0;
        material.save();
        mPresenter.onOperationSuccess(mPresenter.getStringResource(R.string.deactivated));
    }

    @Override
    public List<Material> getRecipeFromProduct(long productId) {
        return Material.findWithQuery(
                Material.class, mPresenter.getStringResource(R.string.q_get_recipe_from_product),
                String.valueOf(productId));
    }

    @Override
    public void updateRecipe(long recipeId, float newAmount) {
        Recipe recipe = Recipe.findById(Recipe.class, recipeId);
        recipe.MaterialAmount = newAmount;
        recipe.save();
        mPresenter.onOperationSuccess(mPresenter.getStringResource(R.string.saved));
    }

    @Override
    public void addProduct(Product newProduct) {
        final long operationId =newProduct.save();
        mPresenter.onOperationSuccess(mPresenter.getStringResource(R.string.product_saved),
                operationId);
    }

    @Override
    public List<Product> getAllProducts() {
        return Product.listAll(Product.class);
    }

    @Override
    public List<Product> getProductsFromCombo(long comboId) {
        return Product.findWithQuery(Product.class, mPresenter.
                getStringResource(R.string.q_get_products_in_combo),
                String.valueOf(comboId));
    }

    @Override
    public List<Product> getProductsFromMaterial(long materialId) {
        return Product.findWithQuery(Product.class, mPresenter.getStringResource(
                R.string.q_get_products_from_material)
        );
    }

    @Override
    public Product getProduct(long productId) {
        return Product.findById(Product.class, productId);
    }

    @Override
    public List<Department> getAllDepartments() {
        return Department.listAll(Department.class);
    }

    @Override
    public Department getDepartment(long departmentId) {
        return Department.findById(Department.class, departmentId);
    }

    @Override
    public void addDepartment(Department newDepartment) {
        newDepartment.save();
        mPresenter.onOperationSuccess(mPresenter.getStringResource(R.string.department_saved));
    }

    @Override
    public void updateDepartment(long departmentId, Department department) {
        Department newDepartment = Material.findById(Department.class, departmentId);
        newDepartment.departmentName = department.departmentName;
        newDepartment.departmentStatus = department.departmentStatus;
        newDepartment.save();
        mPresenter.onOperationSuccess(mPresenter.getStringResource(R.string.saved));
    }


    @Override
    public List<Combo> getAllCombos() {
        return Combo.listAll(Combo.class);
    }

    @Override
    public void addCombo(Combo combo) {
        combo.save();
        mPresenter.onOperationSuccess(mPresenter.getStringResource(R.string.combo_saved));
    }

    @Override
    public Combo getCombo(long comboId) {
        return Combo.findById(Combo.class, comboId);
    }

    @Override
    public void updateCombo(long comboId, Combo combo) {
        Combo newCombo = Combo.findById(Combo.class, comboId);
        newCombo.comboName = combo.comboName;
        newCombo.comboSellCost = combo.comboSellCost;
        newCombo.comboStatus = combo.comboStatus;
        newCombo.save();
    }

    @Override
    public void addProductToCombo(long comboId, long productId, float productAmount) {
        ProductsInCombo arg = new ProductsInCombo();
        arg.productAmount = productAmount;
        arg.product = Product.findById(Product.class, productId);
        arg.combo = Combo.findById(Combo.class, comboId);
        arg.save();
        mPresenter.onOperationSuccess(mPresenter.getStringResource(R.string.saved));


    }

    @Override
    public void onDestroy() {
        Log.d(getClass().getSimpleName(), "Called onDestroy() method");
        //TODO: On destroy operations
    }

    @Override
    public void onConfigurationChanged(MainMVP.RequiredPresenterOps presenter) {
        instance.setPresenter(presenter);
    }
}
