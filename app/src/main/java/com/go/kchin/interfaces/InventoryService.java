package com.go.kchin.interfaces;

import android.support.v4.app.Fragment;

import com.go.kchin.models.Material;
import com.go.kchin.models.Product;

import java.util.List;

/**
 * Created by MAV1GA on 09/11/2016.
 */

public interface InventoryService {

    Material getMaterial(long id);

    void addMaterial(Material material);

    List<Material> getMaterials();

    void moveToFragment(Fragment fragment);

    void updateMaterial(long id, Material material);

    Product getProduct(long id);

    void addProduct(Product product);

    List<Product> getProducts();

    void updateProduct(long id, Product product);

}
