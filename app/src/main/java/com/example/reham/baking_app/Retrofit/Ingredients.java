package com.example.reham.baking_app.Retrofit;

import android.content.Intent;

import com.google.gson.annotations.SerializedName;

/**
 * Created by reham on 6/6/2018.
 */

public class Ingredients {
    @SerializedName("quantity")
    double Quantity;
    @SerializedName("measure")
    String Measure;
    @SerializedName("ingredient")
    String Ingredient;
    public Ingredients(double quantity,String measure,String ingredient){
        Quantity=quantity;
        Measure=measure;
        Ingredient=ingredient;
    }
    public double getQuantity() {
        return Quantity;
    }

    public void setQuantity(double quantity) {
        Quantity = quantity;
    }

    public String getMeasure() {
        return Measure;
    }

    public void setMeasure(String measure) {
        Measure = measure;
    }

    public String getIngredient() {
        return Ingredient;
    }

    public void setIngredient(String ingredient) {
        Ingredient = ingredient;
    }

}
