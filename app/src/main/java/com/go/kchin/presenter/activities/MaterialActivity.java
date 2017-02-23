package com.go.kchin.presenter.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.go.kchin.R;
import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.model.database.Material;
import com.go.kchin.view.fragment.MaterialDetailFragment;

/**
 * Created by MAV1GA on 11/01/2017.
 */

public class MaterialActivity extends BaseActivity implements MainMVP.MaterialPresenterOps {

    public static final String MATERIAL_ID = "material_id";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_material_detail);
    }

    @Override
    protected void init() {
        super.init();
        attachFragment(MaterialDetailFragment.
                newInstance(getIntent().getExtras().getLong(MATERIAL_ID)));
    }

    @Override
    public Material getMaterial(long materialId) {
        return mModel.getMaterialFromId(materialId);
    }

    @Override
    public void save(Material material) {
        mModel.updateMaterial(material);
    }

    @Override
    public void buyMore(long materialId, float materialAmount, float purchaseCost) {
        mModel.buyMaterial(materialId, materialAmount, purchaseCost);
    }
}
