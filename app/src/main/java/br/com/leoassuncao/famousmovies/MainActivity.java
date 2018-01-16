package br.com.leoassuncao.famousmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import br.com.leoassuncao.famousmovies.Adapter.MoviesAdapter;
import br.com.leoassuncao.famousmovies.Data.Movie;

/**
 * Created by leonardo.filho on 12/01/2018.
 */

public class MainActivity extends AppCompatActivity {

    private RecyclerView mMoviesList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMoviesList = (RecyclerView) findViewById(R.id.rv_movies);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mMoviesList.setLayoutManager(layoutManager);
        mMoviesList.setHasFixedSize(true);

        if (savedInstanceState == null) {
            setPopularMovies();
        } else {
            setTopRatedMovies();

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
            setPopularMovies();
        }
        if (item.getItemId() == R.id.action_rating) {
            setTopRatedMovies();
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


}
