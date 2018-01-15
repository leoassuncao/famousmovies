package br.com.leoassuncao.famousmovies;

import android.os.AsyncTask;

import java.net.URL;
import java.util.List;

import br.com.leoassuncao.famousmovies.Adapter.MoviesAdapter;
import br.com.leoassuncao.famousmovies.Data.Movie;
import br.com.leoassuncao.famousmovies.Data.MoviesCollection;
import br.com.leoassuncao.famousmovies.Utils.MoviesJson;
import br.com.leoassuncao.famousmovies.Utils.NetworkUtils;

/**
 * Created by leonardo.filho on 15/01/2018.
 */

public class FetchMovies extends AsyncTask<String, Void, List<Movie>> {

    private MoviesAdapter adapter;

    public FetchMovies(MoviesAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    protected List<Movie> doInBackground(String... params) {
        URL requestUrl = NetworkUtils.buildUrl(NetworkUtils.APIConstants.API_KEY, params[0] );
        try {
            String jsonMoviesResponse = NetworkUtils
                    .getResponseFromHttpUrl(requestUrl);
            MoviesCollection movieCollection = MoviesJson.parseJson(jsonMoviesResponse);
            return movieCollection.getMovies();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        if (movies != null) {
            adapter.setMoviesData(movies);
        }
    }
}
