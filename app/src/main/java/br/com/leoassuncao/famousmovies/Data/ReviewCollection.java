package br.com.leoassuncao.famousmovies.Data;

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
        return "ReviewsCollection{" +
                "reviews=" + reviews+
                '}';
    }

}
