package com.johir.movies.moviestage2.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.johir.movies.moviestage2.R;
import com.johir.movies.moviestage2.model.CONSTANTS;
import com.johir.movies.moviestage2.model.Trailer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mislam on 10/2/17.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerViewHolder> {
    List<Trailer> trailers = new ArrayList<>();
    Callback mCallback;
    public TrailerAdapter(ArrayList<Trailer> trailers,Callback callback) {
        this.trailers = trailers;
        this.mCallback = callback;
    }

    public void add(List<Trailer> trailers){
        this.trailers.clear();
        this.trailers.addAll(trailers);
        notifyDataSetChanged();
    }
    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_item,parent,false);
        view.getLayoutParams().height *=2;
        view.getLayoutParams().width *=2;
        return new TrailerViewHolder(view);
    }
    public interface Callback {
        void watch(Trailer trailer,int position);
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, final int position) {
        final Trailer trailer = trailers.get(position);
        final Context context = holder.mView.getContext();
        holder.trailer = trailer;
        String thumbUrl = "http://img.youtube.com/vi/"+trailer.getmKey()+"/0.jpg";
        Log.d("Thumbnail",thumbUrl);
        Picasso.with(context)
                .load(thumbUrl)
                .config(Bitmap.Config.RGB_565)
                .into(holder.iv_preview);
        holder.mView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(CONSTANTS.YOUTUBE_BASE+trailer.getmKey()));
                mCallback.watch(trailer,position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    public List<Trailer> getTrailers() { return  trailers;}
}
