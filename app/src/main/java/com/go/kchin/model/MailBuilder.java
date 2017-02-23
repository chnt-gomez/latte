package com.go.kchin.model;


import com.go.kchin.R;

import java.util.List;

/**
 * Created by MAV1GA on 17/02/2017.
 */

public class MailBuilder {

    public static String buildPurchaseOrder(List<DepletedItem> item, String storeName,
                                            String date, android.content.res.Resources res){
        StringBuilder sb = new StringBuilder();
        sb.append("<h3 color='#1c2430'>").append(res.getString(R.string.purchase_orders)).append("<h3>");
        return sb.toString();
    }

}
