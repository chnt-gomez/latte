package com.go.kchin.model.database;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.util.List;

/**
 * Created by MAV1GA on 06/01/2017.
 */

public class Product extends SugarRecord {

    public Product(){}

    public String productName;
    public float productRemaining;
    public int productMeasureUnit;
    public float productSellPrice;
    public int productStatus;
    public float productPurchaseCost;
    public int productType;
    public int madeOnSell;
    public int productIndicator;

    @Ignore
    public static final int PRODUCT_TYPE_BUY_AND_SELL = 1;

    @Ignore
    public static final int PRODUCT_TYPE_MADE = 0;

    @Ignore
    public static final int PRODUCT_MADE_AND_STORE = 0;

    @Ignore
    public static final int PRODUCT_MADE_AND_SELL = 1;


    public Department department;


    public void setProductSellPrice(float productSellPrice) {
        this.productSellPrice = productSellPrice;
    }

    public String getDepartmentName() {
       if(department == null){
           return " - ";
       }else{
           return department.departmentName;
       }
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
