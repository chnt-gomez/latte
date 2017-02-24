package com.go.kchin.model.database;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

/**
 * Created by MAV1GA on 06/01/2017.
 */

public class Product extends SugarRecord {

    @Ignore
    public static final int MEASURE_PIECE = 0;

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
    public static final int PRODUCT_TYPE_STORED = 1;

    @Ignore
    public static final int PRODUCT_TYPE_MADE_ON_SALE = 0;



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
