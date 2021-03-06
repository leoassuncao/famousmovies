package br.com.leoassuncao.famousmovies.data;

import com.google.gson.Gson;

/**
 * Created by leonardo.filho on 02/03/2018.
 */

public class Review {

    private String author;
    private String content;
    private String url;

    public Review() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
     return new Gson().toJson(this);
    }
}
