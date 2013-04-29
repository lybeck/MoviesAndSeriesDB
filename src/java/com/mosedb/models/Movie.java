/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Lasse
 */
public class Movie extends AbstractMediaEntity {

    private boolean seen;
    private Integer movieYear;
    private List<Format> formats;

    public Movie(int id, String owner, boolean seen) {
        super(id, owner);
        this.seen = seen;
        this.formats = new ArrayList<Format>();
    }

    public Movie(int id, String owner, boolean seen, Integer movieYear) {
        super(id, owner);
        this.seen = seen;
        this.movieYear = movieYear;
        this.formats = new ArrayList<Format>();
    }

    public Movie(int id, Map<LangId, String> names, String owner, boolean seen, int movieYear, List<String> genres, List<Format> formats) {
        super(id, names, owner, genres);
        this.seen = seen;
        this.movieYear = movieYear;
        this.formats = formats;
    }

    public Movie(Map<LangId, String> names, boolean seen, Integer movieYear, List<String> genres, List<Format> formats) {
        super(names, genres);
        this.seen = seen;
        this.movieYear = movieYear;
        this.formats = formats;
    }

    public List<Format> getFormats() {
        return formats;
    }

    public void setFormats(List<Format> formats) {
        this.formats = formats;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public Integer getMovieYear() {
        return movieYear;
    }

    public void setMovieYear(Integer movieYear) {
        this.movieYear = movieYear;
    }

    @Override
    public String toString() {
        return "movieid:\t" + getId() + "\n"
                + "names:\t" + getNames() + "\n"
                + "owner:\t" + getOwner() + "\n"
                + "year:\t" + movieYear + "\n"
                + "genres:\t" + getGenres() + "\n"
                + "formats:\t" + formats + "\n"
                + "seen:\t" + seen;
    }
}
