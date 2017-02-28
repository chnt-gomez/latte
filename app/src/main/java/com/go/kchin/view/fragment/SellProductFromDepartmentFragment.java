package com.go.kchin.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.go.kchin.R;
import com.go.kchin.adapters.ProductListAdapter;
import com.go.kchin.adapters.SaleAdapter;
import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.model.database.Product;
import com.go.kchin.model.database.Sale;
import com.go.kchin.util.utilities.Loader;
import com.go.kchin.util.utilities.NFormatter;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by MAV1GA on 18/01/2017.
 */

public class SellProductFromDepartmentFragment extends BaseFragment implements AdapterView.OnItemClickListener{

    private ProductListAdapter productListAdapter;
    private MainMVP.SalesPresenterOps mSalesPresenter;

    private static final String DEPARTMENT_GROUP = "department_group";
    private long departmentId;

    @BindView(R.id.lv_inventory)
    ListView listView;

    public static SellProductFromDepartmentFragment newInstance(long departmentId){
        SellProductFromDepartmentFragment fragment = new SellProductFromDepartmentFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(LAYOUT_RES_ID, R.layout.fragment_sell);
        arguments.putLong(DEPARTMENT_GROUP, departmentId );
        fragment.setArguments(arguments);
        return fragment;
    }

    private void reload(@Nullable String query){
        final Loader loader = new Loader(this);
        loader.execute(query);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mSalesPresenter = (MainMVP.SalesPresenterOps)context;
    }



    @Override
    protected void init() {
        super.init();
        listView.setEmptyView(view.findViewById(android.R.id.empty));
        departmentId = getArguments().getLong(DEPARTMENT_GROUP);
        reload(null);
        listView.setOnItemClickListener(this);

    }

    @Override
    public void onLoad() {
        productListAdapter = new ProductListAdapter(getContext(), R.layout.row_product_item,
                mSalesPresenter.getProductsInDepartment(departmentId));
    }

    @Override
    public void onDoneLoading() {
        super.onDoneLoading();
       listView.setAdapter(productListAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        addToCurrentSale(productListAdapter.getItem(position));
    }
    private void addToCurrentSale(Product item) {
        showMessage(R.string.added_to_kart);
    }

}
