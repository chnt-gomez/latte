package com.go.kchin.interfaces;

import com.go.kchin.models.Department;
import com.go.kchin.models.Material;
import com.go.kchin.models.Operation;
import com.go.kchin.models.Product;
import com.go.kchin.models.Package;

import java.util.List;

/**
 * Created by MAV1GA on 09/11/2016.
 */

public interface InventoryService {

    Material getMaterial(long id);

    Operation addMaterial(Material material);

    List<Material> getMaterials();

    void updateMaterial(long id, Material material);

    Product getProduct(long id);

    Operation addProduct(Product product);

    List<Product> getProducts();

    void updateProduct(long id, Product product);

    Department getDepartment(long id);

    List<Department> getDepartments();

    List<Department> getDepartments(String query);

    long updateDepartment(long id, Department department);

    Operation addDepartment(Department department);

    List<Product> getProductsFromMaterial(long materialId);


    List<Material> getMaterialsFromProduct(long aLong);

    List<Material> updateRecipe(long materialId, long productId);

    List<Material> updateRecipe(long materialId, long productId, float amount);

    List<Product> searchProducts(String query);

    List<Department> searchDepartment(String args);

    List<Material> searchMaterials(String query);

    /*
        Packages methods
     */
    List<com.go.kchin.models.Package> getPackages();
    List<com.go.kchin.models.Package> getPackages(long productId);
    com.go.kchin.models.Package getPackage(long packageId);
    List<Package> addPackage(Package arg);


}
