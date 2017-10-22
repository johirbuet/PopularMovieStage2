package com.johir.movies.moviestage2.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.johir.movies.moviestage2.R;

/**
 * Created by mislam on 10/2/17.
 */

public class MovieViewHolder extends RecyclerView.ViewHolder{

    public final View mview;
    public ImageView mIv;
    public TextView mTv;
    public MovieViewHolder(View itemView) {
        super(itemView);
        mIv = (ImageView) itemView.findViewById(R.id.preview);
        mTv = (TextView) itemView.findViewById(R.id.movie_title);
        this.mview = itemView;
    }
}