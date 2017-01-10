package com.go.kchin.presenter.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.go.kchin.R;
import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.model.database.Department;
import com.go.kchin.model.database.Product;
import com.go.kchin.view.fragment.ProductDetailFragment;


/**
 * Created by MAV1GA on 09/01/2017.
 */

public class ProductActivity extends BaseActivity implements MainMVP.ProductPresenterOps{

    public static final String PRODUCT_ID = "product_id";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        init();
    }

    @Override
    protected void init() {
        super.init();
        if (getIntent().getExtras().containsKey(PRODUCT_ID)){
            /**
             * Contains a product, show the info
             */
            attachFragment(ProductDetailFragment.newInstance(getIntent().getExtras().getLong(PRODUCT_ID)));
        }
    }

    @Override
    public Product getProduct(final long id) {
        return mModel.getProduct(id);
    }

    @Override
    public String getDepartmentNameFromProduct(long departmentId) {
        Department department = Department.findById(Department.class, departmentId);
        return department.departmentName;
    }

}
