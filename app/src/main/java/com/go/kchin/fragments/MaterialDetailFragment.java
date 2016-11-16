package com.go.kchin.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.go.kchin.R;
import com.go.kchin.interfaces.FragmentNavigationService;
import com.go.kchin.interfaces.InventoryService;
import com.go.kchin.models.Material;
import com.go.kchin.util.Util;


public class MaterialDetailFragment extends Fragment implements View.OnClickListener, TextWatcher,
        Util.UtilDialogEventListener {

    private static final String MATERIAL_ID = "materialId";

    // TODO: Rename and change types of parameters
    private long materialId;
    private View view;

    private boolean isRestoreAvailable = false;

    private EditText edtMaterialName, edtMaterialUnit;
    private Button btnMaterialBuy;
    private FloatingActionButton btnSave, btnRestore;
    private TextView txtMaterialPrice;

    private Material material;

    private InventoryService inventoryService;
    private FragmentNavigationService navigationService;

    public MaterialDetailFragment() {}

    public static MaterialDetailFragment newInstance(long material_id) {
        MaterialDetailFragment fragment = new MaterialDetailFragment();
        Bundle args = new Bundle();
        args.putLong(MATERIAL_ID, material_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.inventoryService = (InventoryService)context;
        this.navigationService = (FragmentNavigationService)context;
        //if (navigationService!= null);
            //navigationService.hideActionBar();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            materialId = getArguments().getLong(MATERIAL_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_material_detail, container, false);
        init();
        return view;
    }

    private void addToClickListener(View... params){
        for (View v : params){
            v.setOnClickListener(this);
        }
    }

    private void addToTextWatch(EditText... params){
        for (EditText v : params){
            v.addTextChangedListener(this);
        }
    }

    private void init(){

        btnSave = (FloatingActionButton) view.findViewById(R.id.btn_save);
        btnRestore = (FloatingActionButton) view.findViewById(R.id.btn_undo);

        edtMaterialName = (EditText) view.findViewById(R.id.edt_product_name);
        edtMaterialUnit = (EditText) view.findViewById(R.id.edt_material_unit);
        btnMaterialBuy = (Button) view.findViewById(R.id.btn_material_amount);
        txtMaterialPrice = (TextView)view.findViewById(R.id.txt_material_price);

        material = inventoryService.getMaterial(materialId);

        edtMaterialName.setText(material.getMaterialName());
        edtMaterialUnit.setText(material.getMaterialUnit());
        btnMaterialBuy.setText(Util.fromFloat(material.getMaterialAmount()));

        txtMaterialPrice.setText(Util.fromFloat(material.getMaterialCost()));

        addToClickListener(btnSave, btnRestore, btnMaterialBuy);
        addToTextWatch(edtMaterialName, edtMaterialUnit);

        if(!isRestoreAvailable)
            btnSave.setImageResource(R.drawable.ic_done_all_white_24dp);

    }

    private void restore(){
        edtMaterialName.setText(material.getMaterialName());
        edtMaterialUnit.setText(material.getMaterialUnit());
        btnMaterialBuy.setText(Util.fromFloat(material.getMaterialAmount()));
        isRestoreAvailable(false);
    }

    private void isRestoreAvailable(boolean isAvailable){
        isRestoreAvailable = isAvailable;
        if (isAvailable) {
            btnRestore.setVisibility(FloatingActionButton.VISIBLE);
            btnSave.setImageResource(R.drawable.ic_done_white_24dp);

        }
        else {
            btnRestore.setVisibility(FloatingActionButton.GONE);
            btnSave.setImageResource(R.drawable.ic_done_all_white_24dp);

        }
    }

    private void saveChanges(){
        Material newMaterial = new Material();

        String  newMaterialName = edtMaterialName.getText().toString();
        String  newMaterialUnit = edtMaterialUnit.getText().toString();
        float newMaterialAmount = Util.toFloat(btnMaterialBuy.getText().toString());

        newMaterial.setMaterialName(newMaterialName);
        newMaterial.setMaterialUnit(newMaterialUnit);
        newMaterial.setMaterialAmount(newMaterialAmount);

        this.material.setMaterialName(newMaterialName);
        this.material.setMaterialUnit(newMaterialUnit);
        this.material.setMaterialAmount(newMaterialAmount);

        inventoryService.updateMaterial(materialId, newMaterial);

        isRestoreAvailable(false);
    }

    private void buy(float amount){
        float newAmount = material.getMaterialAmount()+amount;
        btnMaterialBuy.setText(Util.fromFloat(newAmount));
        isRestoreAvailable(true);
    }

    @Override
    public void onDetach() {
        this.inventoryService = null;
        this.navigationService = null;
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_save:
                if(isRestoreAvailable)
                    saveChanges();
                else
                    getActivity().onBackPressed();
                break;
            case R.id.btn_undo:
                restore();
                break;
            case R.id.btn_material_amount:
                Util.newBuyMaterialDialog("Buy materials", null, getActivity(),
                        getActivity().getLayoutInflater(), this).show();
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        isRestoreAvailable(true);
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
