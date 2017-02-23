package com.go.kchin.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.go.kchin.R;
import com.go.kchin.adapters.MaterialListAdapter;
import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.util.dialog.loader.Loader;

import butterknife.BindView;

/**
 * Created by MAV1GA on 26/01/2017.
 */

public class BuildRecipeFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    @BindView(R.id.lv_inventory)
    ListView listView;

    @BindView(R.id.btn_add)
    FloatingActionButton btnAdd;

    private final static String PRODUCT_ID = "product_id";
    private MainMVP.ProductPresenterOps mProductPresenter;
    private MaterialListAdapter adapter;

    public static BuildRecipeFragment newInstance(long productId){
        BuildRecipeFragment fragment = new BuildRecipeFragment();
        Bundle args = new Bundle();
        args.putInt(LAYOUT_RES_ID, R.layout.fragment_material_list);
        args.putLong(PRODUCT_ID, productId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.setActivityTitle(getString(R.string.select_material));
    }

    @Override
    protected void init() {
        super.init();
        btnAdd.setVisibility(FloatingActionButton.GONE);
        listView.setOnItemClickListener(this);
        reload();
    }

    private void reload() {
        Loader loader = new Loader(this);
        loader.execute();
    }

    @Override
    public void onPreLoad() {
        super.onPreLoad();
        adapter = new MaterialListAdapter(getContext(), R.layout.row_material_item,
                mProductPresenter.getAllMaterials());
    }

    @Override
    public void onDoneLoading() {
        super.onDoneLoading();
        listView.setAdapter(adapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mProductPresenter = (MainMVP.ProductPresenterOps)context;
    }

    @Override
    public void onDetach() {
        mProductPresenter = null;
        super.onDetach();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mProductPresenter.addMaterialToProductRecipe(getArguments().getLong(PRODUCT_ID), adapter.getItem(position));
    }
}
