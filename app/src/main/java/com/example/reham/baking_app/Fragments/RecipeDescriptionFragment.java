package com.example.reham.baking_app.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.reham.baking_app.R;
import com.example.reham.baking_app.Adapters.RecipeDescriptionAdapter;
import com.example.reham.baking_app.Retrofit.*;
import com.example.reham.baking_app.Retrofit.RecipeDetails;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.reham.baking_app.Strings.Failed;
import static com.example.reham.baking_app.Strings.NO_VIDEO;

/**
 * Created by reham on 6/6/2018.
 */

public class RecipeDescriptionFragment extends Fragment {
    // Define a new interface OnImageClickListener that triggers a callback in the host activity
    OnStepClickListener mCallback;
    GridView gridView;
    List<String> mSteps;
    List<String> longDescription;
    List<String> videoURL;
    List<String> thumbnailURL;
    int id;

    // OnImageClickListener interface, calls a method in the host activity named onImageSelected
    public interface OnStepClickListener {
        void onStepSelected(int position, List<String> longDescription, List<String> videoURL, List<String> thumbnailURL);
    }

    // Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallback = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }


    // Mandatory empty constructor
    public RecipeDescriptionFragment() {

    }

    // Inflates the GridView of all AndroidMe images
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_body, container, false);
        // Get a reference to the GridView in the fragment_master_list xml layout file
        gridView = rootView.findViewById(R.id.text);
        com.example.reham.baking_app.RecipeDetails recipeDetails = (com.example.reham.baking_app.RecipeDetails) getActivity();
        id = recipeDetails.getId();
        Log.i("id", id + "  ");
        Retrofit2 apiService = ApiClient.getClient().create(Retrofit2.class);
        Call<List<Recipes>> call = apiService.getRecipe();
        call.enqueue(new Callback<List<Recipes>>() {
            @Override
            public void onResponse(Call<List<Recipes>> call, Response<List<Recipes>> response) {
                DecimalFormat format = new DecimalFormat();
                mSteps = new ArrayList<>();
                longDescription = new ArrayList<>();
                videoURL = new ArrayList<>();
                thumbnailURL = new ArrayList<>();
                StringBuilder sb = new StringBuilder();
                List<Ingredients> ingredients = response.body().get(id).getIngredients();
                for (int i = 0; i < ingredients.size(); i++) {
                    double q = ingredients.get(i).getQuantity();
                    String quantity = String.valueOf(format.format(q));
                    String measure = ingredients.get(i).getMeasure();
                    String ingredient = ingredients.get(i).getIngredient();
                    sb.append(quantity + " ");
                    sb.append(measure + " of ");
                    sb.append(ingredient + "\n");
                }
                mSteps.add(sb.toString());
                longDescription.add(sb.toString());
                List<RecipeDetails> steps = response.body().get(id).getSteps();
                for (int o = 0; o < steps.size(); o++) {
                    String step = steps.get(o).getShortDescription();
                    String longDes = steps.get(o).getDescription();
                    String video = steps.get(o).getVideoURL();
                    if(steps.get(o).getVideoURL().equals("")){
                        video=NO_VIDEO;
                    }
                    String thumbnail = steps.get(o).getThmbnaiURL();
                    mSteps.add(step);
                    longDescription.add(longDes);
                    videoURL.add(video);
                    thumbnailURL.add(thumbnail);
                }
                // Create the adapter
                // This adapter takes in the context and an ArrayList of ALL the image resources to display
                RecipeDescriptionAdapter mAdapter = new RecipeDescriptionAdapter(getActivity(), mSteps);
                // Set the adapter on the GridView
                gridView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<List<Recipes>> call, Throwable t) {
                Toast.makeText(getActivity(), Failed, Toast.LENGTH_LONG).show();
            }
        });


        // Set a click listener on the gridView and trigger the callback onImageSelected when an item is clicked
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Trigger the callback method and pass in the position that was clicked
                mCallback.onStepSelected(position, longDescription, videoURL, thumbnailURL);
            }
        });

        // Return the root view
        return rootView;
    }

}
