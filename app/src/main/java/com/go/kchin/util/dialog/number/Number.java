package com.go.kchin.util.dialog.number;

import android.util.Log;

import java.util.Locale;

/**
 * Created by MAV1GA on 10/01/2017.
 */

public class Number {

    public static float stringToFloat (String s){
        try{
            return Float.valueOf(s);
        }catch(Exception e){
            Log.w("Number Format!", s+" is not a float number! Returning 0.0 instead");
            return 0.0f;
        }
    }

    public static String floatToStringAsNumber(float f){
        try{
            return String.format(Locale.US, "%.3f", f);
        }catch (Exception e){
            Log.w("Number format!", f+ "cannot be converted to String format: $ #.##");
            return String.valueOf(f);
        }
    }

    public static String floatToStringAsPrice(float f, boolean withPriceTag){
        try{
            return withPriceTag ? String.format(Locale.US, "$ %.2f", f) : String.format(Locale.US, "%.2f", f);
        }catch (Exception e){
            Log.w("Number format!", f+ "cannot be converted to String format: $ #.##");
            return String.valueOf(f);
        }
    }

}
