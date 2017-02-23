package com.go.kchin.model;

import com.go.kchin.model.database.Material;
import com.go.kchin.model.database.Product;

/**
 * Created by MAV1GA on 16/02/2017.
 */

public class DepletedItem {

    private Class classType;
    private long purchaseId;
    private float existences;
    private String itemName;

    public Class getClassType() {
        return classType;
    }

    public void setClassType(Class classType) {
        this.classType = classType;
    }

    public long getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(long purchaseId) {
        this.purchaseId = purchaseId;
    }

    public float getExistences() {
        return existences;
    }

    public void setExistences(float existences) {
        this.existences = existences;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public static DepletedItem fromMaterial(Material m) {
        DepletedItem pOrder = new DepletedItem();
        pOrder.setClassType(m.getClass());
        pOrder.setItemName(m.materialName);
        pOrder.setExistences(m.materialRemaining);
        pOrder.setPurchaseId(m.getId());
        return pOrder;
    }

    public static DepletedItem fromProduct(Product p){
        DepletedItem pOrder = new DepletedItem();
        pOrder.setClassType(p.getClass());
        pOrder.setItemName(p.productName);
        pOrder.setExistences(p.productRemaining);
        pOrder.setPurchaseId(p.getId());
        return pOrder;
    }
}
