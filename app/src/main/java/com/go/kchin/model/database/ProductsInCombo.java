package com.go.kchin.model.database;

import com.orm.SugarRecord;

/**
 * Created by MAV1GA on 06/01/2017.
 */

public class ProductsInCombo extends SugarRecord {

    public ProductsInCombo(){}

    Product product;
    Combo combo;

    public float productAmount;

}
