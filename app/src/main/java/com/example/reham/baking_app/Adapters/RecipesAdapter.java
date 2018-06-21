package com.example.reham.baking_app.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.reham.baking_app.R;
import com.example.reham.baking_app.RecipeDetails;

import java.util.List;

import static com.example.reham.baking_app.Strings.IdText;

/**
 * Created by reham on 6/6/2018.
 */

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder> {
    Context mContext;
    List<String> recipesNames;

    public RecipesAdapter(Context context, List<String> names, List<Integer> ids) {
        mContext = context;
        recipesNames = names;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater;
        mInflater = LayoutInflater.from(mContext);
        View view = mInflater.inflate(R.layout.recipes_recycler, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String name = recipesNames.get(position);
        holder.recipeText.setText(name);
    }

    @Override
    public int getItemCount() {
        return recipesNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView recipeText;

        public ViewHolder(View itemView) {
            super(itemView);
            recipeText = itemView.findViewById(R.id.item_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, RecipeDetails.class);
            int id = getAdapterPosition();
            intent.putExtra(IdText, id);
            mContext.startActivity(intent);
        }
    }
}
