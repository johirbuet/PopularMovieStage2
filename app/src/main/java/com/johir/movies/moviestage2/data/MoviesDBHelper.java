package com.johir.movies.moviestage2.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mislam on 10/3/17.
 */

public class MoviesDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "movies.db";

    public MoviesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_MOVIES_TABLE = "CREATE TABLE " + MoviesContract.Entry.TABLE_NAME
                + " (" +
                MoviesContract.Entry._ID + " INTEGER PRIMARY KEY," +
                MoviesContract.Entry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                MoviesContract.Entry.COLUMN_MOVIE_TITLE + " TEXT NOT NULL, " +
                MoviesContract.Entry.COLUMN_MOVIE_POSTER_PATH + " TEXT NOT NULL, " +
                MoviesContract.Entry.COLUMN_MOVIE_OVERVIEW + " TEXT NOT NULL, " +
                MoviesContract.Entry.COLUMN_MOVIE_VOTE_AVERAGE + " TEXT NOT NULL, " +
                MoviesContract.Entry.COLUMN_MOVIE_RELEASE_DATE + " TEXT NOT NULL, " +
                MoviesContract.Entry.COLUMN_MOVIE_BACKDROP_PATH + " TEXT NOT NULL " +
                " );";
        sqLiteDatabase.execSQL(SQL_CREATE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MoviesContract.Entry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
