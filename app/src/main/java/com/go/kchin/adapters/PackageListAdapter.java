package com.go.kchin.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.go.kchin.R;
import com.go.kchin.models.Package;
import com.go.kchin.util.Util;

import java.util.List;

/**
 * Created by MAV1GA on 28/11/2016.
 */

public class PackageListAdapter extends ArrayAdapter<com.go.kchin.models.Package> {
    public PackageListAdapter(Context context, int resource, List<Package> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null){
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(R.layout.row_package_item, null);
        }

        TextView txtPackageName = (TextView)view.findViewById(R.id.txt_package_name);
        TextView txtPackagePrice = (TextView)view.findViewById(R.id.txt_package_cost);
        TextView txtPackageProducts = (TextView)view.findViewById(R.id.txt_products_in_package);
        txtPackageName.setText(getItem(position).getName());
        txtPackagePrice.setText(Util.fromFloat(getItem(position).getPrice()));

        return view;
    }
}
