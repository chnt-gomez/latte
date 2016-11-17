package com.go.kchin.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.go.kchin.R;
import com.go.kchin.models.Material;
import com.go.kchin.util.Util;


public class MaterialDetailFragment extends InventoryDetailFragment implements
        Util.UtilDialogEventListener{

    private EditText edtMaterialName;
    private Spinner spnMaterialUnit;
    private Button btnMaterialBuy;
    private TextView txtMaterialPrice;

    private Material material;


    public MaterialDetailFragment() {}

    public static MaterialDetailFragment newInstance(long material_id) {
        MaterialDetailFragment fragment = new MaterialDetailFragment();
        Bundle args = new Bundle();
        args.putLong(OBJECT_ID, material_id);
        args.putInt(LAYOUT_RES, R.layout.fragment_material_detail);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void init(){

        super.init();
        edtMaterialName = (EditText) findViewById(R.id.edt_product_name);
        spnMaterialUnit = (Spinner) findViewById(R.id.spn_material_unit);
        btnMaterialBuy = (Button) findViewById(R.id.btn_material_amount);
        txtMaterialPrice = (TextView) findViewById(R.id.txt_material_price);

        material = inventoryService.getMaterial(objectId);

        edtMaterialName.setText(material.getMaterialName());
        spnMaterialUnit.setSelection(Util.getSelectionIndexFromString(material.getMaterialUnit()));
        btnMaterialBuy.setText(Util.fromFloat(material.getMaterialAmount()));

        txtMaterialPrice.setText(Util.fromFloat(material.getMaterialCost()));

        addToClickListener(btnMaterialBuy);
        addTextWatcher(edtMaterialName);
        addOnSpinnerSelectedListener(spnMaterialUnit);

        if(!isUndoAvailable)
            btnSave.setImageResource(R.drawable.ic_done_all_white_24dp);

    }

    @Override
    protected void undo(){
        edtMaterialName.setText(material.getMaterialName());
        spnMaterialUnit.setSelection(Util.getSelectionIndexFromString(material.getMaterialUnit()));
        btnMaterialBuy.setText(Util.fromFloat(material.getMaterialAmount()));
        super.undo();
    }

    @Override
    protected void save(){
        Material newMaterial = new Material();

        String  newMaterialName = edtMaterialName.getText().toString();
        String  newMaterialUnit = spnMaterialUnit.getSelectedItem().toString();
        float newMaterialAmount = Util.toFloat(btnMaterialBuy.getText().toString());

        newMaterial.setMaterialName(newMaterialName);
        newMaterial.setMaterialUnit(newMaterialUnit);
        newMaterial.setMaterialAmount(newMaterialAmount);

        this.material.setMaterialName(newMaterialName);
        this.material.setMaterialUnit(newMaterialUnit);
        this.material.setMaterialAmount(newMaterialAmount);

        inventoryService.updateMaterial(objectId, newMaterial);
        super.save();

    }

    private void buy(float amount){
        float newAmount = material.getMaterialAmount()+amount;
        btnMaterialBuy.setText(Util.fromFloat(newAmount));
        isUndoAvailable(true);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_material_amount:
                Util.newBuyItemDialog("Buy materials", null, getActivity(),
                        getActivity().getLayoutInflater(), this).show();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position != Util.getSelectionIndexFromString(material.getMaterialUnit()))
            isUndoAvailable(true);
    }

    @Override
    public void returnFloat(float amount) {
        buy(amount);
    }

    @Override
    public void returnString(String arg) {
        //Unused
    }



}
