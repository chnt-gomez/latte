package com.go.kchin.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.go.kchin.R;
import com.go.kchin.model.database.Department;

import java.util.List;

/**
 * Created by vicente on 19/02/17.
 */

public class DepartmentGridAdapter extends ArrayAdapter<Department> {
    public DepartmentGridAdapter(Context context, int resource, List<Department> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.gird_department_item_layout, null);
        }
        TextView txtDepartment = (TextView)view.findViewById(R.id.txt_department_name);
        TextView txtDepartmentDetail = (TextView)view.findViewById(R.id.txt_department_detail);

        txtDepartment.setText(getItem(position).departmentName);
        return view;
    }
}
