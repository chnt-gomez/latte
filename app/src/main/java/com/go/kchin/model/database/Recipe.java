package com.go.kchin.model.database;

import com.orm.SugarRecord;

/**
 * Created by MAV1GA on 06/01/2017.
 */

public class Recipe extends SugarRecord {

    public Material material;
    public Product product;

    public float MaterialAmount;

}
