package com.go.kchin.view.fragment;

import android.os.Bundle;
import com.go.kchin.R;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import butterknife.BindView;

/**
 * Created by vicente on 19/02/17.
 */

public class DetailedSaleFragment extends BaseFragment {

    @BindView(R.id.gird_view)
    GridView girdView;

    @BindView(R.id.sliding_layout)
    SlidingUpPanelLayout slideLayout;

    @BindView(R.id.txt_sale_total)
    TextView txtTotal;

    public static DetailedSaleFragment newInstance() {
        DetailedSaleFragment fragment = new DetailedSaleFragment();
        Bundle args = new Bundle();
        args.putInt(LAYOUT_RES_ID, R.layout.fragment_sell);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void init() {
        super.init();
        slideLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                if (newState == SlidingUpPanelLayout.PanelState.EXPANDED){
                    txtTotal.setCompoundDrawablesWithIntrinsicBounds
                            (R.drawable.ic_attach_money_white_24dp, 0, R.drawable.ic_keyboard_arrow_down_white_24dp, 0);
                }
                if (newState == SlidingUpPanelLayout.PanelState.COLLAPSED){
                    txtTotal.setCompoundDrawablesWithIntrinsicBounds
                            (R.drawable.ic_attach_money_white_24dp, 0, R.drawable.ic_keyboard_arrow_up_white_24dp, 0);
                }
            }
        });
    }
}
