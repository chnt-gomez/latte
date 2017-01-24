package com.go.kchin.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import com.go.kchin.R;
import com.go.kchin.model.database.Sale;
import com.go.kchin.model.database.SaleTicket;
import com.go.kchin.util.dialog.number.Number;

import java.util.HashMap;
import java.util.List;

/**
 * Created by MAV1GA on 23/01/2017.
 */

public class QuickReportAdapter extends BaseExpandableListAdapter{

    private Context context;
    private List<SaleTicket> expandableListTitle;
    private HashMap<SaleTicket, List<Sale>> expandableListDetail;

    public QuickReportAdapter (Context context, List<SaleTicket> titles, HashMap<SaleTicket, List<Sale>>
            details){
        this.context = context;
        this.expandableListTitle = titles;
        this.expandableListDetail = details;
    }

    @Override
    public int getGroupCount() {
        return expandableListTitle.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
       return this.expandableListDetail.get(this.expandableListTitle.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.expandableListTitle.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return expandableListDetail.get(expandableListTitle.get(groupPosition))
                .get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        SaleTicket header = (SaleTicket)getGroup(groupPosition);
        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater)this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.row_saleticket_item, null);
        }
        TextView txtTicketTotal = (TextView)convertView.findViewById(R.id.txt_sale_ticket);
        if (header != null){
            txtTicketTotal.setText(Number.floatToStringAsPrice(header.saleTotal, true));
        }
        TextView txtTicketId = (TextView)convertView.findViewById(R.id.txt_sale_id);
        txtTicketId.setText("Sale: "+header.getId());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final Sale childSale = (Sale)getChild(groupPosition, childPosition);
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater)this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.row_sell_item, null);
        }
        TextView child = (TextView)convertView.findViewById(R.id.txt_sell_name);
        child.setText(childSale.product.productName);
        child.setTextColor(Color.BLACK);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
