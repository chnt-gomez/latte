package com.go.kchin.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.go.kchin.R;
import com.go.kchin.models.Material;
import com.go.kchin.util.Util;

import java.util.List;

/**
 * Created by MAV1GA on 08/11/2016.
 */

public class InventoryListAdapter extends ArrayAdapter<Material> {

    private List<Material> items;

    public InventoryListAdapter (Context context, int resource, List<Material> items){
        super(context, resource, items);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(R.layout.row_material_item, null);
        }
        TextView txtMaterialName = (TextView)view.findViewById(R.id.txt_material_name);
        TextView txtMaterialUnit = (TextView)view.findViewById(R.id.txt_material_unit);
        TextView txtMaterialAmount = (TextView)view.findViewById(R.id.txt_material_amount);

        txtMaterialName.setText(getItem(position).getMaterialName());
        txtMaterialUnit.setText(getItem(position).getMaterialUnit());
        txtMaterialAmount.setText(Util.fromFloat(getItem(position).getMaterialAmount()));

        return view;
    }

    public void setItems(List<Material> items) {
        this.items = items;
    }
}
