package com.go.kchin.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.go.kchin.util.dialog.MeasurePicker;

/**
 * Created by MAV1GA on 25/01/2017.
 */

public class SpinnerMeasureAdapter extends ArrayAdapter<String> {

    public SpinnerMeasureAdapter(Context context, int resource, String[] objects) {
        super(context, resource, objects);
    }
}
