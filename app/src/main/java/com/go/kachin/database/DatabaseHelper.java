package com.go.kachin.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.go.kachin.models.Material;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vicente on 7/11/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "kchin.db";
    private static final int DATABASE_VERSION = 1;
    private List<Material> materials;


    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                MaterialContract.MAKE_TABLE+
                ProductContract.MAKE_TABLE+
                ServiceContract.MAKE_TABLE+
                SubMaterialInProduct.MAKE_TABLE
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(
                SubMaterialInProduct.DROP_TABLE+
                MaterialContract.DROP_TABLE+
                ProductContract.DROP_TABLE+
                ServiceContract.DROP_TABLE
        );
    }

    public long addMaterial(Material material) {
        ContentValues values = new ContentValues();
        values.put(MaterialContract.C_NAME, material.getMaterialName());
        values.put(MaterialContract.C_UNIT, material.getMaterialUnit());
        values.put(MaterialContract.C_COST,material.getMaterialCost());
        values.put(MaterialContract.C_AMOUNT, 0.0f);
        values.put(MaterialContract.C_STATUS, 1);

        return insert(MaterialContract.TABLE_NAME, values);
    }

    private long insert(String table, ContentValues values){
        return getWritableDatabase().insert(table, null, values);
    }

    public List<Material> getMaterials() {
        List <Material> materials = new ArrayList<>();
        String projection[] = {
                MaterialContract.C_NAME,
                MaterialContract.C_UNIT,
                MaterialContract.C_COST,
                MaterialContract.C_AMOUNT
        };

        String selection = MaterialContract.C_STATUS + " = ?";
        String selectionArgs[] = {"1"};

        Cursor c = getReadableDatabase().query(
                MaterialContract.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        if(c.getCount() > 0) {
            c.moveToFirst();
            do  {
                String materialName = c.getString(c.getColumnIndex(MaterialContract.C_NAME));
                String materialUnit = c.getString(c.getColumnIndex(MaterialContract.C_UNIT));
                float materialCost = c.getFloat(c.getColumnIndex(MaterialContract.C_COST));
                float materialAmount = c.getFloat(c.getColumnIndex(MaterialContract.C_AMOUNT));
                materials.add(new Material(materialName, materialUnit, materialCost));
                c.moveToNext();
            } while(!c.isAfterLast());
            c.close();
            return materials;
        }
        return null;
    }
}
