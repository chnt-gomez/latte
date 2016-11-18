package com.go.kchin.models;

import android.database.Cursor;

import com.go.kchin.database.MaterialContract;
import com.go.kchin.database.RecipeContract;

/**
 * Created by vicente on 6/11/16.
 */
public class Material {

    private String materialName;
    private String materialUnit;
    private float materialAmount;
    private float materialCost;
    private long id;

    public Material(String materialName, String materialUnit, float materialCost) {
        this.materialName = materialName;
        this.materialUnit = materialUnit;
        this.materialCost = materialCost;
    }

    public Material(){}

    public Material(String materialName, String materialUnit, float materialCost, long materialId) {
        this.materialName = materialName;
        this.materialUnit = materialUnit;
        this.materialCost = materialCost;
        this.id = materialId;
    }

    public Material(String materialName, String materialUnit, float materialCost, long materialId,
                    float materialAmount) {
        this.materialName = materialName;
        this.materialUnit = materialUnit;
        this.materialCost = materialCost;
        this.id = materialId;
        this.materialAmount = materialAmount;
    }


    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialUnit() {
        return materialUnit;
    }

    public void setMaterialUnit(String materialUnit) {
        this.materialUnit = materialUnit;
    }

    public float getMaterialCost() {
        return materialCost;
    }

    public void setMaterialCost(float materialCost) {
        this.materialCost = materialCost;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getMaterialAmount() {
        return materialAmount;
    }

    public void setMaterialAmount(float materialAmount) {
        this.materialAmount = materialAmount;
    }

    public static Material fromCursor(Cursor c, boolean isRecipe) {
        Material material = new Material();
        if (isRecipe)
            material.setMaterialAmount(c.getFloat(c.getColumnIndex(RecipeContract.C_QUANTITY)));
        else
            material.setMaterialAmount(c.getFloat(c.getColumnIndex(MaterialContract.C_AMOUNT)));
        material.setMaterialName(c.getString(c.getColumnIndex(MaterialContract.C_NAME)));
        material.setMaterialUnit(c.getString(c.getColumnIndex(MaterialContract.C_UNIT)));
        material.setMaterialCost(c.getFloat(c.getColumnIndex(MaterialContract.C_COST)));
        material.setId(c.getLong(c.getColumnIndex(MaterialContract.C_ID)));
        return material;
    }
}
