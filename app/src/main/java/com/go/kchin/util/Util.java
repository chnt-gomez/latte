package com.go.kchin.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.go.kchin.R;
import com.go.kchin.adapters.DepartmentListAdapter;
import com.go.kchin.adapters.MaterialListAdapter;
import com.go.kchin.models.Department;
import com.go.kchin.models.Material;
import com.go.kchin.models.Operation;
import com.go.kchin.models.Product;

import java.util.List;

/**
 * Created by vicente on 7/11/16.
 */
public class Util {

    public static Dialog newMaterialDialog(String dialogTitle, String dialogMessage, Context context,
                                             final LayoutInflater inflater,final MaterialDialogEventListener callback){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final View view = inflater.inflate(R.layout.dialog_new_material, null);
        builder.setView(view)
                .setTitle(dialogTitle)
                .setMessage(dialogMessage)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String materialName = ((EditText)view.findViewById(R.id.edt_product_name))
                                .getText().toString();
                        String materialUnit = ((Spinner)view.findViewById(R.id.spn_material_unit))
                                .getSelectedItem().toString();
                        String materialCost = ((EditText)view.findViewById(R.id.edt_material_cost))
                                .getText().toString();
                        callback.returnMaterial(new Material(materialName, materialUnit, toFloat(materialCost)));
                    }
                })
                .setNeutralButton("Add and see", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String materialName = ((EditText)view.findViewById(R.id.edt_product_name))
                                .getText().toString();
                        String materialUnit = ((Spinner)view.findViewById(R.id.spn_material_unit))
                                .getSelectedItem().toString();
                        String materialCost = ((EditText)view.findViewById(R.id.edt_material_cost))
                                .getText().toString();
                        Operation operation = callback.returnMaterial(new Material(materialName, materialUnit, toFloat(materialCost)));
                        callback.moveToMaterial(operation.getInsertionId());

                    }
                });
        return builder.create();
    }

    public static Dialog editAmountDialog(String dialogTitle, String dialogMessage, Context context,
                                   final LayoutInflater inflater, final UtilDialogEventListener callback){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final View view = inflater.inflate(R.layout.dialog_float_amount, null);
        builder.setView(view)
                .setTitle(dialogTitle)
                .setMessage(dialogMessage)
                .setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        float newAmount = Util.toFloat(((EditText)view.findViewById(R.id.edt_amount))
                                .getText().toString());
                        callback.returnFloat(newAmount);
                    }
                });
        return builder.create();

    }

    public static Dialog newProductSalePriceDialog (String dialogTitle, float oldPrice, Context context,
                                                    final LayoutInflater inflater,final UtilDialogEventListener callback){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final View view = inflater.inflate(R.layout.dialog_new_product_price, null);
        ((EditText)view.findViewById(R.id.edt_product_sale_price)).setHint("Old price: $ "+Util.fromFloat(oldPrice));
        builder.setView(view)
                .setTitle(dialogTitle)
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        float newSalePrice = Util.toFloat(((EditText)view.findViewById(R.id.edt_product_sale_price))
                                .getText().toString());
                        callback.returnFloat(newSalePrice);
                    }
                });
        return builder.create();
    }

    public static Dialog newProductDialog(String dialogTitle, String dialogMessage, Context context,
                                          final LayoutInflater inflater,final ProductDialogEventListener callback){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final View view = inflater.inflate(R.layout.dialog_new_product, null);
        builder.setView(view)
                .setTitle(dialogTitle)
                .setMessage(dialogMessage)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String productName = ((EditText)view.findViewById(R.id.edt_product_name))
                                .getText().toString();
                        String productUnit = ((EditText)view.findViewById(R.id.edt_product_unit))
                                .getText().toString();
                        String productPrice = ((EditText)view.findViewById(R.id.edt_product_sale_price))
                                .getText().toString();
                        Product product = new Product();
                        product.setProductUnit(productUnit);
                        product.setProductName(productName);
                        product.setProductSalePrice(Util.toFloat(productPrice));
                        callback.returnProduct(product);

                    }
                })
                .setNeutralButton("Add and see", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String productName = ((EditText)view.findViewById(R.id.edt_product_name))
                                .getText().toString();
                        String productUnit = ((EditText)view.findViewById(R.id.edt_product_unit))
                                .getText().toString();
                        String productPrice = ((EditText)view.findViewById(R.id.edt_product_sale_price))
                                .getText().toString();
                        Product product = new Product();
                        product.setProductUnit(productUnit);
                        product.setProductName(productName);
                        product.setProductSalePrice(Util.toFloat(productPrice));
                        long id = callback.returnProduct(product).getInsertionId();
                        callback.moveToProduct(id);

                    }
                });
        return builder.create();
    }

    public static Dialog newBuyItemDialog(String dialogTitle, String dialogMessage, Context context,
                                          final LayoutInflater inflater, final UtilDialogEventListener callback){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final View view = inflater.inflate(R.layout.dialog_buy_item, null);
        builder.setView(view)
                .setTitle(dialogTitle)
                .setMessage(dialogMessage)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String materialAmount = ((EditText)view.findViewById(R.id.edt_item_amount))
                                .getText().toString();
                        float amount = Util.toFloat(materialAmount);
                        callback.returnFloat(amount);
                    }
                });
        return builder.create();
    }

    public static Dialog newChooseDepartmentDialog(String dialogTitle, String dialogMessage, Context context,
                                                   final LayoutInflater inflater, final DepartmentDialogEventListener callback,
                                                   final List<Department> departmentList){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final Dialog dialog;
        final View view = inflater.inflate(R.layout.dialog_choose_department, null);
        DepartmentListAdapter adapter = new DepartmentListAdapter(context, R.layout.row_despartment_item, departmentList);
        ListView lv = (ListView)view.findViewById(R.id.lv_department_list_view);
        lv.setAdapter(adapter);

        builder.setView(view)
                .setTitle(dialogTitle)
                .setMessage(dialogMessage);
        dialog = builder.create();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                callback.returnDepartment(departmentList.get(position));
                dialog.dismiss();
            }
        });
        return dialog;

    }

    public static Dialog newChooseMaterialDialog(String dialogTitle, String dialogMessage, Context context,
                                                 final LayoutInflater inflater, final Util.UtilDialogEventListener callback,
                                                 final List<Material> materials){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final Dialog dialog;
        final View view = inflater.inflate(R.layout.dialog_choose_department, null);
        MaterialListAdapter adapter = new MaterialListAdapter(context, R.layout.row_despartment_item, materials);
        ListView lv = (ListView)view.findViewById(R.id.lv_department_list_view);
        lv.setAdapter(adapter);

        builder.setView(view)
                .setTitle(dialogTitle)
                .setMessage(dialogMessage);
        dialog = builder.create();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                callback.returnLong(materials.get(position).getId());
                dialog.dismiss();
            }
        });
        return dialog;
    }

    public static Dialog newDepartmentDialog(String dialogTitle, String dialogMessage, Context context,
                                                 final LayoutInflater inflater, final DepartmentDialogEventListener callback){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final View view = inflater.inflate (R.layout.dialog_new_department, null);
        builder.setView(view)
                .setTitle(dialogTitle)
                .setMessage(dialogMessage)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Department department = new Department();
                        String departmentName = ((EditText)view.findViewById(R.id.edt_department_name))
                                .getText().toString();
                        department.setDepartmentName(departmentName);
                        callback.returnDepartment(department);
                    }
                });
        return builder.create();
    }

    public static float toFloat(String s){
        try {
            return Float.valueOf(s);
        }
        catch (Exception e) {
            return 0.0f;
        }
    }

    public static String fromFloat(float f){
        try{
            return String.valueOf(f);
        }catch(Exception e){
            return "err";
        }
    }

    public static int getSelectionIndexFromString(String productUnit) {
        if(productUnit.equals("Piece"))
            return 0;
        if(productUnit.equals("Kg"))
            return 1;
        if(productUnit.equals("Lt"))
            return 2;
        if(productUnit.equals("Lb"))
            return 3;
        if(productUnit.equals("Pound"))
            return 4;
        if(productUnit.equals("Gram"))
            return 5;
        if(productUnit.equals("Oz"))
            return 6;
        return 0;
    }

    public static Dialog showNewPackageDialog(String dialogTitle, String dialogMessage, Context context,
                                              final PackageDialogEventListener callback, final LayoutInflater
                                              inflater) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            final View view = inflater.inflate (R.layout.dialog_new_package, null);
            builder.setView(view)
                    .setTitle(dialogTitle)
                    .setMessage(dialogMessage)
                    .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            com.go.kchin.models.Package aPackage = new com.go.kchin.models.Package();
                            String packageName = ((EditText)view.findViewById(R.id.edt_package_name))
                                    .getText().toString();
                            aPackage.setName(packageName);
                            callback.returnPackage(aPackage);
                        }
                    });
            return builder.create();
    }

    public interface MaterialDialogEventListener {
        Operation returnMaterial(Material material);
        void moveToMaterial(long id);
    }

    public interface UtilDialogEventListener{
        void returnFloat(float arg);
        void returnString(String arg);
        void returnLong(long arg);
    }

    public interface ProductDialogEventListener{
        Operation returnProduct(Product product);
        void moveToProduct(long productId);
    }

    public interface DepartmentDialogEventListener{
        Operation returnDepartment(Department department);
        void editDepartment(long id, Department department);
    }

    public interface PackageDialogEventListener{
        Operation returnPackage(com.go.kchin.models.Package aPackage);
    }

    public static String toRegex(String arg){
        return arg.replace(" ", "%");
    }

}
