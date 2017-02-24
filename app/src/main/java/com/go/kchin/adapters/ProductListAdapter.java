package com.go.kchin.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.go.kchin.R;
import com.go.kchin.model.database.Product;
import com.go.kchin.util.utilities.NFormatter;

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
        TextView txtProductRemaining = (TextView)view.findViewById(R.id.txt_product_remaining);
        TextView txtProductPrice = (TextView)view.findViewById(R.id.txt_product_sale_price);

        String productName = getItem(position).productName;
        String productRemaining = NFormatter.floatToStringAsNumber(getItem(position).productRemaining);
        String productPrice = NFormatter.floatToStringAsPrice(getItem(position).productSellPrice, true);

        txtProductName.setText(productName);
        if (getItem(position).productType == Product.PRODUCT_TYPE_BUY_AND_SELL) {
            txtProductRemaining.setText(productRemaining);
        }else{
            txtProductRemaining.setText(R.string.without_inv);
        }
        txtProductPrice.setText(productPrice);

        /**
         * Coloring
         */
        if (getItem(position).productRemaining <= 0 &&
                getItem(position).productType == Product.PRODUCT_TYPE_BUY_AND_SELL){
            txtProductRemaining.setTextColor(Color.parseColor("#ff7f7f"));
        }else{
            txtProductRemaining.setTextColor(Color.parseColor("#808080"));
        }

        return view;

    }

}
