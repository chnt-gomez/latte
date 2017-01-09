package com.go.kchin.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.go.kchin.R;
import com.go.kchin.model.database.Sale;

import java.util.List;

/**
 * Created by MAV1GA on 09/12/2016.
 */

public class SaleAdapter extends ArrayAdapter<Sale> {

    public SaleAdapter(Context context, int resource, List<Sale> objects) {
        super(context, resource, objects);
    }

    public void setItems(List<Sale> items){
        this.clear();
        this.addAll(items);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(R.layout.row_sell_item, null);
        }

        TextView txtProductName = (TextView)view.findViewById(R.id.txt_sell_name);
        TextView txtProductUnit = (TextView)view.findViewById(R.id.txt_sell_unit);
        TextView txtProductPrice = (TextView)view.findViewById(R.id.txt_sell_sale_price);



        return view;

    }
}
