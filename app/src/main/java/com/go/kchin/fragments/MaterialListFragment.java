package com.go.kchin.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.go.kchin.R;
import com.go.kchin.adapters.MaterialListAdapter;
import com.go.kchin.models.Material;
import com.go.kchin.models.Operation;
import com.go.kchin.util.Util;

import java.util.List;

public class MaterialListFragment extends InventoryListFragment{

    public static final int ALL_MATERIALS = -1;
    protected static final String PRODUCT_ID = "product_id";
    private MaterialListAdapter adapter;
    private List<Material> items;

    @Override
    public void onSearch(String args) {
        if (args == null)
            updateItemList(inventoryService.getMaterials());
        else
            updateItemList(inventoryService.searchMaterials(args));
    }

    public MaterialListFragment() {
        // Required empty public constructor
    }

    public static MaterialListFragment newInstance(long productId) {
        MaterialListFragment fragment = new MaterialListFragment();
        Bundle args = new Bundle();
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
        if (getArguments().containsKey(PRODUCT_ID)){
            items = inventoryService.getMaterialsFromProduct(getArguments().getLong(PRODUCT_ID));
            btnAdd.setImageResource(R.drawable.ic_note_add_white_24dp);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final long materialId = position;
                    final long productId = getArguments().getLong(PRODUCT_ID);
                    Util.editAmountDialog("Edit amount", null, getActivity(), getActivity().getLayoutInflater(),
                            new Util.UtilDialogEventListener() {
                                @Override
                                public void returnFloat(float arg) {
                                    inventoryService.updateRecipe(materialId, productId, arg);
                                }

                                @Override
                                public void returnString(String arg) {

                                }

                                @Override
                                public void returnLong(long arg) {

                                }
                            }).show();
                }
            });
        }else{
            items = inventoryService.getMaterials();
        }
        adapter = new MaterialListAdapter(getContext(),
                R.layout.row_material_item, items);
        listView.setAdapter(adapter);
    }

    @Override
    protected void add() {
        if (!getArguments().containsKey(PRODUCT_ID)) {
            Util.newMaterialDialog("New material", null, getActivity(), getActivity().getLayoutInflater(),
                    new Util.MaterialDialogEventListener() {
                        @Override
                        public Operation returnMaterial(Material material) {
                            Operation operation = inventoryService.addMaterial(material);
                            updateItemList(operation.getMaterials());
                            return operation;
                        }

                        @Override
                        public void moveToMaterial(long id) {
                            navigationService.moveToFragment(MaterialDetailFragment.newInstance(id));
                        }
                    }).show();
        }else{
            List<Material> materials = inventoryService.getMaterials();
            Util.newChooseMaterialDialog("Select material", null, getActivity(), getActivity().getLayoutInflater(),
                    new Util.UtilDialogEventListener() {
                        @Override
                        public void returnFloat(float arg) {

                        }

                        @Override
                        public void returnString(String arg) {

                        }

                        @Override
                        public void returnLong(long arg) {
                            inventoryService.updateRecipe(arg, getArguments().getLong(PRODUCT_ID) );
                        }
                    },materials).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        navigationService.setActionBarTitle("Materials");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        navigationService.moveToFragment(MaterialDetailFragment.newInstance(
                adapter.getItem(position).getId()));

    }

    @Override
    protected void updateItemList(List<?> items) {
        this.adapter.clear();
        adapter.addAll((List<Material>)items);
        listView.setAdapter(adapter);
    }
}
