package com.go.kchin.interfaces;

import com.go.kchin.model.database.Combo;
import com.go.kchin.model.database.Department;
import com.go.kchin.model.database.Material;
import com.go.kchin.model.database.Product;
import com.go.kchin.presenter.activities.BaseActivity;

import java.util.List;

/**
 * Aggregates all communication operations between MVP pattern layer
 */

public interface MainMVP {

    /**
     * View mandatory methods. Available to Presenter
     *      Presenter -> View
     */
    interface RequiredViewOps{
        void showSnackBar(String msg);
        void showAlert(String msg);
        //All other ops
    }

    /**
     * Operations offered from Presenter to View
     *      View -> Presenter
     */
    interface PresenterOps{
        void onConfigurationChanged(RequiredViewOps view);
        void onDestroy(boolean isChangingConfig);
        void moveToActivity(Class activity);
        //Any other presenter ops
    }

    /**
     * Operations offered from Presenter to Model
     *      Model -> Presenter
     */
    interface RequiredPresenterOps{
        void onOperationSuccess(String message);
        void onOperationError(String message);
        String getStringResource(int stringResource);
        //Any other returning operation
    }

    /**
     * Operations offered from ProductsPresenter to Model
     */
    interface RequiredProductsPresenterOps{

    }

    /**
     * Operations offered from ProductsPresenter to View
     */
    interface ProductsPresenterOps{
        /**
         * Returns all products to View
         */
        List<Product> getAllProducts();

        /**
         * Moves to a new Activity to create a product
         */
        void newProduct();

        /**
         * Move to product description
         */
        void moveToProduct(long productId);
    }

    /**
     * Model operations offered to Presenter
     *      Presenter -> Model
     */
    interface ModelOps{

        /**
         * Materials Operations ---------------------------------
         */

        /**
         * Saves a material
         * @param newMaterial Material reference
         */
        void addMaterial(Material newMaterial);

        /**
         * Return all materials with status = active
         */
        List<Material> getAllMaterials();

        /**
         * Returns a material from the id
         * @param materialId Material id reference
         */
        Material getMaterialFromId(long materialId);

        /**
         * Updates the parameters of a Material
         * @param materialId Material id reference
         * @param newMaterialParams new Material parameters
         */
        void updateMaterial(long materialId, Material newMaterialParams);

        /**
         * Sets the status of the Material to active
         * @param materialId Material id reference
         */
        void activateMaterial(long materialId);

        /**
         * Sets the status of the material to inactive
         * @param materialId Material id reference
         */
        void deactivateMaterial(long materialId);

        /**
         * Returns all Materials used in a Recipe from a Product
         * @param productId Product Id reference
         */
        List<Material> getRecipeFromProduct(long productId);

        /**
         * Updates the actual amount of Material used in a Recipe
         */
        void updateRecipe(long recipeId, float newAmount);


        /**
         * Products Operations --------------------------------------
         */

        /**
         * Saves a new product
         * @param newProduct Product reference
         */
        void addProduct(Product newProduct);

        /**
         * Returns all Products
         */
        List<Product> getAllProducts();

        /**
         * Returns all Products from the given Combo
         * @param comboId Combo Id reference
         */
        List<Product> getProductsFromCombo(long comboId);

        /**
         * Returns all Products using given Material
         * @param materialId Material Id reference
         */
        List<Product> getProductsFromMaterial(long materialId);

        /**
         * Returns a Product from Id
         * @param productId Product Id reference
         */
        Product getProduct(long productId);


        /**
         * Department operations ----------------------------------------
         */

        /**
         * Returns all saved Departments
         */
        List<Department> getAllDepartments();

        /**
         * Returns a department from given Id
         * @param departmentId Department Id reference
         */
        Department getDepartment(long departmentId);

        /**
         * Saves a new Department
         * @param newDepartment Department reference
         */
        void addDepartment(Department newDepartment);

        /**
         * Updates a Department with the given Id
         * @param departmentId Department Id reference
         * @param department Department new parameters
         */
        void updateDepartment(long departmentId, Department department);


        /**
         * Combo operations
         */

        /**
         * Returns all saved and active Combos
         */
        List<Combo> getAllCombos();

        /**
         * Saves a new Combo
         * @param combo Combo reference parameters
         */
        void addCombo(Combo combo);

        /**
         * Returns a Combo from the Id
         * @param comboId Combo Id reference
         */
        Combo getCombo(long comboId);

        /**
         * Updates a Combo parameters from the Id
         * @param comboId Combo Id reference
         * @param combo Combo parameters reference
         */
        void updateCombo(long comboId, Combo combo);

        /**
         * Adds an existing product to a Combo
         * @param comboId Combo Id reference
         * @param productId Product Id reference
         */
        void addProductToCombo(long comboId, long productId, float materialAmount);


        /**
         * Sales operations -----------------------------------------------
         */

        //TODO: Continue with sales operations

        void onDestroy();
    }

}
