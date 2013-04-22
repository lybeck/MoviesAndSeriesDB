/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.models;

import com.mosedb.models.Movie.LangId;
import java.util.List;
import java.util.Map;

/**
 *
 * @author llybeck
 */
public class Series {

    private int id;
    private String owner;
    private Map<Movie.LangId, String> names;
    private List<String> genres;

    public Series(Map<LangId, String> names, List<String> genres) {
        this.names = names;
        this.genres = genres;
    }

    public Series(int id, Map<LangId, String> names, String owner, List<String> genres) {
        this.id = id;
        this.names = names;
        this.owner = owner;
        this.genres = genres;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Map<Movie.LangId, String> getNames() {
        return names;
    }

    public void setNames(Map<Movie.LangId, String> names) {
        this.names = names;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }
}
