package com.go.kchin.model;

/**
 * Created by vicente on 22/02/17.
 */
public class SimplePurchase {


    public float getPurchasedItems() {
        return purchasedItems;
    }

    public void setPurchasedItems(float purchasedItems) {
        this.purchasedItems = purchasedItems;
    }

    public float getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(float purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    private float purchasedItems;
    private float purchaseAmount;

}
