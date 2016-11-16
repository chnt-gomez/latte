package com.go.kchin.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import com.go.kchin.R;
import com.go.kchin.models.Department;
import com.go.kchin.models.Material;
import com.go.kchin.models.Product;

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
                        String materialUnit = ((EditText)view.findViewById(R.id.edt_material_unit))
                                .getText().toString();
                        String materialCost = ((EditText)view.findViewById(R.id.edt_material_unit))
                                .getText().toString();
                        callback.returnMaterial(new Material(materialName, materialUnit, toFloat(materialCost)));
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
                });
        return builder.create();
    }

    public static Dialog newBuyMaterialDialog(String dialogTitle, String dialogMessage, Context context,
                                              final LayoutInflater inflater, final UtilDialogEventListener callback){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final View view = inflater.inflate(R.layout.dialog_buy_material, null);
        builder.setView(view)
                .setTitle(dialogTitle)
                .setMessage(dialogMessage)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String materialAmount = ((EditText)view.findViewById(R.id.edt_material_amount))
                                .getText().toString();
                        float amount = Util.toFloat(materialAmount);
                        callback.returnFloat(amount);
                    }
                });
        return builder.create();
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

    public interface MaterialDialogEventListener {
        void returnMaterial(Material material);
    }

    public interface UtilDialogEventListener{
        void returnFloat(float arg);
        void returnString(String arg);
    }

    public interface ProductDialogEventListener{
        void returnProduct(Product product);
    }

    public interface DepartmentDialogEventListener{
        void returnDepartment(Department department);
        void editDepartment(long id, Department department);
    }

}
