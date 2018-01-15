package br.com.leoassuncao.famousmovies.Utils;

import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.leoassuncao.famousmovies.Data.Movie;
import br.com.leoassuncao.famousmovies.Data.MoviesCollection;

/**
 * Created by leonardo.filho on 15/01/2018.
 */

public class MoviesJson {

    private static final String statusError = "status_code";
    private static final String movies = "results";
    private static final String poster = "poster_path";
    private static final String overview= "overview";
    private static final String releaseDate = "release_date";
    private static final String title = "title";
    private static final String id = "id";
    private static final String voteAverage = "vote_average";
    private static final String popularity = "popularity";

    public static MoviesCollection parseJson(String json)
            throws JSONException {
        JSONObject responseJson = new JSONObject(json);
        if (responseJson.has(statusError)) {
            int errorCode = responseJson.getInt(statusError);
            Log.e(MoviesJson.class.getCanonicalName(), "parse json movies error code: " + String.valueOf(errorCode));
        }
        JSONArray moviesArray = responseJson.getJSONArray(movies);
        List<Movie> movieList = parseMovieList(moviesArray);
        MoviesCollection movieCollection = new MoviesCollection();
        movieCollection.setMovies(movieList);
        return movieCollection;
    }

    @NonNull
    private static List<Movie> parseMovieList(JSONArray moviesArray) throws JSONException {
        List<Movie> movieList = new ArrayList<>();
        for (int i = 0; i < moviesArray.length(); i++) {
            JSONObject movie = moviesArray.getJSONObject(i);
            Movie currentMovie = parseMovie(movie);
            movieList.add(currentMovie);
        }
        return movieList;
    }

    @NonNull
    private static Movie parseMovie(JSONObject movie) throws JSONException {
        Movie currentMovie = new Movie();
        currentMovie.setPosterPath(movie.getString(poster));
        currentMovie.setOverview(movie.getString(overview));
        currentMovie.setReleaseDate(movie.getString(releaseDate));
        currentMovie.setTitle(movie.getString(title));
        currentMovie.setId(movie.getInt(id));
        currentMovie.setVoteAverage(movie.getLong(voteAverage));
        currentMovie.setPopularity(movie.getLong(popularity));
        return currentMovie;
    }
}
