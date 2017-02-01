package com.go.kchin.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.go.kchin.R;
import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.interfaces.RequiredDialogOps;
import com.go.kchin.model.database.Material;
import com.go.kchin.util.dialog.Dialogs;
import com.go.kchin.util.dialog.MeasurePicker;
import com.go.kchin.util.dialog.loader.Loader;
import com.go.kchin.util.dialog.number.Number;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by MAV1GA on 11/01/2017.
 */

public class MaterialDetailFragment extends BaseFragment {


    private Material material;
    private static final String MATERIAL_ID = "material_id";
    private MainMVP.MaterialPresenterOps mMaterialPresenter;

    @BindView(R.id.edt_material_name)
    EditText edtMaterialName;

    @BindView(R.id.spn_material_unit)
    Spinner spnMaterialUnit;

    @BindView(R.id.btn_material_amount)
    Button btnMaterialAmount;

    public static MaterialDetailFragment newInstance(long materialId){
        MaterialDetailFragment fragment = new MaterialDetailFragment();
        Bundle args = new Bundle();
        args.putInt(LAYOUT_RES_ID, R.layout.fragment_material_detail);
        args.putLong(MATERIAL_ID, materialId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mMaterialPresenter = (MainMVP.MaterialPresenterOps)context;
    }

    @Override
    public void onDetach() {
        this.mMaterialPresenter = null;
        super.onDetach();
    }

    @Override
    protected void init() {
        super.init();
        spnMaterialUnit.setAdapter(new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, MeasurePicker.getEntries(getResources())));
        reload();
    }

    @Override
    public void onLoad() {
        super.onLoad();
        material = mMaterialPresenter.getMaterial(getArguments().getLong(MATERIAL_ID));
    }

    @OnClick(R.id.btn_save)
    public void save(View view){
        material.materialName = edtMaterialName.getText().toString();
        material.materialMeasure = spnMaterialUnit.getSelectedItemPosition();
        mMaterialPresenter.save(material);
    }

    @Override
    public void onDoneLoading() {
        super.onDoneLoading();
        edtMaterialName.setText(material.materialName);
        btnMaterialAmount.setText(Number.floatToStringAsNumber(material.materialRemaining));
        spnMaterialUnit.setSelection(material.materialMeasure);

    }

    @OnClick(R.id.btn_material_amount)
    public void buyMore(View view){
        Dialogs.newFloatDialog(getContext(), getString(R.string.buy_more), null, new RequiredDialogOps.NewFloatOps() {
            @Override
            public void onNewFloat(float arg) {
                material.materialRemaining +=arg;
                btnMaterialAmount.setText(Number.floatToStringAsNumber(material.materialRemaining));
            }
        }).show();
    }

    private void reload(){
        Loader loader = new Loader(this);
        loader.execute();
    }

    @Override
    public void onPause() {
        save(null);
        super.onPause();

    }
}
