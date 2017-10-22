package com.johir.movies.moviestage2.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.johir.movies.moviestage2.model.Movies;

/**
 * Created by mislam on 10/3/17.
 */

public class MoviesProvider extends ContentProvider {
    private MoviesDBHelper moviesDBHelper;
    public static final int MOVIES = 400;
    public static final UriMatcher sUriMatcher = buildUriMatcher();
    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MoviesContract.CONTENT_AUTHORITY;
        matcher.addURI(authority,MoviesContract.PATH, MOVIES);
        return matcher;
    }
    @Override
    public boolean onCreate() {
        moviesDBHelper = new MoviesDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projections, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)){
            case MOVIES:
                cursor = moviesDBHelper.getReadableDatabase().query(
                        MoviesContract.Entry.TABLE_NAME,
                        projections,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri: "+uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIES:
                return MoviesContract.Entry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("unknow uri: "+uri);
        }

    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db = moviesDBHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;
        switch (match) {
            case MOVIES:
                long id = db.insert(MoviesContract.Entry.TABLE_NAME,null,contentValues);
                if( id > 0) {
                    returnUri = MoviesContract.Entry.buildMovieUri(id);
                }
                else {
                    throw new SQLException("Failed to insert row: "+uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("unknown uri: "+uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = moviesDBHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        if(selection == null) {
            selection = "1";
        }
        int rowsdeleted = 0;
        switch (match) {
            case MOVIES:
                rowsdeleted = db.delete(MoviesContract.Entry.TABLE_NAME,selection,selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("unknown uri: "+uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return rowsdeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = moviesDBHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int rowsUpdated =0;
        switch (match) {
            case MOVIES:
                rowsUpdated = db.update(MoviesContract.Entry.TABLE_NAME,contentValues,selection,selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("unknown uri: "+uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return rowsUpdated;
    }
}
