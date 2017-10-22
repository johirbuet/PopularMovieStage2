package com.johir.movies.moviestage2;

import android.os.AsyncTask;
import android.util.Log;

import com.johir.movies.moviestage2.adapter.ReviewAdapter;
import com.johir.movies.moviestage2.model.CONSTANTS;
import com.johir.movies.moviestage2.model.RetrofitService;
import com.johir.movies.moviestage2.model.Review;
import com.johir.movies.moviestage2.model.Reviews;

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

public class FetchReviews extends AsyncTask<Long,Void,List<Review>> {

    ReviewAdapter mAdapter;
    public FetchReviews(ArrayList<Review> reviews, ReviewAdapter adapter) {
        mAdapter = adapter;
    }
    @Override
    protected void onPostExecute(List<Review> reviews) {
        super.onPostExecute(reviews);
        mAdapter.add(reviews);
    }

    @Override
    protected List<Review> doInBackground(Long... params) {
        if(params.length == 0)
            return null;
        long id = params[0];
        Retrofit rf = new Retrofit.Builder().baseUrl(CONSTANTS.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitService rs = rf.create(RetrofitService.class);
        Call<Reviews> call = rs.getReviews(id,CONSTANTS.API_KEY);
        try {
            Response<Reviews> res = call.execute();
            Reviews reviews = res.body();
            return reviews.getReviews();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("FetchReviews",e.getMessage());
        }
        return null;
    }
}
