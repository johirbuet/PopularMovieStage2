package com.johir.movies.moviestage2.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by mislam on 10/1/17.
 */

// This Service will be used by retrofit
public interface RetrofitService {
    @GET("movie/{sort_by}")
    Call<Movies> getMovies(@Path("sort_by") String sortBy, @Query("api_key") String key);
    @GET("movie/{id}/reviews")
    Call<Reviews> getReviews(@Path("id") long id, @Query("api_key") String key);
    @GET("movie/{id}/videos")
    Call<Trailers> getTrailers(@Path("id") long id, @Query("api_key") String key);


}
