package com.go.kchin.view.fragment;

import android.os.Bundle;

import com.go.kchin.R;
import com.go.kchin.adapters.PurchasesAdapter;
import com.go.kchin.util.dialog.loader.Loader;

/**
 * Created by MAV1GA on 21/02/2017.
 */

public class PurchaseListFragment extends BaseFragment {

    private PurchasesAdapter adapter;

    public static PurchaseListFragment newInstance(){
        PurchaseListFragment fragment = new PurchaseListFragment();
        Bundle args = new Bundle();
        args.putInt(LAYOUT_RES_ID, R.layout.fragment_inventory);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void init() {
        super.init();
        reload();
    }

    private void reload(){
        Loader loader = new Loader(null);
        loader.execute();
    }

    @Override
    public void onLoad() {
        super.onLoad();
    }
}
