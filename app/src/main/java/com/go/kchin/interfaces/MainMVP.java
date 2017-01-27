package com.go.kchin.interfaces;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.go.kchin.model.database.Combo;
import com.go.kchin.model.database.Department;
import com.go.kchin.model.database.Material;
import com.go.kchin.model.database.Product;
import com.go.kchin.model.database.Sale;
import com.go.kchin.model.database.SaleTicket;
import com.go.kchin.presenter.activities.BaseActivity;
import com.go.kchin.view.fragment.BaseFragment;

import org.joda.time.DateTime;

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

        void onOperationSuccesfull(String message, long rowId);
        void onOperationSuccesfull(String message);
        void showMessage(String msg);
        void showMessage(int resourceString);
        void showError(String msg);
        void onOperationError(String msg, long rowId);

        //All other ops
    }


    /**
     * Operations offered from Presenter to View
     *      View -> Presenter
     */
    interface PresenterOps{
        void moveToActivity(Class activity, Bundle args);
        void moveToFragment(BaseFragment fragment);
        void setViewLayer(MainMVP.RequiredViewOps fragment);
    }

    /**
     * Operations offered from Presenter to Model
     *      Model -> Presenter
     */
    interface RequiredPresenterOps{

        void onOperationSuccess(String message,long rowId);
        void onOperationSuccess(String message);
        void onOperationError(String message);
        String getStringResource(int stringResource);
        //Any other returning operation
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
         * Creates a new product
         */
        void newProduct(Product p);

        /**
         * Find and returns the Product Id reference
         * @param productId Product Id reference
         */
        Product findProduct(long productId);
    }

    interface ProductPresenterOps{

        /**
         * Returns a Product from the Id
         * @param id the Product Id reference
         */
        Product getProduct(long id);

        /**
         * Returns the Department name from a product Id
         */
        String getDepartmentNameFromProduct(long productId);

        /**
         * Saves an already existing product
         * @param product Product object reference
         */
        long saveProduct(Product product);

        /**
         * Gets all Departments
         */
        List<Department> getAllDepartments();

        /**
         * Updates the current Product with a new Department
         * @param aLong Product id reference
         * @param item Department object reference
         */
        void pickDepartment(long aLong, Department item);

        List<Material> getRecipe(long aLong);

        List<Material> getAllMaterials();

        void addMaterialToProductRecipe(long aLong, Material item);
    }

    interface DepartmentsPresenterOps {

        List<Department> getAllDepartments();
        Department getDepartment(long departmentId);
        void addDepartment(Department department);
        void addProductToDepartment(long productId, long departmentId);

    }

    interface MaterialsPresenterOps {

        /**
         * Returns all saved Materials
         */
        List<Material> getAllMaterials();

        /**
         * Saves a new Material
         * @param material Material object reference
         */
        void newMaterial(Material material);

    }

    interface MaterialPresenterOps {

        /**
         * Returns a saved Material
         * @param materialId Material Id reference
         */
        Material getMaterial(long materialId);

    }

    interface DetailedReportPresenterOps {

        /**
         * Returns all SaleTickets from the system date
         */
        List<SaleTicket> getDaySaleTickets(DateTime date);

        /**
         * Returns all Sales related to a SaleTicket
         * @param saleTicket SaleTicket object reference
         */
        List<Sale> getSalesInTicket(SaleTicket saleTicket);


    }

    interface QuickReportPresenterOps{

        /**
         * Returns the total of sales of a given date
         */
        float getDaySaleTotal(DateTime date);

        /**
         * Returns the total of purchases of a given date
         */
        float getDayPurchasesTotal(DateTime date);

        /**
         * Returns the total of earnings with the sales discount applies
         */
        float getNetEarnings(DateTime date);

        /**
         * Returns an array with the initial id of the recorded sale, and with the final
         * id of the sale
         */
        long[] getRecordedTicketsIdRange(DateTime date);
    }

    interface SalesPresenterOps {

        void applyCurrentSale(List<Sale> sale);

        List<Product> getProducts();

        /**
         * Converts a product into a sale and adds it to the Current Sale adapter
         * @param product Product object reference
         */
    }

    /**
     * Model operations offered to Presenter
     * Presenter -> Model
     */
    interface ModelOps{

        /**
         * Materials Operations ---------------------------------
         */

        /**
         * Saves a material
         *
         * @param newMaterial Material reference
         */
        void addMaterial(Material newMaterial);

        /**
         * Return all materials with status = active
         */
        List<Material> getAllMaterials();

        /**
         * Returns a material from the id
         *
         * @param materialId Material id reference
         */
        Material getMaterialFromId(long materialId);

        /**
         * Updates the parameters of a Material
         *
         * @param materialId        Material id reference
         * @param newMaterialParams new Material parameters
         */
        void updateMaterial(long materialId, Material newMaterialParams);

        /**
         * Sets the status of the Material to active
         *
         * @param materialId Material id reference
         */
        void activateMaterial(long materialId);

        /**
         * Sets the status of the material to inactive
         *
         * @param materialId Material id reference
         */
        void deactivateMaterial(long materialId);

        /**
         * Returns all Materials used in a Recipe from a Product
         *
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
         *
         * @param newProduct Product reference
         */
        void addProduct(Product newProduct);

        /**
         * Returns all Products
         */
        List<Product> getAllProducts();

        /**
         * Returns all Products from the given Combo
         *
         * @param comboId Combo Id reference
         */
        List<Product> getProductsFromCombo(long comboId);

        /**
         * Returns all Products using given Material
         *
         * @param materialId Material Id reference
         */
        List<Product> getProductsFromMaterial(long materialId);

        /**
         * Returns a Product from Id
         *
         * @param productId Product Id reference
         */
        Product getProduct(long productId);

        /**
         * Updates a Product
         */
        long updateProduct(Product product);

        void addMaterialToRecipe(long aLong, Material item);

        /**
         * Sets a Department reference of a Product
         *
         * @param aLong Product id reference
         * @param item  Department reference
         */
        void setProductDepartment(long aLong, Department item);

        /**
         * Department operations ----------------------------------------
         */

        /**
         * Returns all saved Departments
         */
        List<Department> getAllDepartments();

        /**
         * Returns a department from given Id
         *
         * @param departmentId Department Id reference
         */
        Department getDepartment(long departmentId);

        /**
         * Saves a new Department
         *
         * @param newDepartment Department reference
         */
        void addDepartment(Department newDepartment);

        /**
         * Updates a Department with the given Id
         *
         * @param departmentId Department Id reference
         * @param department   Department new parameters
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
         *
         * @param combo Combo reference parameters
         */
        void addCombo(Combo combo);

        /**
         * Returns a Combo from the Id
         *
         * @param comboId Combo Id reference
         */
        Combo getCombo(long comboId);

        /**
         * Updates a Combo parameters from the Id
         *
         * @param comboId Combo Id reference
         * @param combo   Combo parameters reference
         */
        void updateCombo(long comboId, Combo combo);

        /**
         * Adds an existing product to a Combo
         *
         * @param comboId   Combo Id reference
         * @param productId Product Id reference
         */
        void addProductToCombo(long comboId, long productId, float materialAmount);

        List<SaleTicket> getTicketsFromDate(DateTime time);

        List<Sale> getSalesInTicket(SaleTicket ticket);

        /**
         * Sales operations -----------------------------------------------
         */
        void applySale(List<Sale> currentSale);

        void onDestroy();

        void onConfigurationChanged(MainMVP.RequiredPresenterOps presenter);



    }


}
