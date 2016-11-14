package com.go.kchin.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.go.kchin.models.Material;
import com.go.kchin.models.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vicente on 7/11/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "kchin.db";
    private static final int DATABASE_VERSION = 1;


    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(MaterialContract.MAKE_TABLE);
        sqLiteDatabase.execSQL(DepartmentContract.MAKE_TABLE);
        sqLiteDatabase.execSQL(ProductContract.MAKE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        //TODO: Set upgrade code
    }

    private long insert(String table, ContentValues values){
        return getWritableDatabase().insert(table, null, values);
    }

    private int update(String table, ContentValues values, String selection, String[] selectionArgs){
        return getWritableDatabase().update(
            table,
            values,
            selection,
            selectionArgs
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

    public List<Material> getMaterials() {
        List <Material> materials = new ArrayList<>();
        String projection[] = {
                MaterialContract.C_ID,
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
                long materialId = c.getLong(c.getColumnIndex(MaterialContract.C_ID));
                materials.add(new Material(materialName, materialUnit, materialCost, materialId, materialAmount));

                c.moveToNext();

            } while(!c.isAfterLast());
            c.close();
            return materials;
        }
        return new ArrayList<>();
    }

    public Material getMaterialFromId(long id) {
        Material material = new Material();
        String projection[] = {
                MaterialContract.C_ID,
                MaterialContract.C_NAME,
                MaterialContract.C_UNIT,
                MaterialContract.C_COST,
                MaterialContract.C_AMOUNT
        };

        String selection = MaterialContract.C_STATUS + " = ? AND " + MaterialContract.C_ID + " = ?";
        String selectionArgs[] = {"1", String.valueOf(id)};

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
            String materialName = c.getString(c.getColumnIndex(MaterialContract.C_NAME));
            String materialUnit = c.getString(c.getColumnIndex(MaterialContract.C_UNIT));
            float materialCost = c.getFloat(c.getColumnIndex(MaterialContract.C_COST));
            float materialAmount = c.getFloat(c.getColumnIndex(MaterialContract.C_AMOUNT));
            long materialId = c.getLong(c.getColumnIndex(MaterialContract.C_ID));

            material.setMaterialName(materialName);
            material.setMaterialUnit(materialUnit);
            material.setMaterialCost(materialCost);
            material.setMaterialAmount(materialAmount);
            material.setId(materialId);

            c.close();
            return material;
        }
        return null;
    }

    public int updateMaterial(long id, Material material) {
        ContentValues values = new ContentValues();
        values.put(MaterialContract.C_NAME, material.getMaterialName());
        values.put(MaterialContract.C_AMOUNT, material.getMaterialAmount());
        values.put(MaterialContract.C_UNIT, material.getMaterialUnit());

        String selection = MaterialContract.C_ID + " = ?";
        String [] selectionArgs = {String.valueOf(id)};

        return update(MaterialContract.TABLE_NAME, values, selection, selectionArgs);
    }

    public long addProduct(Product product){
        ContentValues values = new ContentValues();
        values.put(ProductContract.C_NAME, product.getProductName());
        values.put(ProductContract.C_AMOUNT, product.getProductAmount());
        values.put(ProductContract.C_COST, product.getProductPurchasePrice());
        values.put(ProductContract.C_DEPARTMENT, product.getProductDepartment());
        values.put(ProductContract.C_PRICE, product.getProductSalePrice());
        values.put(ProductContract.C_SOLD, 0.0f);
        values.put(ProductContract.C_UNIT, product.getProductUnit());
        values.put(ProductContract.C_STATUS, 1);

        return insert(ProductContract.TABLE_NAME, values);
    }

    public List<Product> getProducts(){
        List<Product> products = new ArrayList<>();
        String projection[] = {
                ProductContract.C_ID,
                ProductContract.C_DEPARTMENT,
                ProductContract.C_AMOUNT,
                ProductContract.C_COST,
                ProductContract.C_NAME,
                ProductContract.C_PRICE,
                ProductContract.C_SOLD,
                ProductContract.C_UNIT
        };

        String selection = ProductContract.C_STATUS + " = ?";
        String selectionArgs[] = {"1"};

        Cursor c = query(ProductContract.TABLE_NAME, projection, selection, selectionArgs);

        if (c.getCount() > 0){
            c.moveToFirst();
            do {
                products.add(Product.fromCursor(c));
                c.moveToNext();
            }while(!c.isAfterLast());
            c.close();
            return products;
        }
        return new ArrayList<>();
    }

    public int updateProduct(long productId, Product product){
        ContentValues values = new ContentValues();

        values.put(ProductContract.C_NAME, product.getProductName());
        values.put(ProductContract.C_UNIT, product.getProductUnit());

        String selection = ProductContract.C_ID + " = ?";
        String [] selectionArgs = {String.valueOf(productId)};

        return update(ProductContract.TABLE_NAME, values, selection, selectionArgs);
    }

    private Cursor query(String table, String[] projection, String selection, String[] selectionArgs){
        Cursor c = getReadableDatabase().query(
                table, projection, selection, selectionArgs, null, null, null
        );
        return c;
    }

    public Product getProduct(long id) {

        String[] projection = {
                ProductContract.C_ID,
                ProductContract.C_DEPARTMENT,
                ProductContract.C_AMOUNT,
                ProductContract.C_COST,
                ProductContract.C_NAME,
                ProductContract.C_PRICE,
                ProductContract.C_SOLD,
                ProductContract.C_UNIT
        };

        String selection = ProductContract.C_STATUS+" = ? AND "+ProductContract.C_ID+" = ?";
        String selectionArgs[] = {"1", String.valueOf(id)};

        Cursor c = query(ProductContract.TABLE_NAME, projection, selection, selectionArgs);
        if (c.getCount() > 0){
            c.moveToFirst();
            return Product.fromCursor(c);
        }
        return new Product();
    }
}
