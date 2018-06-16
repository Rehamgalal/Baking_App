package com.example.reham.baking_app.Retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;

/**
 * Created by reham on 6/6/2018.
 */

public interface Retrofit2 {
@GET("/topher/2017/May/59121517_baking/baking.json")
Call<List<Recipes>> getRecipe();

}
