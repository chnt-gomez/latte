package com.go.kchin.model;

import com.go.kchin.model.database.Material;
import com.go.kchin.model.database.Product;

/**
 * Created by MAV1GA on 16/02/2017.
 */

public class PurchaseOrder {

    private Class classType;
    private long purchaseId;
    private float existences;
    private String purchaseName;

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

    public String getPurchaseName() {
        return purchaseName;
    }

    public void setPurchaseName(String purchaseName) {
        this.purchaseName = purchaseName;
    }

    public static PurchaseOrder fromMaterial(Material m) {
        PurchaseOrder pOrder = new PurchaseOrder();
        pOrder.setClassType(m.getClass());
        pOrder.setPurchaseName(m.materialName);
        pOrder.setExistences(m.materialRemaining);
        pOrder.setPurchaseId(m.getId());
        return pOrder;
    }

    public static PurchaseOrder fromProduct(Product p){
        PurchaseOrder pOrder = new PurchaseOrder();
        pOrder.setClassType(p.getClass());
        pOrder.setPurchaseName(p.productName);
        pOrder.setExistences(p.productRemaining);
        pOrder.setPurchaseId(p.getId());
        return pOrder;
    }
}
