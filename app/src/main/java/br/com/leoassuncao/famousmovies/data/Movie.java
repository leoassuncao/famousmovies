package br.com.leoassuncao.famousmovies.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by leonardo.filho on 15/01/2018.
 */

public class Movie implements Parcelable {

    private String posterPath;
    private String overview;
    private String releaseDate;
    private String title;
    private int id;
    private long voteAverage;
    private long popularity;

    public Movie() {
    }

    public Movie(Parcel parcel) {
        posterPath = parcel.readString();
        overview = parcel.readString();
        releaseDate = parcel.readString();
        title = parcel.readString();
        id = parcel.readInt();
        voteAverage = parcel.readLong();
        popularity = parcel.readLong();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(posterPath);
        parcel.writeString(overview);
        parcel.writeString(releaseDate);
        parcel.writeString(title);
        parcel.writeInt(id);
        parcel.writeLong(voteAverage);
        parcel.writeLong(popularity);
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }
    };

     @Override
    public int describeContents() {
        return 0;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(long voteAverage) {
        this.voteAverage = voteAverage;
    }

    public long getPopularity() {
        return popularity;
    }

    public void setPopularity(long popularity) {
        this.popularity = popularity;
    }

}
