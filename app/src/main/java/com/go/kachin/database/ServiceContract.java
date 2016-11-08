package com.go.kachin.database;

/**
 * Created by vicente on 6/11/16.
 */
public class ServiceContract {

    private ServiceContract(){}

    public static final String TABLE_NAME = "services";
    public static final String C_NAME = "service_name";
    public static final String C_ID = "service_id";
    public static final String C_PRICE = "service_price";
    public static final String C_DESC = "service_description";
    public static final String C_STATUS = "service_status";

    public static final String MAKE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " ("+C_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    C_NAME+" TEXT, " +
                    C_PRICE+" REAL, " +
                    C_DESC+" TEXT, " +
                    C_STATUS+" INTEGER" +
                    "); ";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME+"; ";

}
