package com.go.kchin.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.go.kchin.R;
import com.go.kchin.adapters.MaterialListAdapter;
import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.interfaces.RequiredDialogOps;
import com.go.kchin.model.database.Material;
import com.go.kchin.model.database.Product;
import com.go.kchin.util.utilities.Dialogs;
import com.go.kchin.util.utilities.Loader;

import butterknife.BindView;
import butterknife.OnClick;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;

/**
 * Created by MAV1GA on 26/01/2017.
 */

public class BuildRecipeFragment extends BaseFragment implements AdapterView.OnItemClickListener, RequiredDialogOps.RequiredNewMaterialOps {

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

    @OnClick(R.id.btn_add)
    public void addNewMaterial(View v){
        Dialogs.newMaterialDialog(getContext(), getString(R.string.new_material), this).show();
    }

    @Override
    protected void init() {
        super.init();
        listView.setOnItemClickListener(this);
        listView.setEmptyView(view.findViewById(android.R.id.empty));
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
    protected void onOperationResultClick(long rowId) {
        mProductPresenter.addMaterialToProductRecipe(getArguments().getLong(PRODUCT_ID), rowId);
    }

    @Override
    public void onOperationSuccesfull(String message, @Nullable final long rowId) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).setAction(
                R.string.add, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onOperationResultClick(rowId);
                    }
                }
        ).show();
        reload();
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

    @Override
    public void onNewMaterial(Material material) {
        mProductPresenter.addMaterial(material);
    }


}
