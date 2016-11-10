package com.go.kchin.models;

import java.util.List;

/**
 * Created by MAV1GA on 10/11/2016.
 */

public class Recipe {

    private List<Material> materials;

    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }

    public void addMaterial(Material material){
        materials.add(material);
    }
}
