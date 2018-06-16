package com.example.reham.baking_app;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.reham.baking_app.Fragments.RecipeDescriptionFragment;
import com.example.reham.baking_app.Widget.BakingWidget;
import com.example.reham.baking_app.Widget.WidgetDataOnSharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetails extends AppCompatActivity implements RecipeDescriptionFragment.OnStepClickListener {
    int id;
    public static final String SHARED_PREFS_KEY = "SHARED_PREFS_KEY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        id = i.getIntExtra("id", 1);
        setContentView(R.layout.activity_recipe_details);


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
        Intent intent=new Intent(this,StepActivity.class);
        if (position>0){
        intent.putExtra("position",position);
        intent.putStringArrayListExtra("description",(ArrayList<String>) longDescription);
        intent.putStringArrayListExtra("videoURL",(ArrayList<String>) videoURL);
        intent.putStringArrayListExtra("thumbnailURL",(ArrayList<String>) thumbnailURL);
        startActivity(intent);}
        else {}
    }
}
