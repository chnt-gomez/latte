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
import com.go.kchin.model.DepletedItem;
import com.go.kchin.util.utilities.NFormatter;

import java.util.List;

/**
 * Created by MAV1GA on 16/02/2017.
 */

public class LowInventoryAdapter extends ArrayAdapter<DepletedItem>{


    public LowInventoryAdapter(Context context, int resource, List<DepletedItem> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       View view = convertView;
        if (view == null){
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(R.layout.row_product_item, null);
        }
        TextView txtPurchaseName = (TextView)view.findViewById(R.id.txt_product_name);
        //TextView txtPurchaseRemaining = (TextView)view.findViewById(R.id.txt_product_remaining);
        TextView txtPurchaseRemaining = (TextView)view.findViewById(R.id.txt_product_sale_price);

        String purchaseName = getItem(position).getItemName();
        String purchaseRemaining = NFormatter.floatToStringAsNumber(getItem(position).getExistences());

        txtPurchaseName.setText(purchaseName);
        txtPurchaseRemaining.setText(purchaseRemaining);

        /**
         * Color
         */
        if (getItem(position).getExistences() <= 0){
            txtPurchaseRemaining.setTextColor(Color.parseColor("#ff7f7f"));
        }else{
            txtPurchaseRemaining.setTextColor(Color.parseColor("#808080"));
        }

        return view;
    }
}
