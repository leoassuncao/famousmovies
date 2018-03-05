package br.com.leoassuncao.famousmovies.Adapter;

import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.com.leoassuncao.famousmovies.Data.Movie;
import br.com.leoassuncao.famousmovies.DetailsActivity;
import br.com.leoassuncao.famousmovies.R;
import br.com.leoassuncao.famousmovies.Utils.NetworkUtils;

/**
 * Created by leonardo.filho on 15/01/2018.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesAdapterViewHolder> {

    private List<Movie> movies = new ArrayList<>();

    public MoviesAdapter() {}

    public void setMoviesData(List<Movie> list) {
        movies = list;
        notifyDataSetChanged();
    }

    public class MoviesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imagePoster;

        public MoviesAdapterViewHolder(View view) {
            super(view);
            imagePoster = view.findViewById(R.id.image_poster);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), DetailsActivity.class);
            Movie clickedMovie = movies.get(getAdapterPosition());
            intent.putExtra("movieDetails", clickedMovie);
            view.getContext().startActivity(intent);
        }
    }

    @Override
   public MoviesAdapterViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.movies_list_item, parent, false);
        MoviesAdapterViewHolder viewHolder = new MoviesAdapterViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MoviesAdapterViewHolder holder, int position) {
        Picasso.with(holder.imagePoster.getContext())
                .load(NetworkUtils.buildPosterUrl(movies.get(position).getPosterPath()).toString())
                .placeholder(R.drawable.placeholder_poster)
                .into(holder.imagePoster);
    }


    @Override
    public int getItemCount() {
        return movies.size();
    }


}
