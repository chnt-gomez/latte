package com.go.kchin.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.go.kchin.R;
import com.go.kchin.models.Product;
import com.go.kchin.util.Util;

import java.util.List;

/**
 * Created by MAV1GA on 11/11/2016.
 */

public class ProductListAdapter extends ArrayAdapter<Product> {

    public ProductListAdapter(Context context, int resource, List<Product> objects) {
        super(context, resource, objects);
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(R.layout.row_product_item, null);
        }

        TextView txtProductName = (TextView)view.findViewById(R.id.txt_product_name);
        TextView txtProductUnit = (TextView)view.findViewById(R.id.txt_product_unit);
        TextView txtProductPrice = (TextView)view.findViewById(R.id.txt_product_sale_price);

        txtProductName.setText(getItem(position).getProductName());
        txtProductUnit.setText(getItem(position).getProductUnit());
        txtProductPrice.setText(Util.fromFloat(getItem(position).getProductSalePrice()));

        return view;

    }


}
