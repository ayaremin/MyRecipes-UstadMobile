package com.eminayar.myrecipes.models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by EminAyar on 21.06.2018.
 */

public class Recipe extends RealmObject {

    @PrimaryKey
    private int recipeId;
    private String name;
    private String description;
    private long createdAt;
    private boolean favourite;
    private RealmList<String> ingredients = new RealmList<>();

    public Recipe() {
    }

    public Recipe(String name, String description, long createdAt, RealmList<String> ingredients) {
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.ingredients = ingredients;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setId(int id) {
        this.recipeId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public RealmList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(RealmList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public String getIngredientsAsString() {
        StringBuilder sb = new StringBuilder();
        for (String ingredient : ingredients) {
            sb.append(ingredient);
            sb.append(", ");
        }
        return sb.toString();
    }
}
