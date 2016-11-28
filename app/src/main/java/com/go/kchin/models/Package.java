package com.go.kchin.models;

import android.database.Cursor;

import com.go.kchin.database.PackageContract;

import java.util.List;

/**
 * Created by MAV1GA on 10/11/2016.
 */

public class Package {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getItems() {
        return items;
    }

    public void setItems(List<Product> items) {
        this.items = items;
    }

    public float getPrice(){
        return this.price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    private String name;
    private List<Product> items;
    private float price;

    public long getPackageId() {
        return packageId;
    }

    public void setPackageId(long packageId) {
        this.packageId = packageId;
    }

    private long packageId;

    public static Package fromCursor(Cursor c) {
        Package aPackage = new Package();
        aPackage.setName(c.getString(c.getColumnIndex(PackageContract.C_NAME)));
        aPackage.setPrice(c.getFloat(c.getColumnIndex(PackageContract.C_COST)));
        aPackage.setPackageId(c.getLong(c.getColumnIndex(PackageContract.C_ID)));
        return aPackage;
    }
}
