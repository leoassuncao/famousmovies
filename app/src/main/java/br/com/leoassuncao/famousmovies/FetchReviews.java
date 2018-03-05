package br.com.leoassuncao.famousmovies;

import android.os.AsyncTask;

import java.net.URL;
import java.util.List;

import br.com.leoassuncao.famousmovies.Data.Review;
import br.com.leoassuncao.famousmovies.Data.ReviewCollection;
import br.com.leoassuncao.famousmovies.Utils.NetworkUtils;
import br.com.leoassuncao.famousmovies.Utils.ReviewsJson;

/**
 * Created by leonardo.filho on 02/03/2018.
 */

public class FetchReviews extends AsyncTask<String, Void, List<Review>> {

    private static final String LOG_TAG = FetchTrailers.class.getSimpleName();
    private String id;

    public FetchReviews(String id) {
        this.id = id;
    }

    @Override
    protected List<Review> doInBackground(String... params) {
        URL reviewsRequestUrl = NetworkUtils.buildTrailersAndReviewsUrl(BuildConfig.API_KEY, id, "reviews");
        try {
            String jsonReviewsResponse = NetworkUtils
                    .getResponseFromHttpUrl(reviewsRequestUrl);
            ReviewCollection reviewsCollection = ReviewsJson.parseJson(jsonReviewsResponse);
            return reviewsCollection.getReviews();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
