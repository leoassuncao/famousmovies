package br.com.leoassuncao.famousmovies.data;

import com.google.gson.Gson;

/**
 * Created by leonardo.filho on 01/03/2018.
 */

public class Trailer {

    private String id;
    private String key;
    private String name;
    private String site;
    private String type;

    public Trailer () {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
