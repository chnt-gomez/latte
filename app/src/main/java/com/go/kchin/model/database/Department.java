package com.go.kchin.model.database;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.util.List;

/**
 * Created by MAV1GA on 06/01/2017.
 */

public class Department extends SugarRecord {

    public Department(){}

    public String departmentName;
    public String departmentStatus;

    @Ignore
    public long productsInDepartment;
}
