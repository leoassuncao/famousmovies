package br.com.leoassuncao.famousmovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;



import br.com.leoassuncao.famousmovies.Adapter.FavoriteAdapter;
import br.com.leoassuncao.famousmovies.Adapter.MoviesAdapter;
import br.com.leoassuncao.famousmovies.Data.FavoriteCursorLoader;


/**
 * Created by leonardo.filho on 12/01/2018.
 */

public class MainActivity extends AppCompatActivity {

    private RecyclerView mMoviesList;
    private Snackbar mSnackbar;
    public static final int ID_FAVORITES_LOADER = 11;
    private int optionSelected = -1 ;
    private final static String MENU_SELECTED = "selected";
    private Parcelable listState;


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
            if (savedInstanceState != null) {
               optionSelected = savedInstanceState.getInt(MENU_SELECTED);
                listState = savedInstanceState.getParcelable("ListState");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        if (optionSelected == -1) {
           setPopularMovies();
        }
        switch (optionSelected) {
            case R.id.action_favorites:
                setFavoriteMovies();
                break;

            case R.id.action_popularity:
                setPopularMovies();
                break;

            case R.id.action_rating:
                setTopRatedMovies();
                break;
        }
        return true;
    }


    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(MENU_SELECTED , optionSelected);
        savedInstanceState.putParcelable("ListState", mMoviesList.getLayoutManager().onSaveInstanceState());
        super.onSaveInstanceState(savedInstanceState);

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_popularity) {
            boolean IsConnected = checkConnection();
            if (!IsConnected) {
                showError();
            } else {
                optionSelected = id;
                setPopularMovies();
            }
        }
        if (id == R.id.action_rating) {
            boolean IsConnected = checkConnection();
            if (!IsConnected) {
                showError();
            } else {
                optionSelected = id;
                setTopRatedMovies();
            }
        }
        if(id == R.id.action_favorites) {
            optionSelected = id;
                setFavoriteMovies();

        }
        return super.onOptionsItemSelected(item);

    }


    public void setTopRatedMovies() {
        MoviesAdapter moviesAdapter = new MoviesAdapter();
        FetchMovies movies = new FetchMovies(moviesAdapter);
        mMoviesList.setAdapter(moviesAdapter);
        movies.execute("top_rated");
        mMoviesList.getLayoutManager().onRestoreInstanceState(listState);
    }


    public void setPopularMovies() {
        MoviesAdapter moviesAdapter = new MoviesAdapter();
        FetchMovies moviesTask = new FetchMovies(moviesAdapter);
        mMoviesList.setAdapter(moviesAdapter);
        moviesTask.execute("popular");
        mMoviesList.getLayoutManager().onRestoreInstanceState(listState);
    }

    public void setFavoriteMovies() {
        FavoriteAdapter favoriteAdapter = new FavoriteAdapter();
        mMoviesList.setAdapter(favoriteAdapter);
        getSupportLoaderManager().initLoader(ID_FAVORITES_LOADER, null, new FavoriteCursorLoader(this, favoriteAdapter));
        mMoviesList.getLayoutManager().onRestoreInstanceState(listState);
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

  /*  private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // You can change this divider to adjust the size of the poster
        int widthDivider = 400;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 2) return 2;
        return nColumns;
    } */
}
