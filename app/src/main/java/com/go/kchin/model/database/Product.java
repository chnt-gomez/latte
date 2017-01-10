package com.go.kchin.model.database;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by MAV1GA on 06/01/2017.
 */

public class Product extends SugarRecord {

    public Product(){}

    public String productName;
    public float productRemaining;
    public int productMeasureType;
    public String productMeasureUnit;
    public float productSellPrice;
    public int productStatus;
    public float productPurchaseCost;

    public Department department;

    List<Material> getRecipe(){
        return Material.find(Material.class, "product = ?", getId().toString());
    }

}
