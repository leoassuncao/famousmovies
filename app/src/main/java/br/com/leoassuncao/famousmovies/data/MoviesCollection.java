package br.com.leoassuncao.famousmovies.data;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by leonardo.filho on 15/01/2018.
 */

public class MoviesCollection {
    List<Movie> movies;

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
