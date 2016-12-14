package com.go.kchin.database;

/**
 * Created by MAV1GA on 14/12/2016.
 */

public class ProductInSaleContract {

    private ProductInSaleContract(){}

    public static final String TABLE_NAME = "product_in_sale";
    public static final String C_ID = "product_id";
    public static final String C_SALE_ID = "sale_id";
    public static final String C_PRODUCT_AMOUNT = "product_amount";

    public static final String MAKE_TABLE =
            "CREATE TABLE "+TABLE_NAME+" ("+C_ID+" INTEGER, " +
                    C_SALE_ID + " INTEGER, " +
                    C_PRODUCT_AMOUNT+ "REAL, "+
                    "FOREIGN KEY ("+C_SALE_ID+") REFERENCES " +
                    SaleContract.TABLE_NAME+" ("+SaleContract.C_ID+"))";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME+"; ";

}
