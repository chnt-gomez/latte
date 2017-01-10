package com.go.kchin.util.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.go.kchin.R;
import com.go.kchin.interfaces.RequiredDialogOps;
import com.go.kchin.model.database.Department;
import com.go.kchin.model.database.Product;
import com.go.kchin.util.dialog.number.Number;

import org.w3c.dom.Text;

/**
 * Created by MAV1GA on 09/01/2017.
 */

public class Dialogs {

    private static ProgressDialog progressInstance;
    private static Dialog instance;

    /**
     * Builds a generic 'Loading' dialog
     * @param context Activity reference
     * @param message Message reference
     */
    public static Dialog buildLoadingDialog(Context context, @Nullable String message){
        progressInstance = new ProgressDialog(context);
        progressInstance.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        if (message != null){
            progressInstance.setMessage(message);
        }else{
            progressInstance.setMessage("Loading...");
        }
        progressInstance.setIndeterminate(true);
        progressInstance.setCanceledOnTouchOutside(false);
        return progressInstance;
    }


    /**
     * Builds a dialog to create a new Product.
     * @param context The Activity reference
     * @param message A message reference
     */
    public static Dialog newProductDialog(Context context, String message,
                                          final RequiredDialogOps.RequiredNewProductOps callback){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        final View dialogView = inflater.inflate(R.layout.dialog_new_product, null);
        builder.setView(dialogView).
                setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Product product = new Product();
                        EditText productName = (EditText)dialogView.findViewById(R.id.edt_product_name);
                        EditText productSellPrice = (EditText)dialogView.findViewById(R.id.edt_product_sale_price);
                        Spinner productMeasure = (Spinner)dialogView.findViewById(R.id.spn_product_unit);
                        product.productName = productName.getText().toString();
                        product.productSellPrice = Number.stringToFloat(productSellPrice.getText().toString());
                        product.productMeasureUnit = productMeasure.getSelectedItem().toString();
                        callback.onNewProduct(product);
                    }
                });
        instance = builder.create();
        return instance;
    }

    /**
     * Builds a dialog to create a new Department
     * @param context Activity reference
     * @param message Message reference
     */
    public static Dialog newDepartmentDialog(Context context, String message,
                                           final RequiredDialogOps.RequiredNewDepartmentOps callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        final View dialogView = inflater.inflate(R.layout.dialog_new_department , null);
        builder.setView(dialogView).
                setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Department department = new Department();
                        EditText edtDepartmentName = (EditText)dialogView.findViewById(R.id.edt_department_name);
                        department.departmentName = edtDepartmentName.getText().toString();
                        callback.onNewDepartment(department);
                    }
                });
        instance = builder.create();
        return instance;
    }

    /**
     * Dismisses any Dialog
     */
    public static void dismiss(){
        if (instance != null){
            instance.dismiss();
            instance = null;
        }
        if (progressInstance != null){
            progressInstance.dismiss();
            progressInstance = null;
        }
    }


}
