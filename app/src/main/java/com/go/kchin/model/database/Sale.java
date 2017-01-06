package com.go.kchin.model.database;

import com.orm.SugarRecord;

/**
 * Created by MAV1GA on 06/01/2017.
 */

public class Sale extends SugarRecord {

    public Sale(){}

    Product product;
    SaleTicket saleTicket;

    public float productAmount;
    public float saleTotal;

}
