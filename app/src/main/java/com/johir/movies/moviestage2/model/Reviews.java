package com.johir.movies.moviestage2.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mislam on 10/1/17.
 */

public class Reviews {
    @SerializedName("results")
    private List<Review> reviews = new ArrayList<>();
    public List<Review> getReviews(){
        return reviews;
    }
}
