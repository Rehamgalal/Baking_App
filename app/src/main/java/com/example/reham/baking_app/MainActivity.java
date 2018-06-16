package com.example.reham.baking_app;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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

public class MainActivity extends AppCompatActivity {
    RecyclerView RC;
    List<String> names;
    List<Integer> Ids;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        RC = findViewById(R.id.recyler_view);
        RC.setLayoutManager(new GridLayoutManager(this,1));
        RC.setHasFixedSize(true);
        getData();
    }
    public void getData(){
        Retrofit2 apiService=ApiClient.getClient().create(Retrofit2.class);
        Call<List<Recipes>> call=  apiService.getRecipe();
        call.enqueue(new Callback<List<Recipes>>() {
            @Override
            public void onResponse(Call<List<Recipes>> call, Response<List<Recipes>> response) {
                names=new ArrayList<>();
                Ids=new ArrayList<>();
                for (int i = 0 ;i<4;i++)
                {  String name = response.body().get(i).getRecipeName();
                names.add(name);
                }
                Log.e("thelist",""+names.toString());
                RecipesAdapter recipesAdapter= new RecipesAdapter(getApplicationContext(),names,Ids);
                RC.setAdapter(recipesAdapter);
            }

            @Override
            public void onFailure(Call<List<Recipes>> call, Throwable t) {
                Log.e("onFailure",t.getMessage());
            }
        });
    }
}
