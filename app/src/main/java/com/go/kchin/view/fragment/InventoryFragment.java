package com.go.kchin.view.fragment;

import android.os.Bundle;

import com.go.kchin.R;

/**
 * Created by MAV1GA on 09/02/2017.
 */

public class InventoryFragment extends BaseFragment {

    public static InventoryFragment newInstance(){
        InventoryFragment fragment = new InventoryFragment();
        Bundle args = new Bundle();
        args.putInt(LAYOUT_RES_ID, R.layout.fragment_inventory_options);
        fragment.setArguments(args);
        return fragment;
    }

}
