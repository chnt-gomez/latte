package com.go.kchin.presenter.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.model.database.Material;

import java.util.List;

/**
 * Created by MAV1GA on 11/01/2017.
 */

public class MaterialsActivity extends BaseActivity implements MainMVP.MaterialsPresenterOps{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public List<Material> getAllMaterials() {
        return mModel.getAllMaterials();
    }

    @Override
    public void newMaterial(Material material) {
        mModel.addMaterial(material);
    }
}
