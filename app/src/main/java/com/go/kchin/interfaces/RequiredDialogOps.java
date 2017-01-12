package com.go.kchin.interfaces;

import com.go.kchin.model.database.Department;
import com.go.kchin.model.database.Material;
import com.go.kchin.model.database.Product;

/**
 * Created by MAV1GA on 10/01/2017.
 */

public interface RequiredDialogOps {

    /**
     * Calls from Dialog to View
     */
    interface RequiredNewProductOps{
        /**
         * Returns a Product from the Dialog
         * @param product Product reference
         */
        void onNewProduct(Product product);
    }

    interface RequiredNewDepartmentOps{
        /**
         * Returns a Department from the Dialog
         * @param department Department reference
         */
        void onNewDepartment(Department department);
    }


    interface RequiredNewMaterialOps {
        /**
         * Returns a Material from the Dialog
         * @param material Material object reference
         */
        void onNewMaterial(Material material);
    }
}
