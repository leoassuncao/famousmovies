package br.com.leoassuncao.famousmovies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.leoassuncao.famousmovies.Data.Movie;
import br.com.leoassuncao.famousmovies.Utils.NetworkUtils;

/**
 * Created by leonardo.filho on 15/01/2018.
 */

public class DetailsActivity extends AppCompatActivity {

    ImageView movie_image;
    TextView movie_title;
    TextView release_date;
    TextView rating;
    TextView sinopse;
    Toolbar toolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        movie_image = (ImageView) findViewById(R.id.movie_image);
        movie_title = (TextView) findViewById(R.id.movie_title);
        release_date = (TextView) findViewById(R.id.release_date);
        rating = (TextView) findViewById(R.id.rating);
        sinopse = (TextView) findViewById(R.id.sinopse);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setToolbar();

        Bundle extras = getIntent().getExtras();
        final Movie movie = extras.getParcelable("movieDetails");
        setMovie(movie);
    }

    private void setMovie(Movie movie) {
        Picasso.with(movie_image.getContext())
                .load(NetworkUtils.buildPosterUrl(movie.getPosterPath()))
                .fit().centerCrop()
                .placeholder(R.drawable.placeholder_poster)
                .into(movie_image);

        movie_title.setText(movie.getTitle());
        release_date.setText(movie.getReleaseDate());
        sinopse.setText(movie.getOverview());
        rating.setText(String.valueOf(movie.getVoteAverage()) + "/10");

    }

    private void setToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
