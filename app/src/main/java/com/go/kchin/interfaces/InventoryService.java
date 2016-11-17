package com.go.kchin.interfaces;

import android.support.v4.app.Fragment;

import com.go.kchin.models.Department;
import com.go.kchin.models.Material;
import com.go.kchin.models.Product;

import java.util.List;

/**
 * Created by MAV1GA on 09/11/2016.
 */

public interface InventoryService {

    Material getMaterial(long id);

    long addMaterial(Material material);

    List<Material> getMaterials();

    void updateMaterial(long id, Material material);

    Product getProduct(long id);

    void addProduct(Product product);

    List<Product> getProducts();

    void updateProduct(long id, Product product);

    Department getDepartment(long id);

    List<Department> getDepartments();

    long updateDepartment(long id, Department department);

    void addDepartment(Department department);



}
