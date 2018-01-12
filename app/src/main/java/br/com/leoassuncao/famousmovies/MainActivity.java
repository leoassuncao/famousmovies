package br.com.leoassuncao.famousmovies;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by leonardo.filho on 12/01/2018.
 */

public class MainActivity extends AppCompatActivity {

    private RecyclerView mMoviesList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMoviesList = (RecyclerView) findViewById(R.id.rv_movies);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mMoviesList.setLayoutManager(layoutManager);
        mMoviesList.setHasFixedSize(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_popularity:
                return true;

            case R.id.action_rating:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
