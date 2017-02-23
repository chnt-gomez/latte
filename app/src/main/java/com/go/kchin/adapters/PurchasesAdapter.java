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
import com.go.kchin.model.database.PurchaseOperation;
import com.go.kchin.util.dialog.number.Number;

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
            view = layoutInflater.inflate(R.layout.row_purchase_item, null);
        }

        TextView txtAmount = (TextView)view.findViewById(R.id.txt_items_amount);
        TextView txtItemName = (TextView)view.findViewById(R.id.txt_item_name);
        TextView txtPurchaseCost = (TextView)view.findViewById(R.id.txt_purchase_amount);

        PurchaseOperation operation = getItem(position);

        if (operation != null){
            txtAmount.setText(Number.floatToStringAsNumber(operation.purchaseItems));
            txtItemName.setText(operation.purchaseConcept);
            txtPurchaseCost.setText(Number.floatToStringAsPrice(operation.purchaseAmount, true));
        }

        return view;
    }
}
