package com.go.kchin.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

import com.go.kchin.R;
import com.go.kchin.adapters.PurchasesAdapter;
import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.model.database.PurchaseOperation;
import com.go.kchin.util.dialog.loader.Loader;

import org.joda.time.DateTime;

import java.util.List;

import butterknife.BindView;

/**
 * Created by MAV1GA on 21/02/2017.
 */

public class PurchaseListFragment extends BaseFragment{

    private PurchasesAdapter adapter;
    private MainMVP.PurchasesPresenterOps purchasesPresenter;

    @BindView(R.id.lv_inventory)
    ListView listView;

    public static PurchaseListFragment newInstance(){
        PurchaseListFragment fragment = new PurchaseListFragment();
        Bundle args = new Bundle();
        args.putInt(LAYOUT_RES_ID, R.layout.fragment_purchase_list);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void init() {
        super.init();
        reload();
    }

    private void reload(){
        Loader loader = new Loader(this);
        loader.execute();
    }

    @Override
    public void onLoad() {
        super.onLoad();
        adapter = new PurchasesAdapter(getContext(), R.layout.row_purchase_item,
                purchasesPresenter.getPurchases(DateTime.now()));
    }

    @Override
    public void onDoneLoading() {
        super.onDoneLoading();
        listView.setAdapter(adapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.purchasesPresenter = (MainMVP.PurchasesPresenterOps)context;
    }
}
