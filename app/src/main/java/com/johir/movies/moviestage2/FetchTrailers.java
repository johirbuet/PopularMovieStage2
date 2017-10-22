package com.johir.movies.moviestage2;

import android.os.AsyncTask;
import android.util.Log;

import com.johir.movies.moviestage2.adapter.TrailerAdapter;
import com.johir.movies.moviestage2.model.CONSTANTS;
import com.johir.movies.moviestage2.model.RetrofitService;
import com.johir.movies.moviestage2.model.Review;
import com.johir.movies.moviestage2.model.Reviews;
import com.johir.movies.moviestage2.model.Trailer;
import com.johir.movies.moviestage2.model.Trailers;

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

public class FetchTrailers extends AsyncTask<Long,Void,List<Trailer>> {
    TrailerAdapter mAdapter;
    List<Trailer> trailers;
    public FetchTrailers(ArrayList<Trailer> trailers, TrailerAdapter adapter) {
        this.trailers = trailers;
        mAdapter = adapter;
    }

    @Override
    protected void onPostExecute(List<Trailer> trailers) {
        super.onPostExecute(trailers);
        mAdapter.add(trailers);
    }

    @Override
    protected List<Trailer> doInBackground(Long... longs) {
        if (longs.length == 0) {
            return null;
        }
        long id = longs[0];
        Retrofit rf = new Retrofit.Builder().baseUrl(CONSTANTS.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitService rs = rf.create(RetrofitService.class);
        Call<Trailers> call = rs.getTrailers(id,CONSTANTS.API_KEY);
        try {
            Response<Trailers> res = call.execute();
            Trailers trailers = res.body();
            Log.d("Trailers",res.toString());
            return trailers.getTrailers();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("FetchTrailer",e.getMessage());
        }
        return null;
    }
}
