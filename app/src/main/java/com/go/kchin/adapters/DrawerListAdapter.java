package com.go.kchin.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.go.kchin.R;


/**
 * Created by MAV1GA on 18/11/2016.
 */
public class DrawerListAdapter extends ArrayAdapter<String> {
    public DrawerListAdapter(Context context, int resource, String[] objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.row_drawer_item, null);
        }
        final TextView txtDrawerItem = (TextView)view.findViewById(R.id.txt_drawer_item);
        txtDrawerItem.setText(getItem(position));
        return view;
    }
}
