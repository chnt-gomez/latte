package com.go.kachin.database;

/**
 * Created by vicente on 6/11/16.
 */
public class MaterialContract {

    private MaterialContract(){}

    public static final String TABLE_NAME = "materials";
    public static final String C_NAME = "material_name";
    public static final String C_ID = "material_id";
    public static final String C_UNIT = "material_unit";
    public static final String C_AMOUNT = "material_amount";
    public static final String C_COST = "material_cost";
    public static final String C_STATUS = "material_status";

    public static final String MAKE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " ("+C_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    C_NAME+" TEXT, " +
                    C_UNIT+" TEXT, " +
                    C_AMOUNT+" REAL, " +
                    C_COST+" REAL,"+
                    C_STATUS+" INTEGER"+
                    "); ";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME+"; ";

}
