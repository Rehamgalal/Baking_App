package com.example.reham.baking_app;


import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.reham.baking_app.Adapters.RecipesAdapter;
import com.example.reham.baking_app.Retrofit.ApiClient;
import com.example.reham.baking_app.Retrofit.Recipes;
import com.example.reham.baking_app.Retrofit.Retrofit2;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Strings {
    RecyclerView RC;
    List<String> names;
    List<Integer> Ids;
    BakingIdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getIdlingResource();
        ButterKnife.bind(this);
        RC = findViewById(R.id.recycler_view);
        RC.setLayoutManager(new GridLayoutManager(this, 1));
        RC.setHasFixedSize(true);
        getData();
    }

    public void getData() {
        Retrofit2 apiService = ApiClient.getClient().create(Retrofit2.class);
        Call<List<Recipes>> call = apiService.getRecipe();
        call.enqueue(new Callback<List<Recipes>>() {
            @Override
            public void onResponse(Call<List<Recipes>> call, Response<List<Recipes>> response) {
                names = new ArrayList<>();
                Ids = new ArrayList<>();
                for (int i = 0; i < 4; i++) {
                    String name = response.body().get(i).getRecipeName();
                    names.add(name);
                }
                RecipesAdapter recipesAdapter = new RecipesAdapter(getApplicationContext(), names, Ids);
                RC.setAdapter(recipesAdapter);
                if (mIdlingResource != null) {
                    mIdlingResource.setIdleState(true);
                }
            }

            @Override
            public void onFailure(Call<List<Recipes>> call, Throwable t) {
                Toast.makeText(getBaseContext(), Failed, Toast.LENGTH_LONG).show();
            }
        });
    }

    @VisibleForTesting
    @Nullable
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new BakingIdlingResource();
            mIdlingResource.setIdleState(false);
        }
        return mIdlingResource;
    }
}
