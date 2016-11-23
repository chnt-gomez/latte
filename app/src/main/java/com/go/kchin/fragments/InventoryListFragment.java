package com.go.kchin.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.go.kchin.R;
import com.go.kchin.interfaces.FragmentNavigationService;
import com.go.kchin.interfaces.InventoryService;

/**
 * Created by MAV1GA on 16/11/2016.
 */
public class InventoryListFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    protected FloatingActionButton btnAdd;

    protected ListView listView;
    protected View view;

    protected FragmentNavigationService navigationService;
    protected InventoryService inventoryService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_inventory, null);
        init();
        return view;
    }

    protected void init() {
        listView = (ListView)findViewById(R.id.lv_inventory);
        listView.setOnItemClickListener(this);
        btnAdd = (FloatingActionButton)findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        navigationService.showActionBar();
    }

    protected View findViewById(int res){
        return view.findViewById(res);
    }

    protected void addOnClickListener(View ... params){
        for (View v : params){
            v.setOnClickListener(this);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.navigationService = (FragmentNavigationService)context;
        this.inventoryService = (InventoryService)context;
    }

    @Override
    public void onDetach() {
        this.navigationService = null;
        this.inventoryService = null;
        super.onDetach();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    protected void add(){

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_add) {
            add();
            return;
        }
    }
}
