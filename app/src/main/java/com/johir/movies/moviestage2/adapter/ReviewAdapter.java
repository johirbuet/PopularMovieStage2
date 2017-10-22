package com.johir.movies.moviestage2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.johir.movies.moviestage2.R;
import com.johir.movies.moviestage2.model.Review;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mislam on 10/2/17.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewViewHolder> {
    List<Review> reviews = new ArrayList<>();
    Callback mCallback;
    public ReviewAdapter(ArrayList<Review> reviews, Callback callback) {
        this.reviews = reviews;
        this.mCallback = callback;
    }

    public void add(List<Review> reviews) {
        this.reviews.clear();
        this.reviews.addAll(reviews);
        notifyDataSetChanged();
    }
    public interface Callback{
        void read(Review review,int position);
    }
    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_item,parent,false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, final int position) {
        final Review review = reviews.get(position);
        final Context context = holder.mView.getContext();
        holder.tvAuthor.setText(review.getmAuthor());
        holder.tv_review.setText(review.getmContent());
        holder.mView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mCallback.read(review,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }
}
