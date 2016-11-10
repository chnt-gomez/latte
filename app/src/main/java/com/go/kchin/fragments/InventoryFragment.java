package com.go.kchin.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.go.kchin.R;
import com.go.kchin.adapters.InventoryListAdapter;
import com.go.kchin.interfaces.FragmentNavigationService;
import com.go.kchin.interfaces.InventoryService;
import com.go.kchin.models.Material;
import com.go.kchin.util.Util;

public class InventoryFragment extends Fragment implements OnClickListener,
        Util.DialogEventListener {

    private View view;
    private FloatingActionButton btnAdd;
    private InventoryListAdapter adapter;
    private ListView listView;

    private InventoryService inventoryService;
    private FragmentNavigationService navigationService;

    public InventoryFragment() {
        // Required empty public constructor
    }

    public static InventoryFragment newInstance() {
        InventoryFragment fragment = new InventoryFragment();
        return fragment;
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        navigationService.showActionBar();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_inventory, container, false);
        init();
        return view;
    }

    private void init() {
        btnAdd = (FloatingActionButton) view.findViewById(R.id.btn_add);
        addToListener(btnAdd);
        if(inventoryService.getMaterials() != null) {
            adapter = new InventoryListAdapter(getContext(),
                    R.layout.row_material_item, inventoryService.getMaterials());
            listView = (ListView)view.findViewById(R.id.lv_materials_list_view);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    inventoryService.moveToFragment(MaterialDetailFragment.newInstance(adapter.getItem(position).getId()));
                }
            });

            listView.setAdapter(adapter);
        }
    }

    private void addToListener(View... params){
        for (View v : params){
            v.setOnClickListener(this);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        inventoryService = (InventoryService)context;
        navigationService = (FragmentNavigationService)context;
        if (navigationService != null)
            navigationService.setActionBarTitle("Inventory");

    }

    @Override
    public void onDetach() {
        super.onDetach();
        inventoryService = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add:
                Util.newMaterialDialog("New material", null, getActivity(),
                        getActivity().getLayoutInflater(), this).show();
        }
    }

    @Override
    public void returnMaterial(Material material) {
        if(material != null) {
            inventoryService.addMaterial(material);
        }
    }

    @Override
    public void returnFloat(float amount) {


    }

    private void updateList() {
        adapter.setItems(inventoryService.getMaterials());
        listView.setAdapter(adapter);
    }
}
