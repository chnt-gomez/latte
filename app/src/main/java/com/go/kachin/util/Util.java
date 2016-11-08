package com.go.kachin.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.go.kachin.R;
import com.go.kachin.models.Material;

/**
 * Created by vicente on 7/11/16.
 */
public class Util {

    public static Dialog newMaterialDialog(String dialogTitle, String dialogMessage, Context context,
                                             final LayoutInflater inflater,final NewMaterialInterface callback){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final View view = inflater.inflate(R.layout.dialog_new_material, null);
        final Material[] material = new Material[1];
        builder.setView(view)
                .setTitle(dialogTitle)
                .setMessage(dialogMessage)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String materialName = ((EditText)view.findViewById(R.id.edt_material_name))
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

    public interface NewMaterialInterface{
        void returnMaterial(Material material);
    }

}
