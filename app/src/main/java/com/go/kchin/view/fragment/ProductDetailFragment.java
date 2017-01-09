package com.go.kchin.view.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.widget.Button;
import android.widget.EditText;

import com.go.kchin.R;

/**
 * Created by MAV1GA on 09/01/2017.
 */

public class ProductDetailFragment extends BaseFragment {


    private FloatingActionButton btnAdd;
    private EditText edtProductName;
    private Button btnRemaining;

    public static ProductDetailFragment newInstance(long productId){
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(LAYOUT_RES_ID, R.layout.fragment_product_detail);
        fragment.setArguments(arguments);
        return fragment;
    }


}
