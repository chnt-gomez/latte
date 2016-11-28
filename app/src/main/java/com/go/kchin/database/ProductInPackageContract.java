package com.go.kchin.database;

/**
 * Created by MAV1GA on 28/11/2016.
 */

public class ProductInPackageContract {

    public static final String TABLE_NAME = "product_in_package";
    public static final String C_PRODUCT = "product_id";
    public static final String C_PACKAGE = "package_id";
    public static final String C_PRODUCT_AMOUNT = "product_amount";

    public static final String MAKE_TABLE = "CREATE TABLE "+TABLE_NAME+" (" +
            C_PRODUCT+" INTEGER, " +
            C_PACKAGE+" INTEGER, " +
            C_PRODUCT_AMOUNT+" REAL, " +
            "FOREIGN KEY ("+C_PACKAGE+") REFERENCES "+
            PackageContract.TABLE_NAME+ " ("+PackageContract.C_ID+"), " +
            "FOREIGN KEY ("+C_PRODUCT+") REFERENCES "+
            ProductContract.TABLE_NAME+ " ("+ProductContract.C_ID+"))";
}
