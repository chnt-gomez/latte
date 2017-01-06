package com.go.kchin.model.database;

import com.orm.SugarRecord;

/**
 * Created by MAV1GA on 06/01/2017.
 */

public class Combo extends SugarRecord {

    public Combo(){}

    public String comboName;
    public float comboSellCost;
    public int comboStatus;

    Department department;

}
