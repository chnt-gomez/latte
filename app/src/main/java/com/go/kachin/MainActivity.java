package com.go.kachin;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.go.kachin.database.DatabaseHelper;
import com.go.kachin.fragments.InventoryFragment;
import com.go.kachin.models.Material;

public class MainActivity extends AppCompatActivity implements InventoryFragment.InventoryService{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseHelper helper = new DatabaseHelper(this);
        init();

    }

    private void init() {
        addFragment(InventoryFragment.newInstance("", ""));
    }

    private void addFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_fragment_holder, fragment ,fragment.getTag()).commit();
    }

    @Override
    public void addMaterial(Material material) {
        //Here I should save the material
    }
}
