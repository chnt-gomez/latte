package com.go.kchin.model;

import com.go.kchin.R;
import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.model.database.Combo;
import com.go.kchin.model.database.Department;
import com.go.kchin.model.database.Material;
import com.go.kchin.model.database.Product;
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
        mPresenter = presenter;
        return instance;
    }

    private DatabaseEngine (){}


    @Override
    public void addMaterial(Material newMaterial) {
        newMaterial.save();
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
        material.save();
    }

    @Override
    public void activateMaterial(long materialId) {
        Material material = Material.findById(Material.class, materialId);
        material.materialStatus = 1;
        material.save();
    }

    @Override
    public void deactivateMaterial(long materialId) {
        Material material = Material.findById(Material.class, materialId);
        material.materialStatus = 0;
        material.save();
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
    }

    @Override
    public void addProduct(Product newProduct) {
        newProduct.save();
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
        return null;
    }

    @Override
    public Product getProduct(long productId) {
        return null;
    }

    @Override
    public List<Department> getAllDepartments() {
        return null;
    }

    @Override
    public Department getDepartment(long departmentId) {
        return null;
    }

    @Override
    public void addDepartment(Department newDepartment) {

    }

    @Override
    public void updateDepartment(long departmentId, Department department) {

    }

    @Override
    public List<Combo> getAllCombos() {
        return null;
    }

    @Override
    public void addCombo(Combo combo) {

    }

    @Override
    public Combo getCombo(long comboId) {
        return null;
    }

    @Override
    public void updateCombo(long comboId, Combo combo) {

    }

    @Override
    public void addProductToCombo(long comboId, long productId) {

    }

    @Override
    public void onDestroy() {

    }
}
