package com.go.kchin.models;

import android.database.Cursor;

import com.go.kchin.database.DepartmentContract;

/**
 * Created by MAV1GA on 14/11/2016.
 */

public class Department {

    private String departmentName;
    private int productsInDepartment;
    private long departmentId;

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getProductsInDepartment() {
        return productsInDepartment;
    }

    public void setProductsInDepartment(int productsInDepartment) {
        this.productsInDepartment = productsInDepartment;
    }

    public static Department fromCursor(Cursor c){
        Department department = new Department();
        department.setDepartmentId(c.getLong(c.getColumnIndex(DepartmentContract.C_ID)));
        department.setDepartmentName(c.getString(c.getColumnIndex(DepartmentContract.C_NAME)));
        department.setProductsInDepartment(c.getInt(c.getColumnIndex(DepartmentContract.C_PRODUCTS)));
        return department;
    }

    public long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }
}
