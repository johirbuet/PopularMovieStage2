package com.johir.movies.moviestage2.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.johir.movies.moviestage2.R;
import com.johir.movies.moviestage2.model.Trailer;

/**
 * Created by mislam on 10/2/17.
 */

public class TrailerViewHolder extends RecyclerView.ViewHolder {
    public final View mView;
    ImageView iv_preview;
    public Trailer trailer;
    public TrailerViewHolder(View itemView) {
        super(itemView);
        iv_preview = (ImageView) itemView.findViewById(R.id.trailer_thumbnail);
        mView = itemView;
    }

}
