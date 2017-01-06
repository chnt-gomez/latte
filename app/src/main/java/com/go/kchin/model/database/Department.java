package com.go.kchin.model.database;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by MAV1GA on 06/01/2017.
 */

public class Department extends SugarRecord {

    public Department(){}

    public String departmentName;
    public String departmentStatus;

    List<Combo> getCombos(){
        return Combo.find(Combo.class, "department = ?", getId().toString());
    }

    List<Product> getProducts(){
        return Product.find(Product.class, "department = ?", getId().toString());
    }



}
