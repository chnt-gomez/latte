package com.go.kchin.models;

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
}
