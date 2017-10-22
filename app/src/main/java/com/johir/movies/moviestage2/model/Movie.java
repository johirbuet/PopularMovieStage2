package com.johir.movies.moviestage2.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mislam on 10/1/17.
 */

public class Movie implements Parcelable {
    @SerializedName("id")
    private long mId;
    @SerializedName("original_title")
    private String mTitle;
    @SerializedName("poster_path")
    private String mPosterPath;
    @SerializedName("overview")
    private String mOverView;
    @SerializedName("vote_average")
    private String mRating;
    @SerializedName("release_date")
    private String mReleaseDate;
    @SerializedName("backdrop_path")
    private String mBackdropPath;


    public Movie(long mId, String mTitle, String mPosterPath, String mOverView, String mRating, String mReleaseDate, String mBackdropPath) {

        this.mId = mId;
        this.mTitle = mTitle;
        this.mPosterPath = mPosterPath;
        this.mOverView = mOverView;
        this.mRating = mRating;
        this.mReleaseDate = mReleaseDate;
        this.mBackdropPath = mBackdropPath;
    }

    public long getmId() {
        return mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmPosterPath() {
        return CONSTANTS.POSTER_PATH+mPosterPath;
    }

    public String getmOverView() {
        return mOverView;
    }

    public String getmRating() {
        return mRating;
    }

    public String getmReleaseDate() {
        return mReleaseDate;
    }

    public String getmBackdropPath() {
        return mBackdropPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mId);
        dest.writeString(this.mTitle);
        dest.writeString(this.mPosterPath);
        dest.writeString(this.mOverView);
        dest.writeString(this.mRating);
        dest.writeString(this.mReleaseDate);
        dest.writeString(this.mBackdropPath);
    }

    protected Movie(Parcel in) {
        this.mId = in.readLong();
        this.mTitle = in.readString();
        this.mPosterPath = in.readString();
        this.mOverView = in.readString();
        this.mRating = in.readString();
        this.mReleaseDate = in.readString();
        this.mBackdropPath = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
