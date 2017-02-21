package com.go.kchin.model.database;

import com.orm.SugarRecord;

/**
 * Created by MAV1GA on 21/02/2017.
 */

public class PurchaseOperation extends SugarRecord {

    public String purchaseConcept;
    public int purchaseItems;
    public float purchaseAmount;
    public long purchaseDateTime;

}
