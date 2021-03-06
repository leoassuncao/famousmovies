package br.com.leoassuncao.famousmovies.data;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import br.com.leoassuncao.famousmovies.adapter.FavoriteAdapter;

import static br.com.leoassuncao.famousmovies.MainActivity.ID_FAVORITES_LOADER;

/**
 * Created by leonardo.filho on 01/03/2018.
 */

public class FavoriteCursorLoader implements LoaderManager.LoaderCallbacks<Cursor> {

    private Context context;
    private FavoriteAdapter favoritesAdapter;

    public FavoriteCursorLoader(Context context, FavoriteAdapter favoritesAdapter) {
        this.context = context;
        this.favoritesAdapter = favoritesAdapter;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {
        switch (loaderId) {
            case ID_FAVORITES_LOADER:
                String[] projection = {
                        MoviesContract.MoviesEntry.COLUMN_MOVIE_TITLE,
                        MoviesContract.MoviesEntry.COLUMN_MOVIE_ID,
                        MoviesContract.MoviesEntry.COLUMN_MOVIE_DESCRIPTION,
                        MoviesContract.MoviesEntry.COLUMN_MOVIE_POSTER_PATH,
                        MoviesContract.MoviesEntry.COLUMN_MOVIE_RELEASE_DATE,
                        MoviesContract.MoviesEntry.COLUMN_MOVIE_VOTE_AVERAGE
                };
                return new CursorLoader(context,
                        MoviesContract.MoviesEntry.CONTENT_URI,
                        projection,
                        null,
                        null,
                        null);
            default:
                throw new RuntimeException("Loader failed: " + loaderId);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        favoritesAdapter.changeCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        favoritesAdapter.changeCursor(null);
    }
}
