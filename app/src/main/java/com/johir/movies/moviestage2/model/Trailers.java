package com.johir.movies.moviestage2.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mislam on 10/1/17.
 */

public class Trailers {
    @SerializedName("results")
    private List<Trailer> trailers = new ArrayList<>();
    public List<Trailer> getTrailers() {
        return trailers;
    }
}
