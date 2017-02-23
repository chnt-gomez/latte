package com.go.kchin.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.go.kchin.R;
import com.go.kchin.model.database.Recipe;
import com.go.kchin.util.dialog.MeasurePicker;
import com.go.kchin.util.dialog.number.Number;

import java.util.List;

/**
 * Created by MAV1GA on 21/02/2017.
 */

public class RecipeListAdapter extends ArrayAdapter<Recipe> {
    public RecipeListAdapter(Context context, int resource, List<Recipe> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(R.layout.row_material_item, null);
        }
        TextView txtMaterialName = (TextView) view.findViewById(R.id.txt_material_name);
        TextView txtMaterialUnit = (TextView) view.findViewById(R.id.txt_material_unit);
        TextView txtMaterialAmount = (TextView) view.findViewById(R.id.txt_material_amount);

        final Recipe recipe = getItem(position);

        txtMaterialName.setText(recipe.material.materialName);
        txtMaterialUnit.setText(MeasurePicker.getString(getContext().getResources(), recipe.material.materialMeasure));
        txtMaterialAmount.setText(Number.floatToStringAsNumber(recipe.MaterialAmount));

        return view;
    }
}
