package com.johir.movies.moviestage2.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.johir.movies.moviestage2.R;

/**
 * Created by mislam on 10/2/17.
 */

public class ReviewViewHolder extends RecyclerView.ViewHolder {
    public final View mView;
    TextView tv_review;
    TextView tvAuthor;
    public ReviewViewHolder(View itemView) {
        super(itemView);
        tvAuthor = itemView.findViewById(R.id.review_author);
        tv_review = itemView.findViewById(R.id.review_content);
        mView = itemView;

    }
}
