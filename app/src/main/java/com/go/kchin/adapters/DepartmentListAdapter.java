package com.go.kchin.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.go.kchin.R;
import com.go.kchin.models.Department;

import java.util.List;

/**
 * Created by MAV1GA on 14/11/2016.
 */

public class DepartmentListAdapter extends ArrayAdapter<Department>{

    public DepartmentListAdapter(Context context, int resource, List<Department> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.row_despartment_item, null);
        }
        TextView txtDepartment = (TextView)view.findViewById(R.id.txt_department_name);
        TextView txtDeparmentProductsAmount = (TextView)view.findViewById(R.id.txt_department_products_amount);

        txtDepartment.setText(getItem(position).getDepartmentName());
        txtDeparmentProductsAmount.setText(String.valueOf(getItem(position).getProductsInDepartment()) + " " +
                "products.");
        return view;
    }
}
