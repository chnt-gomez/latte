package com.go.kchin.fragments;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
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
    private Button btnMaterialBuy, btnSeeProducts;
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
        btnSeeProducts = (Button)findViewById(R.id.btn_related_products);

        material = inventoryService.getMaterial(objectId);

        edtMaterialName.setText(material.getMaterialName());
        spnMaterialUnit.setSelection(Util.getSelectionIndexFromString(material.getMaterialUnit()));
        btnMaterialBuy.setText(Util.fromFloat(material.getMaterialAmount()));

        txtMaterialPrice.setText(Util.fromFloat(material.getMaterialCost()));

        addToClickListener(btnMaterialBuy, btnSeeProducts);
        addOnSpinnerSelectedListener(spnMaterialUnit);

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

        Snackbar.make(fragmentView, "Material saved", Snackbar.LENGTH_SHORT).show();
    }

    private void buy(float amount){
        float newAmount = material.getMaterialAmount()+amount;
        btnMaterialBuy.setText(Util.fromFloat(newAmount));
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_material_amount:
                Util.newBuyItemDialog("Buy materials", null, getActivity(),
                        getActivity().getLayoutInflater(), this).show();
                break;
            case R.id.btn_related_products:
                navigationService.moveToFragment(ProductListFragment.newInstanceFromMaterial(material.getId()), true);
                break;
        }
    }

    @Override
    public void returnFloat(float amount) {
        buy(amount);
    }

    @Override
    public void returnString(String arg) {
        //Unused
    }

    @Override
    public void returnLong(long arg) {
        //Unused
    }


}
