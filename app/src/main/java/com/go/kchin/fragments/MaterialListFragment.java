package com.go.kchin.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;

import com.go.kchin.R;
import com.go.kchin.adapters.MaterialListAdapter;
import com.go.kchin.models.Material;
import com.go.kchin.util.Util;

import java.util.List;

public class MaterialListFragment extends InventoryListFragment{

    public static final int ALL_MATERIALS = -1;
    protected static final String PRODUCT_ID = "product_id";
    private MaterialListAdapter adapter;
    private FloatingActionButton btnAddToRecipe;
    public MaterialListFragment() {
        // Required empty public constructor
    }

    public static MaterialListFragment newInstance(long productId) {
        MaterialListFragment fragment = new MaterialListFragment();
        Bundle args = new Bundle();
        args.putInt(LAYOUT_RES, R.layout.fragment_material_list);
        args.putInt(LIST_VIEW_ID, R.id.lv_materials_list_view);
        if (productId != -1)
            args.putLong(PRODUCT_ID, productId);
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
        final List<Material> items;
        if (getArguments().containsKey(PRODUCT_ID)){
            items = inventoryService.getMaterialsFromProduct(getArguments().getLong(PRODUCT_ID));
            btnAddToRecipe = (FloatingActionButton)findViewById(R.id.btn_add_to_recipe);
            btnAddToRecipe.setVisibility(FloatingActionButton.VISIBLE);
            addOnClickListener(btnAddToRecipe);
        }else{
            items = inventoryService.getMaterials();
        }
        adapter = new MaterialListAdapter(getContext(),
                R.layout.row_material_item, items);
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch(view.getId()){
            case R.id.btn_add_to_recipe:
                Util.newChooseMaterialDialog("Select product", null, getActivity(), getActivity().getLayoutInflater(),
                        new Util.UtilDialogEventListener() {
                            @Override
                            public void returnFloat(float arg) {
                                //Unused
                            }

                            @Override
                            public void returnString(String arg) {
                                //Unused
                            }

                            @Override
                            public void returnLong(long arg) {
                                inventoryService.addMaterialToRecipe(arg, getArguments().getLong(PRODUCT_ID));
                            }
                        }, inventoryService.getMaterials()).show();
                break;
        }
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
