package com.go.kchin.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ListView;

import com.go.kchin.R;
import com.go.kchin.adapters.MaterialListAdapter;
import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.util.dialog.loader.Loader;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by MAV1GA on 26/01/2017.
 */

public class RecipeListFragment extends BaseFragment {



    private static final String PRODUCT_ID = "product_id";
    private MaterialListAdapter adapter;
    private MainMVP.ProductPresenterOps mProductPresenter;

    @BindView(R.id.lv_inventory)
    ListView listView;

    @BindView(R.id.btn_add)
    FloatingActionButton btnAdd;

    public static RecipeListFragment newInstance(long productId){
        RecipeListFragment fragment = new RecipeListFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(LAYOUT_RES_ID, R.layout.fragment_inventory);
        arguments.putLong(PRODUCT_ID, productId);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    protected void init() {
        super.init();
        reload();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mProductPresenter = (MainMVP.ProductPresenterOps) context;
    }

    @Override
    public void onDetach() {
        mProductPresenter = null;
        super.onDetach();
    }

    private void reload() {
        Loader loader = new Loader(this);
        loader.execute();
    }

    @Override
    public void onLoad() {
        super.onLoad();
        adapter = new MaterialListAdapter(getContext(), R.layout.row_material_item,
                mProductPresenter.getRecipe(getArguments().getLong(PRODUCT_ID)));
    }

    @OnClick(R.id.btn_add)
    public void addToRecipe(View view){
        mPresenter.moveToFragment(BuildRecipeFragment.newInstance(getArguments().getLong(PRODUCT_ID)));
    }

    @Override
    public void onDoneLoading() {
        super.onDoneLoading();
        listView.setAdapter(adapter);
    }
}
