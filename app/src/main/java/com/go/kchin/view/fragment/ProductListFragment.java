package com.go.kchin.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

public class ProductListFragment extends BaseFragment implements RequiredDialogOps.RequiredNewProductOps,
        AdapterView.OnItemClickListener{


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

    private void reload(@Nullable String query){
        final Loader loader = new Loader(this);
        loader.execute(query);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mProductsPresenter = (MainMVP.ProductsPresenterOps) context;
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
    public void onResume() {
        super.onResume();
        reload(null);
    }

    @Override
    public void search(String query) {
        super.search(query);
        reload(query);
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
        FloatingActionButton btnAdd = (FloatingActionButton) view.findViewById(R.id.btn_add);
        addToClickListener(btnAdd);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        adapter = new ProductListAdapter(getContext(), R.layout.row_product_item,
                mProductsPresenter.getAllProducts());

    }

    @Override
    public void onDoneLoading() {
        super.onDoneLoading();
        updateListView();
    }

    @Override
    public void onSearch(String query) {
        adapter = new ProductListAdapter(getContext(), R.layout.row_product_item,
                mProductsPresenter.getAllProducts(query));
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
            throw new RuntimeException("Either the Adapter or ListView is not correctly initialized");
        }
    }

    @Override
    public void onOperationSuccesfull(String message, @Nullable long rowId) {
        super.onOperationSuccesfull(message, rowId);
        updateListView();
    }

    @Override
    public void onOperationSuccesfull(String message) {
        super.onOperationSuccesfull(message);
        updateListView();
    }

    @Override
    public void onNewProduct(Product product) {
        mProductsPresenter.newProduct(product);
    }

    @Override
    public void onDetach() {
        mProductsPresenter = null;
        super.onDetach();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        seeDetail(adapter.getItem(position).getId());
    }
}
