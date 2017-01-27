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
    public int productMeasureUnit;
    public float productSellPrice;
    public int productStatus;
    public float productPurchaseCost;

    public Department department;

    List<Material> getRecipe(){
        return Material.find(Material.class, "product = ?", getId().toString());
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getProductRemaining() {
        return productRemaining;
    }

    public void setProductRemaining(float productRemaining) {
        this.productRemaining = productRemaining;
    }

    public int getProductMeasureType() {
        return productMeasureType;
    }

    public void setProductMeasureType(int productMeasureType) {
        this.productMeasureType = productMeasureType;
    }

    public int getProductMeasureUnit() {
        return productMeasureUnit;
    }

    public void setProductMeasureUnit(int productMeasureUnit) {
        this.productMeasureUnit = productMeasureUnit;
    }

    public float getProductSellPrice() {
        return productSellPrice;
    }

    public void setProductSellPrice(float productSellPrice) {
        this.productSellPrice = productSellPrice;
    }

    public int getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(int productStatus) {
        this.productStatus = productStatus;
    }

    public float getProductPurchaseCost() {
        return productPurchaseCost;
    }

    public void setProductPurchaseCost(float productPurchaseCost) {
        this.productPurchaseCost = productPurchaseCost;
    }

    public String getDepartmentName() {
       if(department == null){
           return " - ";
       }else{
           return department.departmentName;
       }
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
