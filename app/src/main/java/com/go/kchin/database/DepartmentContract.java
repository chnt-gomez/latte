package com.go.kchin.database;

/**
 * Created by MAV1GA on 14/11/2016.
 */

public class DepartmentContract {

    private DepartmentContract(){}

    public static final String TABLE_NAME ="department";

    public static final String C_ID = "department_id";
    public static final String C_NAME = "department_name";
    public static final String C_STATUS = "department_status";
    public static final String C_PRODUCTS = "products";

    public static final String MAKE_TABLE =
            "CREATE TABLE "+TABLE_NAME+ " ("+C_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    C_NAME+" TEXT, "+C_STATUS+" INTEGER)";


}
