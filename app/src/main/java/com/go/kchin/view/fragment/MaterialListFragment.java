package com.go.kchin.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.go.kchin.R;
import com.go.kchin.adapters.MaterialListAdapter;
import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.interfaces.RequiredDialogOps;
import com.go.kchin.model.database.Material;
import com.go.kchin.presenter.activities.MaterialActivity;
import com.go.kchin.util.dialog.Dialogs;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by MAV1GA on 11/01/2017.
 */

public class MaterialListFragment extends BaseFragment implements RequiredDialogOps.RequiredNewMaterialOps{

    private MainMVP.MaterialsPresenterOps mMaterialsPresenter;
    private ListView listView;
    private MaterialListAdapter adapter;
    @BindView(R.id.btn_add)FloatingActionButton btnAdd;


    public static MaterialListFragment newInstance(){
        MaterialListFragment fragment = new MaterialListFragment();
        Bundle args = new Bundle();
        args.putInt(LAYOUT_RES_ID, R.layout.fragment_inventory);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onNewMaterial(Material material) {
        mMaterialsPresenter.newMaterial(material);
        reload();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mMaterialsPresenter = (MainMVP.MaterialsPresenterOps)context;
    }

    @Override
    public void onDetach() {
        this.mMaterialsPresenter = null;
        super.onDetach();
    }



    @OnClick(R.id.btn_add)
    public void newMaterial(View view){
        Dialogs.newMaterialDialog(getContext(), null, this).show();
    }


    @Override
    protected void init() {
        super.init();
        listView = (ListView)view.findViewById(R.id.lv_inventory);
        reload();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                seeDetail(adapter.getItem(position).getId());
            }
        });
    }

    @Override
    protected void onOperationResultClick(long rowId) {
        seeDetail(rowId);
    }

    private void seeDetail(long rowId){
        Intent intent = new Intent(getActivity(), MaterialActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong(MaterialActivity.MATERIAL_ID, rowId);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void reload(){
        adapter = new MaterialListAdapter(getContext(), R.layout.row_material_item,
                mMaterialsPresenter.getAllMaterials());
        if (listView != null) {
            listView.setAdapter(adapter);
        }else{
            throw new RuntimeException("Either adapter or listview is not correctly initialized");
        }
    }
}
