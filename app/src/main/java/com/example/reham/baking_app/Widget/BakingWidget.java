package com.example.reham.baking_app.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.reham.baking_app.R;
import com.example.reham.baking_app.RecipeDetails;

import java.net.IDN;

/**
 * Implementation of App Widget functionality.
 */
public class BakingWidget extends AppWidgetProvider {

    private static final String TAG = BakingWidget.class.getSimpleName();

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int mWidgetsNumber = appWidgetIds.length;
        for (int i = 0; i < mWidgetsNumber; i++) {
            int appWidgetId = appWidgetIds[i];
            appWidgetManager.updateAppWidget(appWidgetId, getUpdatedRemoteViews(context));
        }

    }

    public static RemoteViews getUpdatedRemoteViews(Context context){
        String[] wigetData = WidgetDataOnSharedPreferences.getWidgetDataFromSharedPreferences(context);
        String id= wigetData[0];
        String ingredients = wigetData[1];
        Log.i("id", ""+id+ingredients);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget);
        views.setTextViewText(R.id.widget_text_view, ingredients);
        Intent intent = new Intent(context, RecipeDetails.class);
        int Id=Integer.parseInt(id);
        intent.putExtra("id",Id);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0 , intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_text_view, pendingIntent);
        return views;
    }
}

