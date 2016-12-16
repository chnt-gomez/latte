package com.go.kchin.models;

import java.util.List;

/**
 * Created by MAV1GA on 09/12/2016.
 */

public class Sale {

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public float getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(float sellPrice) {
        this.sellPrice = sellPrice;
    }

    public float getAmmount() {
        return ammount;
    }

    public void setAmmount(float ammount) {
        this.ammount = ammount;
    }

    public boolean isProduct() {
        return isProduct;
    }

    public void setProduct(boolean product) {
        isProduct = product;
    }

    public boolean isPackage() {
        return isPackage;
    }

    public void setPackage(boolean aPackage) {
        isPackage = aPackage;
    }

    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    private String item;
    private float sellPrice;
    private float ammount;
    private boolean isProduct;
    private boolean isPackage;
    private long objectId;

    public static Sale fromProduct(Product product){
        Sale sale = new Sale();
        sale.item = product.getProductName();
        sale.sellPrice = product.getProductSalePrice();
        sale.ammount = 1;
        sale.isProduct = true;
        sale.isPackage = false;
        sale.objectId = product.getProductId();
        return sale;
    }

    public static Sale fromPackage(Package aPackage){
        Sale sale = new Sale();
        sale.item = aPackage.getName();
        sale.sellPrice = aPackage.getPrice();
        sale.ammount = 1;
        sale.isProduct = false;
        sale.isPackage = true;
        sale.objectId = aPackage.getPackageId();
        return sale;
    }

    public static float getTotalFromSale(List<Sale> sale) {
        float total = 0.0f;
        for (Sale s : sale){
            total += (s.ammount*s.getSellPrice());
        }
        return total;
    }
}
