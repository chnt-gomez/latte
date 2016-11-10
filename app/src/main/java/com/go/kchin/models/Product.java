package com.go.kchin.models;

import android.database.Cursor;

import com.go.kchin.database.ProductContract;

/**
 * Created by vicente on 6/11/16.
 */
public class Product {

    private String productName, productUnit;
    private long productId;
    private float productSalePrice, productPurchasePrice, productAmount, productSold;
    private int productDepartment, productStatus;
    private Recipe productRecipe;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public float getProductSalePrice() {
        return productSalePrice;
    }

    public void setProductSalePrice(float productSalePrice) {
        this.productSalePrice = productSalePrice;
    }

    public float getProductPurchasePrice() {
        return productPurchasePrice;
    }

    public void setProductPurchasePrice(float productPurchasePrice) {
        this.productPurchasePrice = productPurchasePrice;
    }

    public float getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(float productAmount) {
        this.productAmount = productAmount;
    }

    public float getProductSold() {
        return productSold;
    }

    public void setProductSold(float productSold) {
        this.productSold = productSold;
    }

    public int getProductDepartment() {
        return productDepartment;
    }

    public void setProductDepartment(int productDepartment) {
        this.productDepartment = productDepartment;
    }

    public int getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(int productStatus) {
        this.productStatus = productStatus;
    }

    public Recipe getProductRecipe() {
        return productRecipe;
    }

    public void setProductRecipe(Recipe productRecipe) {
        this.productRecipe = productRecipe;
    }

    public static Product fromCursor(Cursor c) {
        Product product = new Product();
        product.setProductAmount(c.getFloat(c.getColumnIndex(ProductContract.C_AMOUNT)));
        product.setProductDepartment(c.getInt(c.getColumnIndex(ProductContract.C_DEPARTMENT)));
        product.setProductId(c.getLong(c.getColumnIndex(ProductContract.C_ID)));
        product.setProductName(c.getString(c.getColumnIndex(ProductContract.C_NAME)));
        product.setProductPurchasePrice(c.getFloat(c.getColumnIndex(ProductContract.C_COST)));
        product.setProductSalePrice(c.getFloat(c.getColumnIndex(ProductContract.C_PRICE)));
        product.setProductUnit(c.getString(c.getColumnIndex(ProductContract.C_UNIT)));
        return product;
    }
}
