package br.com.leoassuncao.famousmovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import br.com.leoassuncao.famousmovies.Adapter.MoviesAdapter;

/**
 * Created by leonardo.filho on 12/01/2018.
 */

public class MainActivity extends AppCompatActivity {

    private RecyclerView mMoviesList;
    private Snackbar mSnackbar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMoviesList = findViewById(R.id.rv_movies);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mMoviesList.setLayoutManager(layoutManager);
        mMoviesList.setHasFixedSize(true);

        boolean IsConnected = checkConnection();
        if (!IsConnected) {
            showError();
        } else {
            if (savedInstanceState == null) {
                setPopularMovies();
            } else {
                setTopRatedMovies();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("optionSelected", R.id.action_rating);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_popularity) {
            boolean IsConnected = checkConnection();
            if (!IsConnected) {
                showError();
            } else {
                setPopularMovies();
            }
        }
        if (item.getItemId() == R.id.action_rating) {
            boolean IsConnected = checkConnection();
            if (!IsConnected) {
                showError();
            } else {
                setTopRatedMovies();
            }
        }
        return super.onOptionsItemSelected(item);

    }


    public void setTopRatedMovies() {
        MoviesAdapter moviesAdapter = new MoviesAdapter();
        FetchMovies movies = new FetchMovies(moviesAdapter);
        mMoviesList.setAdapter(moviesAdapter);
        movies.execute("top_rated");
    }


    public void setPopularMovies() {
        MoviesAdapter moviesAdapter = new MoviesAdapter();
        FetchMovies moviesTask = new FetchMovies(moviesAdapter);
        mMoviesList.setAdapter(moviesAdapter);
        moviesTask.execute("popular");
    }


    boolean checkConnection() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return (activeNetwork != null && activeNetwork.isConnectedOrConnecting());
    }

    private void showError() {
        Snackbar.make(findViewById(R.id.cl_main_activity), R.string.connection_error, Snackbar.LENGTH_LONG).show();
    }
}
