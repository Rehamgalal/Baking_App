package com.example.reham.baking_app;

import android.app.FragmentManager;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.reham.baking_app.Fragments.RecipeDescriptionFragment;
import com.example.reham.baking_app.Fragments.StepFragment;
import com.example.reham.baking_app.Widget.BakingWidget;
import com.example.reham.baking_app.Widget.WidgetDataOnSharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetails extends AppCompatActivity implements RecipeDescriptionFragment.OnStepClickListener {
    int id;
    boolean mTwoPane;
    String description;
    String thumbnailUrl;
    String videoUrl;
    android.support.v4.app.FragmentManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        id = i.getIntExtra("id", 1);
        setContentView(R.layout.activity_recipe_details);
        if(findViewById(R.id.recipe_layout)!=null){
            mTwoPane=true;
            if(savedInstanceState==null){
               Bundle bundle= new Bundle();
                bundle.putString("description",description);
                if (videoUrl!=null&&!videoUrl.equals("")){
                    bundle.putString("videoURL",videoUrl);
                }else if (thumbnailUrl!=null&&!thumbnailUrl.equals("")){
                    bundle.putString("thumbnail",thumbnailUrl);}
                StepFragment stepFragment= new StepFragment();
                manager= getSupportFragmentManager();
                stepFragment.setArguments(bundle);
                manager.beginTransaction().add(R.id.detail_view,stepFragment).commit();


            }
        }else {mTwoPane=false;}
    }

    public int getId() {
        Log.i("id", id + "");
        return id;
    }

    @Override
    public void onStepSelected(int position, List<String> longDescription, List<String> videoURL, List<String> thumbnailURL) {
       WidgetDataOnSharedPreferences.saveWidgetDataOnSharedPreferences(this,id,longDescription.get(0));
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int ids[] = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(),BakingWidget.class));
        BakingWidget bakingWidget= new BakingWidget();
        bakingWidget.onUpdate(this,appWidgetManager,ids);
        //Now update all widgets
        BakingWidget.getUpdatedRemoteViews(this);
        if (mTwoPane){
            if (position>0){
                int i = position-1;
            description=longDescription.get(position);
            videoUrl=videoURL.get(i);
            thumbnailUrl=thumbnailURL.get(i);
            Bundle b= new Bundle();
                b.putString("description",description);
                if (videoUrl!=null&&!videoUrl.equals("")){
                    b.putString("videoURL",videoUrl);
                }else if (thumbnailUrl!=null&&!thumbnailUrl.equals("")){
                    b.putString("thumbnail",thumbnailUrl);}
            StepFragment stepFragment= new StepFragment();
            stepFragment.setArguments(b);
            manager.beginTransaction().replace(R.id.detail_view,stepFragment).commit();}
        }
       else if (!mTwoPane){
        Intent intent=new Intent(this,StepActivity.class);
        if (position>0){
        intent.putExtra("position",position);
        intent.putStringArrayListExtra("description",(ArrayList<String>) longDescription);
        intent.putStringArrayListExtra("videoURL",(ArrayList<String>) videoURL);
        intent.putStringArrayListExtra("thumbnailURL",(ArrayList<String>) thumbnailURL);
        startActivity(intent);}
        else {}
    }
}}
