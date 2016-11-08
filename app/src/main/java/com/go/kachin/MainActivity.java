package com.go.kachin;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.go.kachin.database.DatabaseHelper;
import com.go.kachin.fragments.InventoryFragment;
import com.go.kachin.models.Material;

import java.util.List;

public class MainActivity extends AppCompatActivity implements InventoryFragment.InventoryService{

    DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }

    private void init() {
        helper = new DatabaseHelper(this);
        addFragment(InventoryFragment.newInstance("", ""));
    }

    private void addFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_fragment_holder, fragment ,fragment.getTag()).commit();
    }

    /*
    Materials and Inventory methods
     */

    @Override
    public void addMaterial(Material material) {
        long l = helper.addMaterial(material);
        Toast.makeText(this, "Added row " + l, Toast.LENGTH_SHORT).show();
    }

    @Override
    public List<Material> getMaterials() {
        return helper.getMaterials();
    }

    private void log(String message){
        Log.d(getClass().getSimpleName(), message);
    }
}
