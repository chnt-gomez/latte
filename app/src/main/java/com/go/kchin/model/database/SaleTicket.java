package com.go.kchin.model.database;

import com.orm.SugarRecord;

/**
 * Created by MAV1GA on 06/01/2017.
 */

public class SaleTicket extends SugarRecord {

    public long dateTime;
    public float saleTotal;
    public String vendor;

    public SaleTicket(){}

}
