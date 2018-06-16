package com.example.reham.baking_app.Retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by reham on 6/6/2018.
 */

public class Recipes {
    @SerializedName("id")
    int id;
    @SerializedName("name")
    String recipeName;
    @SerializedName("ingredients")
    List<Ingredients> Ingredients;
    @SerializedName("steps")
    List<RecipeDetails> Steps;
    @SerializedName("servings")
    int Servings;
    @SerializedName("image")
    String Image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setIngredients(List<Ingredients> ingredients) {
        Ingredients = ingredients;
    }

    public void setSteps(List<RecipeDetails> steps) {
        Steps = steps;
    }

    public List<Ingredients> getIngredients() {
        return Ingredients;
    }

    public List<RecipeDetails> getSteps() {
        return Steps;
    }

    public int getServings() {
        return Servings;
    }

    public void setServings(int servings) {
        Servings = servings;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
