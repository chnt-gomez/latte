package com.go.kachin.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;

import com.go.kachin.InventoryActivity;
import com.go.kachin.R;
import com.go.kachin.adapters.InventoryListAdapter;
import com.go.kachin.models.Material;
import com.go.kachin.util.Util;

import java.util.List;

public class InventoryFragment extends Fragment implements OnClickListener,
        Util.NewMaterialInterface{

    private View view;
    private FloatingActionButton btnAdd;
    private InventoryListAdapter adapter;
    ListView listView;

    private InventoryService mListener;

    public InventoryFragment() {
        // Required empty public constructor
    }

    public static InventoryFragment newInstance() {
        InventoryFragment fragment = new InventoryFragment();
        return fragment;
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
        if(mListener.getMaterials() != null) {
            adapter = new InventoryListAdapter(getContext(),
                    R.layout.row_material_item, mListener.getMaterials());
            listView = (ListView)view.findViewById(R.id.lv_materials_list_view);
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
        if (context instanceof InventoryService) {
            mListener = (InventoryService) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement InventoryService");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
            mListener.addMaterial(material);
        }
    }


    private void updateList() {
        adapter.setItems(mListener.getMaterials());
        listView.setAdapter(adapter);
    }

    public interface InventoryService {
        void addMaterial(Material material);
        List<Material> getMaterials();
    }
}
