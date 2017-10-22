package com.johir.movies.moviestage2.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mislam on 10/1/17.
 */

public class Trailer implements Parcelable {

    @SerializedName("id")
    private String mId;
    @SerializedName("key")
    private String mKey;
    @SerializedName("name")
    private String mName;
    @SerializedName("site")
    private String mSite;
    @SerializedName("size")
    private String mSize;

    public Trailer(String mId, String mKey, String mName, String mSite, String mSize) {
        this.mId = mId;
        this.mKey = mKey;
        this.mName = mName;
        this.mSite = mSite;
        this.mSize = mSize;
    }

    public String getTrailerURL(){
        return CONSTANTS.YOUTUBE_BASE+mKey;
    }
    public String getmId() {
        return mId;
    }

    public String getmKey() {
        return mKey;
    }

    public String getmName() {
        return mName;
    }

    public String getmSite() {
        return mSite;
    }

    public String getmSize() {
        return mSize;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mId);
        dest.writeString(this.mKey);
        dest.writeString(this.mName);
        dest.writeString(this.mSite);
        dest.writeString(this.mSize);
    }

    protected Trailer(Parcel in) {
        this.mId = in.readString();
        this.mKey = in.readString();
        this.mName = in.readString();
        this.mSite = in.readString();
        this.mSize = in.readString();
    }

    public static final Parcelable.Creator<Trailer> CREATOR = new Parcelable.Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel source) {
            return new Trailer(source);
        }

        @Override
        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };
}
