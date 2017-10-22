package com.johir.movies.moviestage2.model;

import android.graphics.*;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mislam on 10/1/17.
 */

public class Movies {
    @SerializedName("results")
    private List<Movie> movies = new ArrayList<>();
    public List<Movie> getMovies(){
        return movies;
    }
}
