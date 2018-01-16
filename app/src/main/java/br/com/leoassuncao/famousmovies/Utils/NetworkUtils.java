package br.com.leoassuncao.famousmovies.Utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;

import static br.com.leoassuncao.famousmovies.Utils.NetworkUtils.APIConstants.API_KEY;
import static br.com.leoassuncao.famousmovies.Utils.NetworkUtils.APIConstants.BASE_URL;
import static br.com.leoassuncao.famousmovies.Utils.NetworkUtils.APIConstants.IMAGE_BASE_URL;
import static br.com.leoassuncao.famousmovies.Utils.NetworkUtils.APIConstants.IMAGE_SIZE;

/**
 * Created by leonardo.filho on 12/01/2018.
 */

public final class NetworkUtils {

    public final static class APIConstants {
        public static final String BASE_URL = "api.themoviedb.org";
        public static final String API_KEY = "331f086d764c674f62af6a6c14de7f26";
        public final static String IMAGE_BASE_URL = "image.tmdb.org/t/p/";
        public static final String IMAGE_SIZE = "w185";
    }


    public static URL buildUrl(String apiKey, String filter) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority(BASE_URL)
                .appendPath("3")
                .appendPath("movie")
                .appendPath(filter)
                .appendQueryParameter("api_key", API_KEY);
        URL url = null;
        try {
            url = new URL(builder.build().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL moviesUrl) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) moviesUrl.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static String buildPosterUrl (String posterPath) {
        return "http://image.tmdb.org/t/p/w500/" + posterPath;
    }
}
