package com.go.kchin.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.go.kchin.R;
import com.go.kchin.model.database.PurchaseOperation;

import java.util.List;

/**
 * Created by MAV1GA on 21/02/2017.
 */

public class PurchasesAdapter extends ArrayAdapter<PurchaseOperation> {

    public PurchasesAdapter(Context context, int resource, List<PurchaseOperation> objects) {
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

        return view;
    }
}
