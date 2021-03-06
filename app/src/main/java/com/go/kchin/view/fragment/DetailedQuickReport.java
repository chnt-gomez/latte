package com.go.kchin.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.widget.ExpandableListView;

import com.go.kchin.R;
import com.go.kchin.adapters.QuickReportAdapter;
import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.model.database.Sale;
import com.go.kchin.model.database.SaleTicket;
import com.go.kchin.util.utilities.Loader;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * Created by MAV1GA on 23/01/2017.
 */

public class DetailedQuickReport extends BaseFragment {

    @BindView(R.id.exlv_sales)
    ExpandableListView explvSales;

    private QuickReportAdapter adapter;
    private MainMVP.DetailedReportPresenterOps mReportPresenter;

    private static final String DATE_TIME = "date_time";

    public static DetailedQuickReport newInstance(DateTime dateTime){
        DetailedQuickReport fragment = new DetailedQuickReport();
        Bundle args = new Bundle();
        args.putInt(LAYOUT_RES_ID, R.layout.fragment_detailed_report);
        args.putLong(DATE_TIME, dateTime.getMillis());
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        DateTime dateTime = new DateTime(getArguments().getLong(DATE_TIME));
        HashMap<SaleTicket, List<Sale>> expandableHashMap = new HashMap<>();
        // First get the SaleTickets
        final List<SaleTicket> tickets = mReportPresenter.getDaySaleTickets(dateTime);
        //From each saleTicker, get all the tickets;
        for (SaleTicket st : tickets){
            List<Sale> sales = new ArrayList<>();
            sales.addAll(mReportPresenter.getSalesInTicket(st));
            expandableHashMap.put(st, sales);
        }
        adapter = new QuickReportAdapter(getContext(), tickets, expandableHashMap);

    }

    @Override
        public void onDoneLoading() {
        super.onDoneLoading();
        updateListView();
    }

    private void updateListView(){
        if (adapter != null && explvSales != null){
            explvSales.setAdapter(adapter);
        }else{
            throw new RuntimeException("Either the Adapter or ListView is not correctly initialized");
        }
    }

    @Override
    protected void init() {
        super.init();
        reload();
    }

    private void reload() {
        final Loader loader = new Loader(this);
        loader.execute();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mReportPresenter = (MainMVP.DetailedReportPresenterOps) context;
    }
}
