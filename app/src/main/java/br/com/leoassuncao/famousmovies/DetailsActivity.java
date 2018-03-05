package br.com.leoassuncao.famousmovies;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import br.com.leoassuncao.famousmovies.Data.Movie;
import br.com.leoassuncao.famousmovies.Data.MoviesContract;
import br.com.leoassuncao.famousmovies.Data.MoviesDBHelper;
import br.com.leoassuncao.famousmovies.Data.Review;
import br.com.leoassuncao.famousmovies.Data.Trailer;

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
    LinearLayout linearLayoutTrailers;
    LinearLayout linearLayoutReviews;
    TextView trailerTitle;
    TextView reviewTitle;
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
        linearLayoutTrailers = findViewById(R.id.layout_trailers_list);
        linearLayoutReviews = findViewById(R.id.layout_reviews_list);
        trailerTitle = findViewById(R.id.trailer_title);
        reviewTitle = findViewById(R.id.reviews_title);

        setToolbar();

        Bundle extras = getIntent().getExtras();
        final Movie movie = extras.getParcelable("movieDetails");
        setMovie(movie);

        dbHelper = new MoviesDBHelper(this);
        if (checkIfMovieIsInDb(movie)) {
            changeColorOnFavorite();
        } else {
            changeColorOnUnfavorite();
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

       new FetchTrailers(String.valueOf(movie.getId())) {
            @Override
            protected void onPostExecute(List<Trailer> trailers) {
                addTrailers(trailers);
            }
        }.execute();


        new FetchReviews(String.valueOf(movie.getId())) {
            @Override
            protected void onPostExecute(List<Review> reviews) {
                addReviews(reviews);
            }
        }.execute();
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
        favoriteButton.setText(R.string.favorite_string);
    }

    private void changeColorOnFavorite() {
        favoriteButton.setBackgroundColor(getResources().getColor(R.color.colorFavorite));
        favoriteButton.setText(R.string.unfavorite_string);

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


    private void addTrailers (List<Trailer> trailers) {
        if(trailers != null && !trailers.isEmpty()) {
            for (Trailer trailer : trailers) {
                if (trailer.getType().equals(getString(R.string.trailer_type)) &&
                        trailer.getSite().equals(getString(R.string.trailer_site_youtube))) {
                    View view = getTrailerView(trailer);
                    linearLayoutTrailers.addView(view);
                }
            }
        } else {
            hideTrailers();
        }
    }

    private void addReviews(List<Review> reviews) {
        if (reviews != null && !reviews.isEmpty()) {
            for (Review review : reviews) {
                View view = getReviewView(review);
                linearLayoutReviews.addView(view);
            }
        } else {
            hideReviews();
        }
    }


    private View getTrailerView(final Trailer trailer) {
        LayoutInflater inflater = LayoutInflater.from(DetailsActivity.this);
        View view = inflater.inflate(R.layout.trailer_list_item, linearLayoutTrailers, false);
        TextView trailerNameTV = (TextView) view.findViewById(R.id.trailer_item_name);
        trailerNameTV.setText(trailer.getName());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(NetworkUtils.buildYoutubeUrl(trailer.getKey())));
                startActivity(intent);
            }
        });
        return view;
    }

    private View getReviewView(final Review review) {
        LayoutInflater inflater = LayoutInflater.from(DetailsActivity.this);
        View view = inflater.inflate(R.layout.review_list_item, linearLayoutReviews, false);
        TextView contentTV = view.findViewById(R.id.content_review);
        TextView authorTV = view.findViewById(R.id.author_review);
        authorTV.setText(review.getAuthor());
        contentTV.setText(review.getContent());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = review.getUrl();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
        return view;
    }



    private void hideTrailers() {
        trailerTitle.setVisibility(View.GONE);
        linearLayoutTrailers.setVisibility(View.GONE);
    }

    private void hideReviews() {
        reviewTitle.setVisibility(View.GONE);
        linearLayoutTrailers.setVisibility(View.GONE);
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
