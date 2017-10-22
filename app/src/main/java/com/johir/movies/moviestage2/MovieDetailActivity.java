package com.johir.movies.moviestage2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.johir.movies.moviestage2.adapter.ReviewAdapter;
import com.johir.movies.moviestage2.adapter.TrailerAdapter;
import com.johir.movies.moviestage2.data.MoviesContract;
import com.johir.movies.moviestage2.model.Review;
import com.johir.movies.moviestage2.model.Trailer;
import com.johir.movies.moviestage2.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MovieDetailActivity extends AppCompatActivity implements TrailerAdapter.Callback,ReviewAdapter.Callback {

    RecyclerView rvReviews;
    RecyclerView rvTrailers;
    ImageView preview;
    TrailerAdapter tAdapter;
    ReviewAdapter rAdapater;
    TextView movieTitle;
    TextView movieRelease;
    TextView movieOverview;
    TextView userRating;
    Button bWatchTrailer;
    Button bMarkfavorite;
    Button bRemoveFav;
    List<ImageView> ratings;
    Movie movie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        rvReviews = (RecyclerView) findViewById(R.id.rv_reviews);
        rvTrailers = (RecyclerView) findViewById(R.id.rv_trailers);
        preview = (ImageView) findViewById(R.id.im_detail);
        tAdapter = new TrailerAdapter(new ArrayList<Trailer>(),this);
        rAdapater = new ReviewAdapter(new ArrayList<Review>(),this);
        movieTitle = (TextView) findViewById(R.id.tv_title);
        movieRelease = (TextView) findViewById(R.id.tv_rdate);
        movieOverview = (TextView) findViewById(R.id.tv_overview);
        userRating = (TextView) findViewById(R.id.tv_urate);
        bWatchTrailer = (Button) findViewById(R.id.btn_watc_trailer);
        bMarkfavorite = (Button) findViewById(R.id.button_mark_as_favorite);
        bRemoveFav = (Button) findViewById(R.id.button_remove_from_favorites);
        ratings = new ArrayList<>();
        ratings.add((ImageView) findViewById(R.id.rating_first_star));
        ratings.add((ImageView) findViewById(R.id.rating_second_star));
        ratings.add((ImageView) findViewById(R.id.rating_third_star));
        ratings.add((ImageView) findViewById(R.id.rating_fourth_star));
        ratings.add((ImageView) findViewById(R.id.rating_fifth_star));
        movie = getIntent().getExtras().getParcelable("movie");
        Picasso.with(this)
                .load(movie.getmPosterPath())
                .into(preview);
        movieTitle.setText(movie.getmTitle());
        movieRelease.setText(movie.getmReleaseDate());
        movieOverview.setText(movie.getmOverView());
        userRating.setText(movie.getmRating());
        if(movie.getmRating() != null ){
            float rate = Float.parseFloat(movie.getmRating())/2;
            int intpart = (int) rate;
            for(int i=0;i<intpart;i++) {
                ratings.get(i).setImageResource(R.drawable.ic_start_black_24dp);
            }
            if(Math.round(rate) > intpart) {
                ratings.get(intpart).setImageResource(R.drawable.ic_start_black_24dp);
            }
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        rvTrailers.setLayoutManager(layoutManager);
        tAdapter = new TrailerAdapter(new ArrayList<Trailer>(),this);
        rvTrailers.setAdapter(tAdapter);
        rvTrailers.setNestedScrollingEnabled(false);

        rvReviews.setLayoutManager(new LinearLayoutManager(this));
        rAdapater = new ReviewAdapter(new ArrayList<Review>(),this);
        rvReviews.setAdapter(rAdapater);
        fetchTrailers(movie);
        fetchReviews(movie);
        updateFav();

    }
    private void updateFav(){
        new AsyncTask<Void,Void,Boolean>(){

            @Override
            protected Boolean doInBackground(Void... voids) {
                return isFavorite();
            }

            @Override
            protected void onPostExecute(Boolean isFavorite) {
                if(isFavorite) {
                    bRemoveFav.setVisibility(View.VISIBLE);
                    bMarkfavorite.setVisibility(View.GONE);
                } else {
                    bRemoveFav.setVisibility(View.GONE);
                    bMarkfavorite.setVisibility(View.VISIBLE);
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        bMarkfavorite.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        markAsFavorite();
                    }
                }
        );
        bRemoveFav.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        removeFav();
                    }
                }
        );
        bWatchTrailer.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(tAdapter.getItemCount() > 0 ){
                            watch(tAdapter.getTrailers().get(0),0);
                        }

                    }
                }
        );
    }
    public void markAsFavorite(){
        new AsyncTask<Void,Void,Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                if(!isFavorite()){
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(MoviesContract.Entry.COLUMN_MOVIE_ID,movie.getmId());
                    contentValues.put(MoviesContract.Entry.COLUMN_MOVIE_TITLE,movie.getmTitle());
                    contentValues.put(MoviesContract.Entry.COLUMN_MOVIE_POSTER_PATH,movie.getmPosterPath());
                    contentValues.put(MoviesContract.Entry.COLUMN_MOVIE_OVERVIEW,movie.getmOverView());
                    contentValues.put(MoviesContract.Entry.COLUMN_MOVIE_VOTE_AVERAGE,movie.getmRating());
                    contentValues.put(MoviesContract.Entry.COLUMN_MOVIE_RELEASE_DATE,movie.getmReleaseDate());
                    contentValues.put(MoviesContract.Entry.COLUMN_MOVIE_BACKDROP_PATH,movie.getmBackdropPath());
                    getBaseContext().getContentResolver().insert(MoviesContract.Entry.CONTENT_URI,contentValues);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                updateFav();
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
    public void removeFav(){
        new AsyncTask<Void,Void,Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                if(isFavorite()){
                    getBaseContext().getContentResolver().delete(MoviesContract.Entry.CONTENT_URI,
                            MoviesContract.Entry.COLUMN_MOVIE_ID+ " = "+movie.getmId(),null
                            );
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                updateFav();
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private boolean isFavorite(){
        Cursor movieCursor = getBaseContext().getContentResolver().query(
                MoviesContract.Entry.CONTENT_URI,
                new String[] {MoviesContract.Entry.COLUMN_MOVIE_ID},
                MoviesContract.Entry.COLUMN_MOVIE_ID+" = "+movie.getmId(),
                null,
                null
        );
        if(movieCursor != null && movieCursor.moveToFirst()){
            movieCursor.close();
            return true;
        }
        return false;
    }

    public void fetchTrailers(Movie movie){
       FetchTrailers ft = new FetchTrailers(new ArrayList<Trailer>(),tAdapter);
        ft.execute(movie.getmId());
    }
    public void fetchReviews(Movie movie){
        FetchReviews fr = new FetchReviews(new ArrayList<Review>(),rAdapater);
        fr.execute(movie.getmId());
    }


    @Override
    public void read(Review review, int position) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(review.getmUrl())));
    }

    @Override
    public void watch(Trailer trailer, int position) {
        startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(trailer.getTrailerURL())));
    }
}
