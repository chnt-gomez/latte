package com.go.kchin.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ListView;

import com.go.kchin.R;
import com.go.kchin.adapters.ProductListAdapter;
import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.interfaces.RequiredDialogOps;
import com.go.kchin.model.database.Product;
import com.go.kchin.presenter.activities.ProductActivity;
import com.go.kchin.util.utilities.Dialogs;
import com.go.kchin.util.utilities.Loader;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;

/**
 * Created by MAV1GA on 09/01/2017.
 */

public class ProductListFragment extends BaseFragment implements RequiredDialogOps.RequiredNewProductOps,
        AdapterView.OnItemClickListener, ViewTreeObserver.OnGlobalLayoutListener{


    private ListView listView;
    private ProductListAdapter adapter;
    private MainMVP.ProductsPresenterOps mProductsPresenter;

    public static ProductListFragment newInstance(){
        ProductListFragment fragment = new ProductListFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(LAYOUT_RES_ID, R.layout.fragment_product_list );
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

    private void onFirstProductAdded(){
        if (view.findViewById(R.id.product_container) != null) {
            listView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            this.showCaseView = new MaterialShowcaseView.Builder(getActivity())
                    .setTarget(view.findViewById(R.id.product_container))
                    .setContentText(getString(R.string.see_a_product))
                    .setDismissOnTouch(true)
                    .withRectangleShape()
                    .setMaskColour(ContextCompat.getColor(getContext(),
                            R.color.colorDarkGrayBlue))
                    .singleUse("see_a_product")
                    .build();
            showCaseView.show(getActivity());
        }
    }

    @Override
    public void onShowTutorial() {
        new MaterialShowcaseView.Builder(getActivity())
                .setTarget(view.findViewById(R.id.btn_add))
                .setContentText(getString(R.string.add_a_product))
                .setDismissOnTouch(true)
                .setMaskColour(ContextCompat.getColor(getContext(),
                        R.color.colorDarkGrayBlue))
                .singleUse("create_a_product")
                .build().show(getActivity());
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
        listView.setEmptyView(view.findViewById(android.R.id.empty));
        listView.getViewTreeObserver().addOnGlobalLayoutListener(this);
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
        reload(null);
    }

    @Override
    public void onOperationSuccesfull(String message) {
        super.onOperationSuccesfull(message);
        reload(null);
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

    @Override
    public void onGlobalLayout() {
        if (adapter != null && adapter.getCount() == 1)
            onFirstProductAdded();
    }
}
