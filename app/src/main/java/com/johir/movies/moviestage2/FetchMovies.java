package com.johir.movies.moviestage2;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.johir.movies.moviestage2.adapter.MovieAdapter;
import com.johir.movies.moviestage2.model.CONSTANTS;
import com.johir.movies.moviestage2.model.Movies;
import com.johir.movies.moviestage2.model.Movie;
import com.johir.movies.moviestage2.model.RetrofitService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mislam on 10/2/17.
 */

public class FetchMovies extends AsyncTask<Void,Void,List<Movie>> {
    private String sortBy = CONSTANTS.POPULAR;
    private List<Movie> movies =new ArrayList<>();
    MovieAdapter mAdapter;
    RecyclerView mRecyclerview;
    public interface Finished {
        void complete();
    }
    Finished finished;
    public FetchMovies(String sortBy, MovieAdapter adapter,RecyclerView rv,Finished finished) {
        this.sortBy = sortBy;
        movies = new ArrayList<>();
        mAdapter = adapter;
        mRecyclerview =rv;
        this.finished  = finished;
    }
    public List<Movie> getMovies(){
        return movies;
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        super.onPostExecute(movies);
        this.movies = movies;

        mAdapter.add(movies);
        this.finished.complete();
        //mRecyclerview.setAdapter(mAdapter);
    }

    @Override
    protected List<Movie> doInBackground(Void... voids) {
        Retrofit rf = new Retrofit.Builder().baseUrl("http://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitService rs = rf.create(RetrofitService.class);
        Call<Movies> call = rs.getMovies(sortBy,CONSTANTS.API_KEY);

        try {

            Response<Movies> res = call.execute();
            Log.d("Movies",res.toString());
            Movies movies = res.body();
            return movies.getMovies();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("ERRROR FetchMovies",e.getMessage());
        }

        return null;
    }
}
