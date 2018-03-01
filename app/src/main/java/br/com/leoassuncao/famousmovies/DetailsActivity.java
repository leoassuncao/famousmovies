package br.com.leoassuncao.famousmovies;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.leoassuncao.famousmovies.Data.Movie;
import br.com.leoassuncao.famousmovies.Data.MoviesContract;
import br.com.leoassuncao.famousmovies.Data.MoviesDBHelper;
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
    Button favoriteButton;
    private MoviesDBHelper dbHelper;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        movie_image = findViewById(R.id.movie_image);
        movie_title = findViewById(R.id.movie_title);
        release_date = findViewById(R.id.release_date);
        rating = findViewById(R.id.rating);
        sinopse = findViewById(R.id.sinopse);
        toolbar = findViewById(R.id.toolbar);
        favoriteButton = findViewById(R.id.favorite_button);

        setToolbar();

        Bundle extras = getIntent().getExtras();
        final Movie movie = extras.getParcelable("movieDetails");
        setMovie(movie);

        dbHelper = new MoviesDBHelper(this);
        if (checkIfMovieIsInDb(movie)) {
            changeColorOnFavorite();
        }
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkIfMovieIsInDb(movie)) {
                    changeColorOnFavorite();
                    saveMovieInDb(movie);
                } else {
                    changeColorOnUnfavorite();
                    deleteMovieFromDb(movie);
                }
            }
        });
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

    private void deleteMovieFromDb(Movie movie) {
        String selection = MoviesContract.MoviesEntry.COLUMN_MOVIE_ID + "=?";
        String[] selectionArgs = {String.valueOf(movie.getId())};
        getContentResolver().delete(MoviesContract.MoviesEntry.CONTENT_URI, selection, selectionArgs);
    }

    private void saveMovieInDb(Movie movie) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MoviesContract.MoviesEntry.COLUMN_MOVIE_ID, movie.getId());
        contentValues.put(MoviesContract.MoviesEntry.COLUMN_MOVIE_TITLE, movie.getTitle());
        contentValues.put(MoviesContract.MoviesEntry.COLUMN_MOVIE_DESCRIPTION, movie.getOverview());
        contentValues.put(MoviesContract.MoviesEntry.COLUMN_MOVIE_POSTER_PATH, movie.getPosterPath());
        contentValues.put(MoviesContract.MoviesEntry.COLUMN_MOVIE_RELEASE_DATE, movie.getReleaseDate());
        contentValues.put(MoviesContract.MoviesEntry.COLUMN_MOVIE_VOTE_AVERAGE, movie.getVoteAverage());
        getContentResolver().insert(MoviesContract.MoviesEntry.CONTENT_URI, contentValues);
    }

    private void changeColorOnUnfavorite() {
        favoriteButton.setBackgroundColor(getResources().getColor(R.color.colorUnfavorite));
    }

    private void changeColorOnFavorite() {
        favoriteButton.setBackgroundColor(getResources().getColor(R.color.colorFavorite));

    }

    private boolean checkIfMovieIsInDb(Movie movie) {
        Cursor cursor = getContentResolver().query(
                MoviesContract.MoviesEntry.CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int movieId = cursor.getInt(
                        cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_MOVIE_ID));
                if (movieId == movie.getId()) {
                    return true;
                }
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return false;
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
