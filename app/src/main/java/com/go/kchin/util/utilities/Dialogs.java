package com.go.kchin.util.utilities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.go.kchin.R;
import com.go.kchin.adapters.SaleAdapter;
import com.go.kchin.interfaces.RequiredDialogOps;
import com.go.kchin.model.SimplePurchase;
import com.go.kchin.model.database.Department;
import com.go.kchin.model.database.Material;
import com.go.kchin.model.database.Product;
import com.go.kchin.model.database.Sale;

import java.util.List;

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
     * @param title A title reference
     */
    public static Dialog newProductDialog(Context context, String title,
                                          final RequiredDialogOps.RequiredNewProductOps callback){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
        LayoutInflater inflater = LayoutInflater.from(context);
        final View dialogView = inflater.inflate(R.layout.dialog_new_product, null);
        final EditText productName = (EditText)dialogView.findViewById(R.id.edt_product_name);
        final EditText productSellPrice = (EditText)dialogView.findViewById(R.id.edt_product_sale_price);
        builder.setView(dialogView).
                setTitle(title).
                setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Product product = new Product();
                        product.productName = productName.getText().toString();
                        product.productSellPrice = NFormatter.stringToFloat(productSellPrice.getText().toString());
                        callback.onNewProduct(product);
                    }
                });
        instance = builder.create();
        return instance;
    }

    /**
     * Builds a dialog to create a new Department
     * @param context Activity reference
     * @param title A title reference
     */
    public static Dialog newDepartmentDialog(Context context, String title,
                                           final RequiredDialogOps.RequiredNewDepartmentOps callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
        LayoutInflater inflater = LayoutInflater.from(context);
        final View dialogView = inflater.inflate(R.layout.dialog_new_department , null);
        builder.setView(dialogView).
                setTitle(title).
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

    public static Dialog newMaterialDialog(Context context, String title,
                                           final RequiredDialogOps.RequiredNewMaterialOps callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
        LayoutInflater inflater = LayoutInflater.from(context);
        final View dialogView = inflater.inflate(R.layout.dialog_new_material , null);
        final EditText edtMaterialName = (EditText)dialogView.findViewById(R.id.edt_material_name);
        final Spinner spnMaterialMeasure = (Spinner)dialogView.findViewById(R.id.spn_material_unit);
        spnMaterialMeasure.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item,
                MeasurePicker.getEntries(context.getResources())));
        builder.setView(dialogView).
                setTitle(title).
                setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Material material = new Material();
                        material.materialName = edtMaterialName.getText().toString();
                        material.materialMeasure = spnMaterialMeasure.getSelectedItemPosition();
                        callback.onNewMaterial(material);
                    }
                });
        instance = builder.create();
        return instance;
    }

    public static Dialog newApplySaleDialog(final Context context, String title, final List<Sale> sales,
                                          final RequiredDialogOps.RequiredNewSaleOps callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
        LayoutInflater inflater = LayoutInflater.from(context);
        final View dialogView = inflater.inflate(R.layout.dialog_confirm_sale , null);
        final ListView lv = (ListView)dialogView.findViewById(R.id.lv_sale);
        final TextView edtTotal = (TextView)dialogView.findViewById(R.id.txt_sale_total);
        final SaleAdapter adapter = new SaleAdapter(context, R.layout.row_sell_item, sales);
        lv.setAdapter(adapter);
        float total = 0.0f;
        for (Sale s : sales){
            total += s.saleTotal;
        }
        edtTotal.setText(NFormatter.floatToStringAsPrice(total, false));
        builder.setView(dialogView).
                setTitle(title).
                setPositiveButton(R.string.apply, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.onNewSale();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });
        instance = builder.create();
        return instance;
    }

    public static Dialog newFloatDialog(final Context context, String title, String message,
                                        final RequiredDialogOps.NewFloatOps callback){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
        LayoutInflater inflater = LayoutInflater.from(context);
        if (title != null)
            builder.setTitle(title);
        if(message != null)
            builder.setMessage(message);
        final View dialogView = inflater.inflate(R.layout.dialog_float_amount , null);
        builder.setView(dialogView);
        builder.setPositiveButton(context.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText edtAmount = (EditText)dialogView.findViewById(R.id.edt_amount);
                float amount = 0.0f;
                try{amount = Float.valueOf(edtAmount.getText().toString());}
                catch(NumberFormatException e){
                    Log.w(getClass().getSimpleName(), "Invalid format");
                }
                callback.onNewFloat(amount);
            }
        });
        instance = builder.create();
        return instance;
    }

    public static Dialog newFloatMoneyDialog(final Context context, String title, String message,
                                        final RequiredDialogOps.NewFloatOps callback){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
        LayoutInflater inflater = LayoutInflater.from(context);
        if (title != null)
            builder.setTitle(title);
        if(message != null)
            builder.setMessage(message);
        final View dialogView = inflater.inflate(R.layout.dialog_float_amount , null);
        builder.setView(dialogView);
        builder.setPositiveButton(context.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText edtAmount = (EditText)dialogView.findViewById(R.id.edt_amount);
                edtAmount.setHint("$0.00");
                float amount = 0.0f;
                try{amount = Float.valueOf(edtAmount.getText().toString());}
                catch(NumberFormatException e){
                    Log.w(getClass().getSimpleName(), "Invalid format");
                }
                callback.onNewFloat(amount);
            }
        });
        instance = builder.create();
        return instance;
    }

    public static Dialog newPasswordDialog(final Context context, String title, String message,
                                           final RequiredDialogOps.RequiredPasswordOps callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
        LayoutInflater inflater = LayoutInflater.from(context);
        if(title != null)
            builder.setTitle(title);
        if(message != null){
            builder.setMessage(message);
        }
        final View dialogView = inflater.inflate(R.layout.dialog_password, null);
        builder.setView(dialogView);
        builder.setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText edtPassword = (EditText)dialogView.findViewById(R.id.edt_password);
                String password = edtPassword.getText().toString();
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                    if (prefs != null && prefs.contains("password")) {
                        if (password.equals(prefs.getString("password", null))) {
                            callback.isAuthorized(true);
                        } else {
                            callback.isAuthorized(false);
                        }

                    }
                    //TODO: what happens it there is not a password set?!
                }

        });
        builder.setNegativeButton(context.getString(R.string.forgot_password), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callback.recoverPassword();
            }
        });
        instance = builder.create();
        return instance;
    }

    public static Dialog newPurchaseDialog(final Context context, String title, String message,
                                           final RequiredDialogOps.RequiredNewPurchaseOps callback){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
        LayoutInflater inflater = LayoutInflater.from(context);
        if(title != null)
            builder.setTitle(title);
        if(message != null){
            builder.setMessage(message);
        }
        final View dialogView = inflater.inflate(R.layout.dialog_buy_item, null);
        builder.setView(dialogView);
        builder.setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText edtItems = (EditText)dialogView.findViewById(R.id.edt_item_amount);
                EditText edtCost = (EditText)dialogView.findViewById(R.id.edt_amount_paid);
                SimplePurchase purchase = new SimplePurchase();
                purchase.setPurchasedItems(NFormatter.stringToFloat(edtItems.getText().toString()));
                purchase.setPurchaseAmount(NFormatter.stringToFloat(edtCost.getText().toString()));
                callback.onNewPurchase(purchase);

            }

        });
        instance = builder.create();
        return instance;
    }

    public static Dialog newStringDialog(final Context context, String title, String message,
                                         final RequiredDialogOps.RequiredNewStringDialog callback,
                                         @Nullable final String hint) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
        LayoutInflater inflater = LayoutInflater.from(context);
        if(title != null)
            builder.setTitle(title);
        if(message != null){
            builder.setMessage(message);
        }
        final View dialogView = inflater.inflate(R.layout.dialog_new_string, null);
        final EditText edtItems = (EditText)dialogView.findViewById(R.id.edt_new_string);
        if(hint != null)
            edtItems.setHint(hint);
        builder.setView(dialogView);
        builder.setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                callback.onNewString(edtItems.getText().toString());

            }

        });
        instance = builder.create();
        return instance;
    }
}
