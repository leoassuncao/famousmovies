package br.com.leoassuncao.famousmovies.data;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by leonardo.filho on 01/03/2018.
 */

public class TrailerCollection {
    private List<Trailer> trailers;

    public List<Trailer> getTrailers() {
        return trailers;
    }

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
