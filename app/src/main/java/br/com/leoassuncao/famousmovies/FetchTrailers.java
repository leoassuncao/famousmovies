package br.com.leoassuncao.famousmovies;

import android.os.AsyncTask;

import java.net.URL;
import java.util.List;

import br.com.leoassuncao.famousmovies.data.Trailer;
import br.com.leoassuncao.famousmovies.data.TrailerCollection;
import br.com.leoassuncao.famousmovies.utils.NetworkUtils;
import br.com.leoassuncao.famousmovies.utils.TrailersJson;

/**
 * Created by leonardo.filho on 01/03/2018.
 */

public class FetchTrailers extends AsyncTask<String, Void, List<Trailer>>{

    private static final String LOG_TAG = FetchTrailers.class.getSimpleName();
    private String id;

    public FetchTrailers(String id) {
        this.id = id;
    }

    @Override
    protected List<Trailer> doInBackground(String... params) {
        URL trailersRequestUrl = NetworkUtils.buildTrailersAndReviewsUrl(BuildConfig.API_KEY, id, "videos");
        try {
            String jsonTrailersResponse = NetworkUtils
                    .getResponseFromHttpUrl(trailersRequestUrl);
            TrailerCollection trailerCollection = TrailersJson.parseJson(jsonTrailersResponse);
            return trailerCollection.getTrailers();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
