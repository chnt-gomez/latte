package com.go.kchin.models;

import android.database.Cursor;

import com.go.kchin.database.RecipeContract;

/**
 * Created by MAV1GA on 16/11/2016.
 */

public class Ingredient {

    private long materialId;
    private float amount;

    public long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(long materialId) {
        this.materialId = materialId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public static Ingredient fromCursor(Cursor c){
        Ingredient ingredient = new Ingredient();
        ingredient.setAmount(c.getFloat(c.getColumnIndex(RecipeContract.C_QUANTITY)));
        ingredient.setMaterialId(c.getLong(c.getColumnIndex(RecipeContract.C_MATERIAL_ID)));
        return ingredient;
    }
}
