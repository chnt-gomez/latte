package com.go.kchin.view.fragment;

import android.content.Context;
import android.os.Bundle;

import com.go.kchin.R;
import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.model.database.Material;

/**
 * Created by MAV1GA on 11/01/2017.
 */

public class MaterialDetailFragment extends BaseFragment {


    private Material material;
    private static final String MATERIAL_ID = "material_id";
    private MainMVP.MaterialPresenterOps mMaterialPresenter;

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

    }

    private void reload(){
        material = mMaterialPresenter.getMaterial(getArguments().getLong(MATERIAL_ID));
        if(material != null){

        }
    }
}
