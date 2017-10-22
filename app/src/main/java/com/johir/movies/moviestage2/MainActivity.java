package com.johir.movies.moviestage2;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.johir.movies.moviestage2.adapter.MovieAdapter;
import com.johir.movies.moviestage2.data.MoviesContract;
import com.johir.movies.moviestage2.model.CONSTANTS;
import com.johir.movies.moviestage2.model.Movie;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.Callback,
        LoaderManager.LoaderCallbacks<Cursor>,
        FetchMovies.Finished {

    private static final int FAVOURITE_MOVIE_LOADER = 0;
    RecyclerView mRecyclerview;
    MovieAdapter movieAdapter;
    String sortby;
    ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerview = (RecyclerView) findViewById(R.id.list_movies);
        mRecyclerview.setLayoutManager(new GridLayoutManager(this,3));
        movieAdapter = new MovieAdapter(new ArrayList<Movie>(),this);
        mRecyclerview.setAdapter(movieAdapter);
        sortby = CONSTANTS.POPULAR;
        pb = (ProgressBar) findViewById(R.id.pvloading);
        fetchMovies(sortby);
        findViewById(R.id.blank).setVisibility(View.GONE);
        findViewById(R.id.fblank).setVisibility(View.GONE);
        pb.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        switch (sortby) {
            case CONSTANTS.POPULAR:
                menu.findItem(R.id.menu_most_popular).setChecked(true);
                break;
            case CONSTANTS.RATED:
                menu.findItem(R.id.menu_top_rated).setChecked(true);
                break;
            case CONSTANTS.FAVORITE:
                menu.findItem(R.id.menu_fvorite).setChecked(true);
                break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_most_popular:
                sortby = CONSTANTS.POPULAR;
                fetchMovies(sortby);
                item.setChecked(true);
                break;
            case R.id.menu_top_rated:
                sortby = CONSTANTS.RATED;
                fetchMovies(sortby);
                item.setChecked(true);
                break;
            case R.id.menu_fvorite:
                sortby = CONSTANTS.FAVORITE;
                fetchMovies(sortby);
                item.setChecked(true);
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchMovies(String sort) {
        pb.setVisibility(View.VISIBLE);
        if(sort.equals(CONSTANTS.FAVORITE)) {
            getSupportLoaderManager().initLoader(FAVOURITE_MOVIE_LOADER,null,this);
            pb.setVisibility(View.GONE);
        }
        else {

            new FetchMovies(sort,movieAdapter,mRecyclerview,this).execute();

        }
    }

    @Override
    public void startDetail(Movie movie, int position) {
        Intent intent = new Intent(this,MovieDetailActivity.class);
        intent.putExtra("movie",movie);
        startActivity(intent);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,
                MoviesContract.Entry.CONTENT_URI,
                MoviesContract.Entry.MOVIE_COLUMNS,
                null,
                null,
                null
                );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        movieAdapter.add(data);
        mRecyclerview.setAdapter(movieAdapter);

        Log.d("DATA Size","Size "+movieAdapter.movies.size());
        updateBlankPages();

    }
    public void updateBlankPages() {
        if(movieAdapter.getItemCount() == 0 ){
            if( sortby.equals(CONSTANTS.FAVORITE)) {
                findViewById(R.id.blank).setVisibility(View.GONE);
                findViewById(R.id.fblank).setVisibility(View.VISIBLE);
            } else {
                findViewById(R.id.blank).setVisibility(View.VISIBLE);
                findViewById(R.id.fblank).setVisibility(View.GONE);
            }
        }
        else {
            findViewById(R.id.blank).setVisibility(View.GONE);
            findViewById(R.id.fblank).setVisibility(View.GONE);
            findViewById(R.id.pvloading).setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void complete() {
        updateBlankPages();
        pb.setVisibility(View.GONE);
    }
}
