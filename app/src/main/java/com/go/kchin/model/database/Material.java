package com.go.kchin.model.database;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by MAV1GA on 06/01/2017.
 */

public class Material extends SugarRecord {

    public Material(){}

    public String materialName;
    public String materialMeasure;
    public float materialRemaining;
    public float materialPurchaseCost;
    public int materialStatus;

    List<Recipe> getRecipes(){
        return Recipe.find(Recipe.class, "material = ?", getId().toString());
    }

}
