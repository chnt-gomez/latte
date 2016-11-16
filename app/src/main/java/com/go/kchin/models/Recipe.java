package com.go.kchin.models;

import java.util.List;

/**
 * Created by MAV1GA on 10/11/2016.
 */

public class Recipe {

    private long productId;
    private List<Ingredient> recipe;

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public List<Ingredient> getRecipe() {
        return recipe;
    }

    public void setRecipe(List<Ingredient> recipe) {
        this.recipe = recipe;
    }
}
