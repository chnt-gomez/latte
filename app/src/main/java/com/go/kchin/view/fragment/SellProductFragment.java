package com.go.kchin.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.go.kchin.R;
import com.go.kchin.adapters.ProductListAdapter;
import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.util.dialog.loader.Loader;

/**
 * Created by MAV1GA on 18/01/2017.
 */

public class SellProductFragment extends BaseFragment implements AdapterView.OnItemClickListener{

    private ListView listView;
    private ProductListAdapter adapter;
    private MainMVP.SalesPresenterOps mSalesPresenter;

    public static SellProductFragment newInstance(){
        SellProductFragment fragment = new SellProductFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(LAYOUT_RES_ID, R.layout.fragment_inventory);
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
        mSalesPresenter = (MainMVP.SalesPresenterOps)context;
    }

    @Override
    protected void init() {
        super.init();
        listView = (ListView)view.findViewById(R.id.lv_inventory);
        reload();
        FloatingActionButton button = (FloatingActionButton)view.findViewById(R.id.btn_add);
        button.setVisibility(View.GONE);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onLoad() {
        adapter = new ProductListAdapter(getContext(), R.layout.row_product_item,
                mSalesPresenter.getProducts());
    }

    @Override
    public void onDoneLoading() {
        super.onDoneLoading();
        updateListView();
    }

    private void updateListView(){
        if (adapter != null && listView != null){
            listView.setAdapter(adapter);
        }else{
            throw new RuntimeException();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mSalesPresenter.pickProduct(adapter.getItem(position));
    }
}
