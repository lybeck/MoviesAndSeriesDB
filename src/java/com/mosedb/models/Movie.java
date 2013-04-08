/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.models;

import java.util.List;
import java.util.Map;

/**
 *
 * @author llybeck
 */
public class Movie {

    private int id;
    private Map<LangId, String> names;
    private String owner;
    private boolean seen;
    private int movieYear;
    private List<String> genres;
    private List<Format> formats;

    public Movie(int id, Map<LangId, String> names, String owner, boolean seen, int movieYear, List<String> genres, List<Format> formats) {
        this.id = id;
        this.names = names;
        this.owner = owner;
        this.seen = seen;
        this.movieYear = movieYear;
        this.genres = genres;
        this.formats = formats;
    }

    public List<Format> getFormats() {
        return formats;
    }

    public void setFormats(List<Format> formats) {
        this.formats = formats;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<LangId, String> getNames() {
        return names;
    }

    public void setNames(Map<LangId, String> names) {
        this.names = names;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public int getMovieYear() {
        return movieYear;
    }

    public void setMovieYear(int movieYear) {
        this.movieYear = movieYear;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getName(LangId langId) {
        return names.get(langId);
    }

    public void addName(String langId, String name) {
        LangId lid = getLangId(langId);
        names.put(lid, name);
    }

    public void addGenre(String genre) {
        genres.add(genre);
    }

    public static LangId getLangId(String langId) {
        LangId enumLangId = LangId.other;
        if (langId.equalsIgnoreCase("eng")) {
            enumLangId = LangId.eng;
        } else if (langId.equalsIgnoreCase("fi")) {
            enumLangId = LangId.fi;
        } else if (langId.equalsIgnoreCase("swe")) {
            enumLangId = LangId.swe;
        }
        return enumLangId;
    }

    public static enum LangId {

        eng, fi, swe, other;
    }
}
