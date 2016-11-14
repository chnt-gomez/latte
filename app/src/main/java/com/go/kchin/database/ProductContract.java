package com.go.kchin.database;

/**
 * Created by vicente on 6/11/16.
 */
public class ProductContract {

    private ProductContract(){}

    public static final String TABLE_NAME = "products";
    public static final String C_NAME = "product_name";
    public static final String C_ID = "product_id";
    public static final String C_UNIT = "product_unit";
    public static final String C_AMOUNT = "product_amount";
    public static final String C_PRICE = "product_price";
    public static final String C_STATUS = "product_status";
    public static final String C_SOLD = "product_sold";
    public static final String C_COST = "product_cost";
    public static final String C_DEPARTMENT = "product_department";

    public static final String MAKE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " ("+C_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    C_NAME+" TEXT, " +
                    C_PRICE+" REAL, " +
                    C_STATUS+" INTEGER," +
                    C_UNIT+" TEXT,"+
                    C_AMOUNT +" REAL,"+
                    C_SOLD + " REAL,"+
                    C_COST+ " REAL, "+
                    C_DEPARTMENT+" INTEGER, "+
                    " FOREIGN KEY ("+C_DEPARTMENT+") REFERENCES "
                    +DepartmentContract.TABLE_NAME+" ("+DepartmentContract.C_ID+"))";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME+"; ";

}