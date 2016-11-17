package com.go.kchin.database;


/**
 * Created by vicente on 6/11/16.
 */
public class RecipeContract {

    private RecipeContract(){}

    public static final String TABLE_NAME = "material_in_product";
    public static final String C_MATERIAL_ID = "material_id";
    public static final String C_PRODUCT_ID = "product_id";
    public static final String C_QUANTITY = "quantity";

    public static final String MAKE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " ("+
                    C_MATERIAL_ID+" INTEGER, "+
                    C_PRODUCT_ID+" INTEGER, "+
                    C_QUANTITY+" REAL," +
                    "FOREIGN KEY ("+C_MATERIAL_ID+") REFERENCES "+
                    MaterialContract.TABLE_NAME+ " ("+MaterialContract.C_ID+"), " +
                    "FOREIGN KEY ("+C_PRODUCT_ID+") REFERENCES "+
                    ProductContract.TABLE_NAME+ " ("+ProductContract.C_ID+"))";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME+"; ";

}
