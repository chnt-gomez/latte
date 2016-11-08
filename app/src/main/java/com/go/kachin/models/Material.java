package com.go.kachin.models;

/**
 * Created by vicente on 6/11/16.
 */
public class Material {

    private String materialName;
    private String materialUnit;
    private float materialCost;

    public Material(String materialName, String materialUnit, float materialCost) {
        this.materialName = materialName;
        this.materialUnit = materialUnit;
        this.materialCost = materialCost;
    }

    public Material(){}

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
}
