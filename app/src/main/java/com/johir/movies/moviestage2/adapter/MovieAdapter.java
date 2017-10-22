package com.johir.movies.moviestage2.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.johir.movies.moviestage2.R;
import com.johir.movies.moviestage2.data.MoviesContract;
import com.johir.movies.moviestage2.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mislam on 10/2/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder>{

    public List<Movie> movies=new ArrayList<>();
    Callback mCallback;
    public MovieAdapter(ArrayList<Movie> movies, Callback callback){
        this.movies = movies;
        this.mCallback = callback;
    }
    public interface Callback{
        void startDetail(Movie movie,int position);
    }
    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_item,parent,false);
        final Context context  = view.getContext();
        view.getLayoutParams().height = (int) (parent.getWidth()/3 *1.5);
        return new MovieViewHolder(view);
    }
    public void add(List<Movie> movies){
        this.movies.clear();
        this.movies.addAll(movies);
        notifyDataSetChanged();
    }
    public void add (Cursor data) {
        this.movies.clear();
        if(data != null && data.moveToFirst()) {
            do {
                long id = data.getLong(MoviesContract.Entry.COL_MOVIE_ID);
                String title = data.getString(MoviesContract.Entry.COL_MOVIE_TITLE);
                String posterPath = data.getString(MoviesContract.Entry.COL_MOVIE_POSTER_PATH);
                String overview = data.getString(MoviesContract.Entry.COL_MOVIE_OVERVIEW);
                String rating = data.getString(MoviesContract.Entry.COL_MOVIE_VOTE_AVERAGE);
                String releaseDate = data.getString(MoviesContract.Entry.COL_MOVIE_RELEASE_DATE);
                String backDropPath = data.getString(MoviesContract.Entry.COL_MOVIE_BACKDROP_PATH);
                Movie movie = new Movie(id,title,posterPath,overview,rating,releaseDate,backDropPath);
                this.movies.add(movie);

            }while (data.moveToNext());
        }
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int position) {
        final Movie movie = movies.get(position);
        final Context context = holder.mIv.getContext();
        Log.d("Title",movie.getmTitle());
        holder.mTv.setText(movie.getmTitle());
        Picasso.with(context)
                .load(movie.getmPosterPath())
                .config(Bitmap.Config.RGB_565)
                .into(holder.mIv);
        holder.mview.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                mCallback.startDetail(movie,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
