package com.go.kchin.model.database;

import com.orm.SugarRecord;

/**
 * Created by MAV1GA on 06/01/2017.
 */

public class Sale extends SugarRecord {

    public Sale(){}

    public Product product;

    public SaleTicket saleTicket;

    public String saleConcept;
    public float productAmount;
    public float saleTotal;

}
