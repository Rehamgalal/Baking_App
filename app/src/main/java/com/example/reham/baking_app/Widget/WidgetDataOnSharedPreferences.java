package com.example.reham.baking_app.Widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by reham on 6/14/2018.
 */

public class WidgetDataOnSharedPreferences {

    private static final String ID = "package com.example.android.bakingapp.data.id";
    private static final String INGREDIENTS_KEY = "package com.example.android.bakingapp.data.ingredients";
    private static final int DEFAULT_ID= 0;
    private static final String DEFAULT_INGREDIENTS = "Ingredients Name";

    private WidgetDataOnSharedPreferences(){

    }

    public static void saveWidgetDataOnSharedPreferences(Context context, int id, String ingredients){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(ID,id);
        editor.putString(INGREDIENTS_KEY, ingredients);
        editor.apply();
    }


    public static String[] getWidgetDataFromSharedPreferences(Context context){
        String[] widgetData = new String[2];

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        int id = sharedPref.getInt(ID, DEFAULT_ID);
        String ingredients = sharedPref. getString(INGREDIENTS_KEY, DEFAULT_INGREDIENTS);

        widgetData[0]= String.valueOf(id);
        widgetData[1]= ingredients;

        return widgetData;
    }
}