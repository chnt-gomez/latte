package com.go.kchin.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ListView;

import com.go.kchin.R;
import com.go.kchin.adapters.SaleAdapter;
import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.interfaces.RequiredDialogOps;
import com.go.kchin.model.database.Sale;
import com.go.kchin.util.dialog.Dialogs;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;

/**
 * Created by MAV1GA on 18/01/2017.
 */

public class CurrentSaleFragment extends BaseFragment implements RequiredDialogOps.RequiredNewSaleOps{

    private MainMVP.SalesPresenterOps salesPresenterOps;
    private SaleAdapter adapter;
    private ListView lv;


    @BindView(R.id.btn_sell)
    FloatingActionButton btnAdd;

    public static CurrentSaleFragment newInstance(){
        CurrentSaleFragment fragment = new CurrentSaleFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(LAYOUT_RES_ID, R.layout.fragment_quick_sale );
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onOperarionSuccesfull(String message, @Nullable long rowId) {
        super.onOperarionSuccesfull(message, rowId);
        adapter.update(salesPresenterOps.getCurrentSale());
    }

    @Override
    public void onOperarionSuccesfull(String message) {
        super.onOperarionSuccesfull(message);
        adapter.update(salesPresenterOps.getCurrentSale());
    }

    @Override
    protected void init() {
        super.init();
        lv = (ListView)view.findViewById(R.id.lv_sale);
        adapter = new SaleAdapter(getContext(), R.layout.row_sell_item, salesPresenterOps.getCurrentSale());
        lv.setAdapter(adapter);
    }

    @OnClick(R.id.btn_sell)
    public void addToSale(View view){
       mPresenter.moveToFragment(SellProductFragment.newInstance());
    }

    @OnLongClick(R.id.btn_sell)
    public boolean applySale(View view) {
        Dialogs.newApplySaleDialog(getContext(), null, salesPresenterOps.getCurrentSale(), this)
                .show();
        return true;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        salesPresenterOps = (MainMVP.SalesPresenterOps)context;
    }

    @Override
    public void onDetach() {
        salesPresenterOps = null;
        super.onDetach();
    }

    @Override
    public void onNewSale() {
        salesPresenterOps.applyCurrentSale();
    }
}



