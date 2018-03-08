package br.com.leoassuncao.famousmovies.data;

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
        return "MovieCollection{" +
                "movies=" + movies +
                '}';
    }
}
