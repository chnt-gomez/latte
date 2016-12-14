package com.go.kchin.database;

/**
 * Created by MAV1GA on 14/12/2016.
 */

public class SaleContract {

    private SaleContract(){}

    public static final String TABLE_NAME = "sales";
    public static final String C_ID = "sale_id";
    public static final String C_TIME = "sale_date";
    public static final String C_TOTAL = "sale_total";
    public static final String C_VENDOR = "sale_vendor";

    public static final String MAKE_TABLE =
            "CREATE TABLE "+TABLE_NAME+ " ("+C_ID+" INTEGER PRIMARY KEY AUTOINREMENT, " +
                    C_TIME +" INTEGER, " +
                    C_TOTAL+ " REAL, " +
                    C_VENDOR+ " TEXT)";

    public static final String DROP_TABLE =
            "DROP TABLE IS EXISTS "+TABLE_NAME+";";

}
