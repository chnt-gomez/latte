package com.go.kchin.presenter.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.go.kchin.R;
import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.model.database.Material;
import com.go.kchin.view.fragment.MaterialListFragment;

import java.util.List;

/**
 * Created by MAV1GA on 11/01/2017.
 */

public class MaterialsActivity extends BaseActivity implements MainMVP.MaterialsPresenterOps{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        attachFragment(MaterialListFragment.newInstance());
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
