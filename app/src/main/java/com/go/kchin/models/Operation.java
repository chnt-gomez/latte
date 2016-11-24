package com.go.kchin.models;

import java.util.List;

/**
 * Created by MAV1GA on 24/11/2016.
 */

public class Operation {

    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    private List<Material> materials;
    private List<Product> products;
    private List<Department> departments;
    private long insertionId;

    public Operation(){}

    public long getInsertionId() {
        return insertionId;
    }

    public void setInsertionId(long insertionId) {
        this.insertionId = insertionId;
    }
}
