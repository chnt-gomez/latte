package com.go.kchin.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

import com.go.kchin.models.Department;
import com.go.kchin.models.Material;
import com.go.kchin.models.Operation;
import com.go.kchin.models.Package;
import com.go.kchin.models.Product;
import com.go.kchin.models.Sale;
import com.go.kchin.util.Util;

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
        sqLiteDatabase.execSQL(RecipeContract.MAKE_TABLE);
        sqLiteDatabase.execSQL(PackageContract.MAKE_TABLE);
        sqLiteDatabase.execSQL(ProductInPackageContract.MAKE_TABLE);
        sqLiteDatabase.execSQL(SaleContract.MAKE_TABLE);
        sqLiteDatabase.execSQL(ProductInSaleContract.MAKE_TABLE);
        sqLiteDatabase.execSQL(PackageInSaleContract.MAKE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        //super.onUpgrade(sqLiteDatabase, oldVersion, newVersion);
        sqLiteDatabase.execSQL(SaleContract.MAKE_TABLE);
        sqLiteDatabase.execSQL(ProductInSaleContract.MAKE_TABLE);
        sqLiteDatabase.execSQL(PackageInSaleContract.MAKE_TABLE);

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

    public Operation addMaterial(Material material) {

        final Operation operation = new Operation();

        ContentValues values = new ContentValues();
        values.put(MaterialContract.C_NAME, material.getMaterialName());
        values.put(MaterialContract.C_UNIT, material.getMaterialUnit());
        values.put(MaterialContract.C_COST,material.getMaterialCost());
        values.put(MaterialContract.C_AMOUNT, 0.0f);
        values.put(MaterialContract.C_STATUS, 1);

        operation.setInsertionId(insert(MaterialContract.TABLE_NAME, values));

        operation.setMaterials(getMaterials());

        return operation;
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
                materials.add(Material.fromCursor(c, false));
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

    public Operation addProduct(Product product){
        ContentValues values = new ContentValues();
        final Operation operation = new Operation();
        values.put(ProductContract.C_NAME, product.getProductName());
        values.put(ProductContract.C_AMOUNT, product.getProductAmount());
        values.put(ProductContract.C_COST, product.getProductPurchasePrice());
        values.put(ProductContract.C_DEPARTMENT, product.getProductDepartment());
        values.put(ProductContract.C_PRICE, product.getProductSalePrice());
        values.put(ProductContract.C_SOLD, 0.0f);
        values.put(ProductContract.C_UNIT, product.getProductUnit());
        values.put(ProductContract.C_STATUS, 1);

        operation.setInsertionId(insert(ProductContract.TABLE_NAME, values));
        operation.setProducts(getProducts());

        return operation;
    }

    public List<Product> getProducts(String query){
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

        final String regex = Util.toRegex(query);

        String selection = ProductContract.C_STATUS + " = ? AND "+ProductContract.C_NAME + " LIKE ?";
        String selectionArgs[] = {"1", "%"+regex+"%"};

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
        values.put(ProductContract.C_PRICE, product.getProductSalePrice());
        values.put(ProductContract.C_DEPARTMENT, product.getProductDepartment());
        values.put(ProductContract.C_AMOUNT, product.getProductAmount());
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

    public List<Department> getDepartments (){
        ArrayList<Department> departments = new ArrayList<>();

        String newQuery =
                "SELECT "+DepartmentContract.C_ID +", "+
                        DepartmentContract.C_NAME +", "+
                        "(SELECT COUNT(*) FROM "+ProductContract.TABLE_NAME+
                        " WHERE "+DepartmentContract.C_ID+"="+ProductContract.TABLE_NAME+"."+
                        ProductContract.C_DEPARTMENT+") AS "+DepartmentContract.C_PRODUCTS+" " +
                        "FROM "+DepartmentContract.TABLE_NAME;

        Cursor c = getReadableDatabase().rawQuery(newQuery, null);
        if (c.getCount() > 0){
            c.moveToFirst();
            do{
                departments.add(Department.fromCursor(c));
                c.moveToNext();
            }while(!c.isAfterLast());
        }
        c.close();
        return departments;
    }

    public Department getDepartment (long departmentId){
        String rawQuery =
                "SELECT "+DepartmentContract.C_ID +", "+
                        DepartmentContract.C_NAME +", "+
                        "(SELECT COUNT(*) FROM "+ProductContract.TABLE_NAME+
                        " WHERE "+DepartmentContract.C_ID+"="+ProductContract.TABLE_NAME+"."+
                        ProductContract.C_DEPARTMENT+") AS "+DepartmentContract.C_PRODUCTS+" " +
                        "FROM "+DepartmentContract.TABLE_NAME+" WHERE "+DepartmentContract.C_ID+"="+
                        departmentId;
        Cursor c = getReadableDatabase().rawQuery(rawQuery, null);
        if (c.getCount() > 0){
            c.moveToFirst();
            return Department.fromCursor(c);
        }
        return new Department();
    }

    public Operation addDepartment(Department department) {
        ContentValues values = new ContentValues();
        final Operation operation = new Operation();
        values.put(DepartmentContract.C_NAME, department.getDepartmentName());
        operation.setInsertionId(insert(DepartmentContract.TABLE_NAME, values));
        operation.setDepartments(getDepartments());
        return operation;
    }

    public long updateDepartment(long id, Department department) {
        ContentValues values = new ContentValues();
        values.put(DepartmentContract.C_NAME, department.getDepartmentName());

        String selection = DepartmentContract.C_ID + " = ?";
        String [] selectionArgs = {String.valueOf(id)};

        return update(DepartmentContract.TABLE_NAME, values, selection, selectionArgs);
    }

    public List<Material> getRecipe(long productId){

        List<Material> items = new ArrayList<>();

        String projection[] = {
                MaterialContract.TABLE_NAME+"."+MaterialContract.C_ID,
                MaterialContract.TABLE_NAME+"."+MaterialContract.C_NAME,
                MaterialContract.TABLE_NAME+"."+MaterialContract.C_UNIT,
                MaterialContract.TABLE_NAME+"."+MaterialContract.C_COST,
                RecipeContract.TABLE_NAME+"."+RecipeContract.C_QUANTITY
        };

        String selection = MaterialContract.TABLE_NAME+"."+MaterialContract.C_ID +"="+
                RecipeContract.TABLE_NAME+"."+RecipeContract.C_MATERIAL_ID+" AND "+
                RecipeContract.TABLE_NAME+"."+RecipeContract.C_PRODUCT_ID+"= ?";

        String selectionArgs[] = {String.valueOf(productId)};

        String tables = MaterialContract.TABLE_NAME+", "+RecipeContract.TABLE_NAME;
        Cursor c = query(tables, projection, selection, selectionArgs);

        if (c.getCount() > 0){
            c.moveToFirst();
            do{
                items.add(Material.fromCursor(c, true));
                c.moveToNext();
            }while(!c.isAfterLast());
        }
        c.close();
        return items;
    }

    public List<Product> getProductsFromMaterial (long materialId){

        String[] projection = {
                ProductContract.TABLE_NAME+"."+ProductContract.C_ID,
                ProductContract.TABLE_NAME+"."+ProductContract.C_DEPARTMENT,
                ProductContract.TABLE_NAME+"."+ProductContract.C_AMOUNT,
                ProductContract.TABLE_NAME+"."+ProductContract.C_COST,
                ProductContract.TABLE_NAME+"."+ProductContract.C_NAME,
                ProductContract.TABLE_NAME+"."+ProductContract.C_PRICE,
                ProductContract.TABLE_NAME+"."+ProductContract.C_SOLD,
                ProductContract.TABLE_NAME+"."+ProductContract.C_UNIT
        };

        String selection = ProductContract.TABLE_NAME+"."+ProductContract.C_ID+"="+
                RecipeContract.TABLE_NAME+"."+RecipeContract.C_PRODUCT_ID+" AND "+
                RecipeContract.TABLE_NAME+"."+RecipeContract.C_MATERIAL_ID+"="+
                MaterialContract.TABLE_NAME+"."+MaterialContract.C_ID+" AND "+
                MaterialContract.TABLE_NAME+"."+MaterialContract.C_ID+"=?";

        String selectionArgs[] = {
            String.valueOf(materialId)
        };

        String tables = ProductContract.TABLE_NAME+", "+RecipeContract.TABLE_NAME+","+MaterialContract.TABLE_NAME;

        List<Product> products = new ArrayList<>();

        Cursor c = query(tables,  projection, selection, selectionArgs);

        if (c.getCount() > 0){
            c.moveToFirst();
            do{
                products.add(Product.fromCursor(c));
                c.moveToNext();
            }while(!c.isAfterLast());
            c.close();
        }
        return products;
    }

    public List<Material> updateRecipe(long materialId, long productId) {
        ContentValues values = new ContentValues();
        values.put(RecipeContract.C_MATERIAL_ID, materialId);
        values.put(RecipeContract.C_PRODUCT_ID, productId);
        values.put(RecipeContract.C_QUANTITY, 1.0f);

        insert(RecipeContract.TABLE_NAME, values);
        return getRecipe(productId);
    }

    public List<Material> updateRecipe(long materialId, long productId, float amount) {
        ContentValues values = new ContentValues();
        values.put(RecipeContract.C_QUANTITY, amount);

        String selection = RecipeContract.C_MATERIAL_ID + " = ? AND "+
                RecipeContract.C_PRODUCT_ID + " = ?";
        String selectionArgs[] = {
                String.valueOf(materialId),
                String.valueOf(productId)
        };

        update(RecipeContract.TABLE_NAME, values, selection, selectionArgs);

        return getRecipe(productId);
    }

    public List<Material> getMaterials(String query) {
        List <Material> materials = new ArrayList<>();
        String regex = Util.toRegex(query);
        String projection[] = {
                MaterialContract.C_ID,
                MaterialContract.C_NAME,
                MaterialContract.C_UNIT,
                MaterialContract.C_COST,
                MaterialContract.C_AMOUNT
        };

        String selection = MaterialContract.C_STATUS + " = ? AND "+
                MaterialContract.C_NAME+" LIKE ?";
        String selectionArgs[] = {"1", "%"+regex+"%"};

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
                materials.add(Material.fromCursor(c, false));
                c.moveToNext();
            } while(!c.isAfterLast());
            c.close();
            return materials;
        }
        return new ArrayList<>();
    }

    public List<Department> getDepartments(String query) {
        ArrayList<Department> departments = new ArrayList<>();

        String newQuery =
                "SELECT "+DepartmentContract.C_ID +", "+
                        DepartmentContract.C_NAME +", "+
                        "(SELECT COUNT(*) FROM "+ProductContract.TABLE_NAME+
                        " WHERE "+DepartmentContract.C_ID+"="+ProductContract.TABLE_NAME+"."+
                        ProductContract.C_DEPARTMENT+ ") AS "+DepartmentContract.C_PRODUCTS+
                        " FROM "+DepartmentContract.TABLE_NAME+" WHERE " + DepartmentContract.TABLE_NAME+"."+
                        DepartmentContract.C_NAME+ " LIKE '%"+Util.toRegex(query)+"%'";

        Log.d("Database: ", newQuery);

        Cursor c = getReadableDatabase().rawQuery(newQuery, null);
        if (c.getCount() > 0){
            c.moveToFirst();
            do{
                departments.add(Department.fromCursor(c));
                c.moveToNext();
            }while(!c.isAfterLast());
        }
        c.close();
        return departments;
    }

    public List<Package> getPackages(){
        List<Package> packages = new ArrayList<>();
        String [] projection = {
                PackageContract.C_ID,
                PackageContract.C_DEPARTMENT,
                PackageContract.C_SOLD,
                PackageContract.C_NAME,
                PackageContract.C_COST,
        };
        String selection = PackageContract.C_STATUS +" = ?";
        String selectionArgs[] = {"1"};

        Cursor c = query(PackageContract.TABLE_NAME, projection, selection, selectionArgs);

        if (c.getCount() > 0){
            c.moveToFirst();
            do {
                packages.add(Package.fromCursor(c));
                c.moveToNext();
            }while(!c.isAfterLast());
        }
        return packages;
    }

    public List<Product> getProductsInPackage(long packageId){

        String[] projection = {
                ProductContract.TABLE_NAME+"."+ProductContract.C_ID,
                ProductContract.TABLE_NAME+"."+ProductContract.C_DEPARTMENT,
                ProductContract.TABLE_NAME+"."+ProductContract.C_AMOUNT,
                ProductContract.TABLE_NAME+"."+ProductContract.C_COST,
                ProductContract.TABLE_NAME+"."+ProductContract.C_NAME,
                ProductContract.TABLE_NAME+"."+ProductContract.C_PRICE,
                ProductContract.TABLE_NAME+"."+ProductContract.C_SOLD,
                ProductContract.TABLE_NAME+"."+ProductContract.C_UNIT
        };

        String selection = ProductContract.TABLE_NAME+"."+ProductContract.C_ID+" = " +
                ProductInPackageContract.TABLE_NAME+"."+ProductInPackageContract.C_PRODUCT+" AND "+
                PackageContract.TABLE_NAME+"."+PackageContract.C_ID+" = " +
                ProductInPackageContract.TABLE_NAME+"."+ProductInPackageContract.C_PACKAGE+" AND "+
                PackageContract.TABLE_NAME+"."+PackageContract.C_ID+" = ?";

        String selectionArgs[] = {String.valueOf(packageId)};

        String tables = ProductContract.TABLE_NAME+", "+PackageContract.TABLE_NAME+", "+
                ProductInPackageContract.TABLE_NAME;

        Cursor c = query(tables, projection, selection, selectionArgs);

        List<Product> products = new ArrayList<>();

        if (c.getCount() > 0){
            c.moveToFirst();
            do{
                products.add(Product.fromCursor(c));
                c.moveToNext();
            }while (!c.isAfterLast());
        }

        return products;
    }

    public Operation addPackage(Package arg){
        Operation operation = new Operation();
        ContentValues values = new ContentValues();
        values.put(PackageContract.C_NAME, arg.getName());
        values.put(PackageContract.C_STATUS, 1);

        operation.setInsertionId(insert(PackageContract.TABLE_NAME, values));
        operation.setPackages(getPackages());
        return operation;
    }

    public Package getPackage(long packageId) {
        String[] projection = {
                PackageContract.C_ID,
                PackageContract.C_DEPARTMENT,
                PackageContract.C_SOLD,
                PackageContract.C_NAME,
                PackageContract.C_COST,
        };

        String selection = PackageContract.C_ID+" = ?";

        String selectionArgs[] = {
                String.valueOf(packageId)
        };

        Cursor c = query(PackageContract.TABLE_NAME, projection, selection, selectionArgs);

        if (c.getCount() > 0) {
            c.moveToFirst();
            return Package.fromCursor(c);
        }
        c.close();
        return new Package();
    }

    public void updatePackage(long packageId, Package aPackage) {
        ContentValues values = new ContentValues();
        values.put(PackageContract.C_NAME, aPackage.getName());
        values.put(PackageContract.C_COST, aPackage.getPrice());
        String selection = PackageContract.C_ID + " = ?";
        String selectionArgs[] = { String.valueOf(packageId)};
        update(PackageContract.TABLE_NAME, values, selection, selectionArgs);
    }

    public long addProductToPackage(long packageId, long productId) {
        ContentValues values = new ContentValues();
        values.put(ProductInPackageContract.C_PACKAGE, packageId);
        values.put(ProductInPackageContract.C_PRODUCT, productId);
        return insert(ProductInPackageContract.TABLE_NAME, values);
    }



    public void undoTransaction (String tableName, long primaryId, @Nullable long secondaryId){
        if (tableName.equals(DepartmentContract.TABLE_NAME)){
            String whereClause = DepartmentContract.C_ID +" = ?";
            String[] whereArgs = {String.valueOf(primaryId)};
            delete(tableName,whereClause, whereArgs );
            return;
        }
        if (tableName.equals(PackageContract.TABLE_NAME)){
            String whereClause = PackageContract.C_ID +" = ?";
            String[] whereArgs = {String.valueOf(primaryId)};
            delete(tableName,whereClause, whereArgs );
            return;
        }
        if (tableName.equals(ProductContract.TABLE_NAME)){
            String whereClause = ProductContract.C_ID +" = ?";
            String[] whereArgs = {String.valueOf(primaryId)};
            delete(tableName,whereClause, whereArgs );
            return;
        }
        if (tableName.equals(ProductInPackageContract.TABLE_NAME)){
            String whereClause = ProductInPackageContract.C_PACKAGE +"= ? AND "+
                    ProductInPackageContract.C_PRODUCT+" = ?";
            String whereArgs[] = {String.valueOf(primaryId), String.valueOf(secondaryId)};
            delete(tableName, whereClause, whereArgs);
            return;
        }
    }

    private void delete(String tableName, String whereClause, String[] whereArgs){
        getWritableDatabase().delete(tableName, whereClause, whereArgs);
    }

    public void erase(String tableName, long primaryId, long secondaryId) {
        if (tableName.equals(DepartmentContract.TABLE_NAME)){
            String whereClause = DepartmentContract.C_ID +" = ?";
            String[] whereArgs = {String.valueOf(primaryId)};
            delete(tableName,whereClause, whereArgs );
            return;
        }
        if (tableName.equals(PackageContract.TABLE_NAME)){
            String whereClause = PackageContract.C_ID +" = ?";
            String[] whereArgs = {String.valueOf(primaryId)};
            delete(tableName,whereClause, whereArgs );
            return;
        }
        if (tableName.equals(ProductContract.TABLE_NAME)){
            String whereClause = ProductContract.C_ID +" = ?";
            String[] whereArgs = {String.valueOf(primaryId)};
            delete(tableName,whereClause, whereArgs );
            return;
        }
        if (tableName.equals(ProductInPackageContract.TABLE_NAME)){
            String whereClause = ProductInPackageContract.C_PACKAGE +"= ? AND "+
                    ProductInPackageContract.C_PRODUCT+" = ?";
            String whereArgs[] = {String.valueOf(primaryId), String.valueOf(secondaryId)};
            delete(tableName, whereClause, whereArgs);
            return;
        }
    }

    public long applySale(List<Sale> sale) {

        ContentValues values = new ContentValues();
        long date = Util.getCurrentDateInSeconds();
        values.put(SaleContract.C_TIME, date);
        values.put(SaleContract.C_VENDOR, "Default");
        float total = Sale.getTotalFromSale(sale);
        values.put(SaleContract.C_TOTAL, total);
        long insertion = insert(SaleContract.TABLE_NAME, values);

        applyPackageToSale(sale, insertion);
        applyProductToSale(sale, insertion);

        return insertion;

    }

    private void applyPackageToSale(List<Sale> sale, long saleId){

        for (Sale s : sale){
            if (s.isPackage()){
                ContentValues values = new ContentValues();
                values.put(PackageInSaleContract.C_SALE_ID, saleId);
                values.put(PackageInSaleContract.C_PACKAGE_AMOUNT, s.getAmmount());
                values.put(PackageInSaleContract.C_ID, s.getObjectId());
                insert(PackageInSaleContract.TABLE_NAME, values);
            }
        }
    }

    private void applyProductToSale(List<Sale> sale, long saleId){

        for (Sale s : sale){
            if (s.isProduct()){
                ContentValues values = new ContentValues();
                values.put(ProductInSaleContract.C_SALE_ID, saleId);
                values.put(ProductInSaleContract.C_PRODUCT_AMOUNT, s.getAmmount());
                values.put(ProductInSaleContract.C_ID, s.getObjectId());
                insert(ProductInSaleContract.TABLE_NAME, values);
            }
        }
    }
}
