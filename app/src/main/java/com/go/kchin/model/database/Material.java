package com.go.kchin.model.database;

import com.orm.SugarRecord;

/**
 * Created by MAV1GA on 06/01/2017.
 */

public class Material extends SugarRecord {

    public Material(){}

    public String materialName;
    public int materialMeasure;
    public float materialRemaining;
    public float materialPurchaseCost;
    public int materialStatus;
    public int materialIndicator;

}
