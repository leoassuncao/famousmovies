package br.com.leoassuncao.famousmovies.Adapter;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import br.com.leoassuncao.famousmovies.Data.Movie;
import br.com.leoassuncao.famousmovies.Data.MoviesContract;
import br.com.leoassuncao.famousmovies.DetailsActivity;
import br.com.leoassuncao.famousmovies.R;
import br.com.leoassuncao.famousmovies.Utils.NetworkUtils;

/**
 * Created by leonardo.filho on 01/03/2018.
 */

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteAdapterViewHolder>{

    private Cursor cursor;

    public FavoriteAdapter() {

    }

     public void changeCursor(Cursor newCursor) {
        if (cursor != null) {
            cursor.close();
        }
        cursor = newCursor;
        if (newCursor != null) {
            this.notifyDataSetChanged();
        }
    }

    public class FavoriteAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imagePoster;

        public FavoriteAdapterViewHolder(View itemView) {
            super(itemView);
            imagePoster = itemView.findViewById(R.id.image_poster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), DetailsActivity.class);
            cursor.moveToPosition(getAdapterPosition());
            Movie movie = getMovieFromCursor();
            intent.putExtra("movieDetails", movie);
            view.getContext().startActivity(intent);
        }

        @NonNull
        private Movie getMovieFromCursor() {
            Movie currentMovie = new Movie();
            currentMovie.setTitle(cursor.getString(
                    cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_MOVIE_TITLE)));
            currentMovie.setId(cursor.getInt(
                    cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_MOVIE_ID)));
            currentMovie.setOverview(cursor.getString(
                    cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_MOVIE_DESCRIPTION)));
            currentMovie.setPosterPath(cursor.getString(
                    cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_MOVIE_POSTER_PATH)));
            currentMovie.setReleaseDate(cursor.getString(
                    cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_MOVIE_RELEASE_DATE)));
            currentMovie.setVoteAverage(cursor.getLong(
                    cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_MOVIE_VOTE_AVERAGE)));
            return currentMovie;
        }

    }

    @Override
    public FavoriteAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.movies_list_item, parent, false);
        FavoriteAdapterViewHolder viewHolder = new FavoriteAdapterViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FavoriteAdapterViewHolder holder, int position) {
        cursor.moveToPosition(position);
        String posterPath = cursor.getString(
                cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_MOVIE_POSTER_PATH));
        Picasso.with(holder.imagePoster.getContext())
                .load(NetworkUtils.buildPosterUrl(posterPath))
                .placeholder(R.drawable.placeholder_poster)
                .into(holder.imagePoster);
    }

    @Override
    public int getItemCount() {
        if (cursor == null) {
            return 0;
        }
        return cursor.getCount();
    }
    }
