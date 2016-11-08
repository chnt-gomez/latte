package com.go.kachin.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import com.go.kachin.R;

/**
 * Created by vicente on 7/11/16.
 */
public class Util {

    public static Dialog newMaterialDialog(String dialogTitle, String dialogMessage, Context context,
                                           LayoutInflater inflater, DialogInterface.OnClickListener positiveListener,
                                           DialogInterface.OnClickListener negativeListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(inflater.inflate(R.layout.dialog_new_material, null))
                .setTitle(dialogTitle)
                .setMessage(dialogMessage)
                .setPositiveButton("Add", positiveListener)
                .setNegativeButton("Cancel", negativeListener);
        return builder.create();
    }

}
