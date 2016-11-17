package com.go.kchin.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.go.kchin.R;
import com.go.kchin.adapters.InventoryListAdapter;
import com.go.kchin.models.Material;
import com.go.kchin.util.Util;

public class MaterialListFragment extends InventoryListFragment{

    private InventoryListAdapter adapter;
    public MaterialListFragment() {
        // Required empty public constructor
    }

    public static MaterialListFragment newInstance() {
        MaterialListFragment fragment = new MaterialListFragment();
        Bundle args = new Bundle();
        args.putInt(LAYOUT_RES, R.layout.fragment_material_list);
        args.putInt(LIST_VIEW_ID, R.id.lv_materials_list_view);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        navigationService.showActionBar();
    }

    @Override
    protected void init() {
        super.init();
        if(inventoryService.getMaterials() != null) {
            adapter = new InventoryListAdapter(getContext(),
                    R.layout.row_material_item, inventoryService.getMaterials());
            listView.setAdapter(adapter);
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
    }

    @Override
    protected void add() {
        Util.newMaterialDialog("New material", null, getActivity(), getActivity().getLayoutInflater(),
                new Util.MaterialDialogEventListener() {
                    @Override
                    public long returnMaterial(Material material) {
                        return inventoryService.addMaterial(material);

                    }

                    @Override
                    public void moveToMaterial(long id) {
                        navigationService.moveToFragment(MaterialDetailFragment.newInstance(id));
                    }
                }).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        navigationService.moveToFragment(MaterialDetailFragment.newInstance(
                adapter.getItem(position).getId()));

    }
}
