package com.go.kchin.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.go.kchin.R;
import com.go.kchin.adapters.ProductListAdapter;
import com.go.kchin.adapters.SaleAdapter;
import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.model.database.Product;
import com.go.kchin.model.database.Sale;
import com.go.kchin.util.dialog.loader.Loader;
import com.go.kchin.util.dialog.number.Number;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by MAV1GA on 18/01/2017.
 */

public class SellProductFragment extends BaseFragment implements AdapterView.OnItemClickListener{

    private ListView listView;
    private ProductListAdapter productListAdapter;
    private MainMVP.SalesPresenterOps mSalesPresenter;
    private SaleAdapter saleAdapter;

    @BindView(R.id.txt_sale_total)
    TextView txtTotal;

    @BindView(R.id.btn_apply_sale)
    Button btnApplySale;

    @BindView(R.id.lv_sale)
    ListView saleListView;

    @BindView(R.id.sliding_layout)
    SlidingUpPanelLayout slideLayout;

    public static SellProductFragment newInstance(){
        SellProductFragment fragment = new SellProductFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(LAYOUT_RES_ID, R.layout.fragment_sell);
        fragment.setArguments(arguments);
        return fragment;
    }

    private void reload(){
        final Loader loader = new Loader(this);
        loader.execute();
    }

    @Override
    public void onOperationSuccesfull(String message, @Nullable long rowId) {
        super.onOperationSuccesfull(message, rowId);
        updateListView();
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
        listView.setOnItemClickListener(this);
        slideLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                Log.d(getClass().getSimpleName(), "Panel changed from "+previousState.name() +" To "+newState.name());
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
        saleAdapter = new SaleAdapter(getContext(), R.layout.row_sell_item, new ArrayList<Sale>());
        saleListView.setAdapter(saleAdapter);
        txtTotal.setText(Number.floatToStringAsPrice(saleAdapter.getTotal(), false));
    }

    @Override
    public void onOperationSuccesfull(String message) {
        super.onOperationSuccesfull(message);
        if (slideLayout != null){
            if (slideLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED){
                slideLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
            saleAdapter.clear();
            txtTotal.setText(Number.floatToStringAsPrice(saleAdapter.getTotal(), false));
        }
        updateListView();
    }

    @Override
    public void onLoad() {
        productListAdapter = new ProductListAdapter(getContext(), R.layout.row_product_item,
                mSalesPresenter.getProducts());
    }

    @Override
    public void onDoneLoading() {
        super.onDoneLoading();
        updateListView();
    }

    private void updateListView(){
        if (productListAdapter != null && listView != null){
            listView.setAdapter(productListAdapter);
        }else{
            throw new RuntimeException();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        addToCurrentSale(productListAdapter.getItem(position));
    }

    @OnClick(R.id.btn_apply_sale)
    public void applySale(View view){
        mSalesPresenter.applyCurrentSale(saleAdapter.getAll());
    }

    private void addToCurrentSale(Product item) {
        Sale sale = new Sale();
        sale.product = item;
        sale.productAmount = 1.0f;
        sale.saleTotal = item.productSellPrice * sale.productAmount;
        saleAdapter.add(sale);
        txtTotal.setText(Number.floatToStringAsPrice(saleAdapter.getTotal(), false));
        showMessage(R.string.added_to_kart);
    }
}
