package com.go.kchin.util.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.go.kchin.R;

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
     * @return
     */
    public static Dialog newProductDialog(Context context, String message,
              @Nullable Dialog.OnClickListener positiveAction,
              @Nullable Dialog.OnClickListener negativeAction){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        builder.setView(inflater.inflate(R.layout.dialog_new_product, null)).
                setPositiveButton(R.string.ok, positiveAction);
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
