package com.example.reham.baking_app.Retrofit;

import android.telecom.Call;

import com.google.gson.annotations.SerializedName;

/**
 * Created by reham on 6/6/2018.
 */

public class RecipeDetails {
    @SerializedName("id")
    int Id;
    @SerializedName("shortDescription")
    String shortDescription;
    @SerializedName("description")
    String Description;
    @SerializedName("videoURL")
    String videoURL;
    @SerializedName("thumbnailURL")
    String thmbnaiURL;

    public RecipeDetails(int id, String shortdescription, String description, String videoUrl, String thmbnaiUrl) {
        Id = id;
        shortDescription = shortdescription;
        Description = description;
        videoURL = videoUrl;
        thmbnaiURL = thmbnaiUrl;

    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThmbnaiURL() {
        return thmbnaiURL;
    }

    public void setThmbnaiURL(String thmbnaiURL) {
        this.thmbnaiURL = thmbnaiURL;
    }

}
