package com.go.kchin.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.go.kchin.R;
import com.go.kchin.interfaces.FragmentNavigationService;
import com.go.kchin.interfaces.InventoryService;

/**
 * Created by MAV1GA on 16/11/2016.
 */

public abstract class InventoryDetailFragment extends Fragment implements View.OnClickListener,
        Spinner.OnItemSelectedListener{

    abstract void save();

    protected long objectId;
    protected View fragmentView;
    protected FloatingActionButton btnSave;

    protected static final String LAYOUT_RES = "layout";
    protected static final String OBJECT_ID = "object_id";

    protected FragmentNavigationService navigationService;
    protected InventoryService inventoryService;

    protected View findViewById(int res){
        return fragmentView.findViewById(res);
    }

    protected void init(){
        btnSave = (FloatingActionButton)findViewById(R.id.btn_save);
        addToClickListener(btnSave);
        this.objectId = getArguments().getLong(OBJECT_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.fragmentView = inflater.inflate(getArguments().getInt(LAYOUT_RES), null);
        init();
        return fragmentView;
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
    public void onResume() {
        super.onResume();
        navigationService.hideActionBar();
    }

    protected void addToClickListener(View... params){
        for (View v : params){
            v.setOnClickListener(this);
        }
    }

    protected void addOnSpinnerSelectedListener(Spinner... args){
        for (Spinner s : args){
            s.setOnItemSelectedListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_save)
            save();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    protected void makeSnackBar(String message, String actionText, View.OnClickListener listener){
        Snackbar.make(fragmentView, message, Snackbar.LENGTH_LONG).setAction(actionText, listener).show();
    }
}
