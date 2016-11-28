package com.go.kchin.database;

/**
 * Created by MAV1GA on 28/11/2016.
 */

public class PackageContract {

    public static final String TABLE_NAME = "packages";
    public static final String C_NAME = "package_name";
    public static final String C_COST = "package_cost";
    public static final String C_ID = "package_id";
    public static final String C_DEPARTMENT = "package_department_id";
    public static final String C_SOLD = "package_sold";
    public static final String C_STATUS = "package_status";

    public static final String MAKE_TABLE = "CREATE TABLE "+TABLE_NAME+ "(" +
            C_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            C_NAME+" TEXT, " +
            C_COST+" REAL, " +
            C_DEPARTMENT+" INTEGER, "+
            C_SOLD+" REAL, " +
            C_STATUS+" INTEGER, " +
            "FOREIGN KEY ("+C_DEPARTMENT+") REFERENCES " +
            DepartmentContract.TABLE_NAME+" ("+DepartmentContract.C_ID+"))";

}
