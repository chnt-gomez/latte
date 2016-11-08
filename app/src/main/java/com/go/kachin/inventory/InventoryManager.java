package com.go.kachin.inventory;

import android.content.Context;

import com.go.kachin.models.Material;
import com.go.kachin.models.Product;
import com.go.kachin.models.Service;

/**
 * Created by vicente on 6/11/16.
 */
public class InventoryManager {

    public static InventoryManager getInstance(Context context){
        return new InventoryManager();
    }

    public void createProduct(Product product){

    }

    public void createService(Service service){

    }

    public void addToInventory(Material... materials){

    }

    public void addToInventory(Product product, int quantity){

    }

    public void addToInventory(Service service, int quantity){

    }

}
