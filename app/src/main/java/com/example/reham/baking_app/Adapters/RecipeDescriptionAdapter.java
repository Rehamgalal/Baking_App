package com.example.reham.baking_app.Adapters;

import android.content.Context;
import android.telecom.Call;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by reham on 6/6/2018.
 */

public class RecipeDescriptionAdapter extends BaseAdapter {
    Context mContext;
    List<String> Description;

    public RecipeDescriptionAdapter(Context context, List<String> description) {
        mContext = context;
        Description = description;
    }

    @Override
    public int getCount() {
        return Description.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        if (convertView == null) {
            // If the view is not recycled, this creates a new ImageView to hold an image
            textView = new TextView(mContext);
            textView.setPadding(8, 8, 8, 8);
            textView.setTextSize(20f);
        } else {
            textView = (TextView) convertView;

        }

        // Set the image resource and return the newly created ImageView
        textView.setText(Description.get(position));
        return textView;
    }
}

