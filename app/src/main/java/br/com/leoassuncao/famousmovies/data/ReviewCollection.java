package br.com.leoassuncao.famousmovies.data;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by leonardo.filho on 02/03/2018.
 */

public class ReviewCollection {
    private List<Review> reviews;

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
