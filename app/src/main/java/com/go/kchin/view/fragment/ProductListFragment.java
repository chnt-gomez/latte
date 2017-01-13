package com.go.kchin.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.go.kchin.R;
import com.go.kchin.adapters.ProductListAdapter;
import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.interfaces.RequiredDialogOps;
import com.go.kchin.model.database.Product;
import com.go.kchin.presenter.activities.ProductActivity;
import com.go.kchin.util.dialog.Dialogs;
import com.go.kchin.util.dialog.loader.Loader;

/**
 * Created by MAV1GA on 09/01/2017.
 */

public class ProductListFragment extends BaseFragment implements RequiredDialogOps.RequiredNewProductOps{


    private ListView listView;
    private ProductListAdapter adapter;
    private MainMVP.ProductsPresenterOps mProductsPresenter;

    public static ProductListFragment newInstance(){
        ProductListFragment fragment = new ProductListFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(LAYOUT_RES_ID, R.layout.fragment_inventory );
        fragment.setArguments(arguments);
        return fragment;
    }

    private void reload(){
        final Loader loader = new Loader(this);
        loader.execute();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mProductsPresenter = (MainMVP.ProductsPresenterOps)context;
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btn_add:
                Dialogs.newProductDialog(getContext(), getResources().getString(R.string.new_product),
                        this).show();
                break;
        }
    }

    @Override
    protected void onOperationResultClick(long rowId) {
        super.onOperationResultClick(rowId);
        seeDetail(rowId);
    }

    @Override
    protected void init() {
        super.init();
        listView = (ListView)view.findViewById(R.id.lv_inventory);
        reload();
        FloatingActionButton btnAdd = (FloatingActionButton) view.findViewById(R.id.btn_add);
        addToClickListener(btnAdd);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               seeDetail(adapter.getItem(position).getId());
            }
        });
    }

    @Override
    public void onLoad() {
        super.onLoad();
        adapter = new ProductListAdapter(getContext(), R.layout.row_product_item,
                mProductsPresenter.getAllProducts());
        try{
            updateListView();
        }catch (Exception e){
            Log.w(getClass().getSimpleName(), e.getMessage());
        }
    }

    @Override
    public void onDoneLoading() {
        super.onDoneLoading();
    }

    private void seeDetail(long productId){
        Intent intent = new Intent(getContext(), ProductActivity.class);
        Bundle args = new Bundle();
        args.putLong(ProductActivity.PRODUCT_ID, productId);
        intent.putExtras(args);
        startActivity(intent);
    }

    private void updateListView(){
        if (adapter != null && listView != null) {
            listView.setAdapter(adapter);
        }else{
            throw new RuntimeException("Either adapter or listview is not correctly initialized");
        }
    }

    @Override
    public void showOperationResult(String message, long rowId) {
        super.showOperationResult(message, rowId);
        adapter.add(mProductsPresenter.findProduct(rowId));
    }

    @Override
    public void onNewProduct(Product product) {
        mProductsPresenter.newProduct(product);
    }
}
