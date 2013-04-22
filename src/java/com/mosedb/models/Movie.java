/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.models;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author llybeck
 */
public class Movie implements Comparable<Movie> {

    private int id;
    private Map<LangId, String> names;
    private String owner;
    private boolean seen;
    private Integer movieYear;
    private List<String> genres;
    private List<Format> formats;

    public Movie(int id, String owner, boolean seen) {
        this.id = id;
        this.owner = owner;
        this.seen = seen;
        this.names = new EnumMap<LangId, String>(LangId.class);
        this.genres = new ArrayList<String>();
        this.formats = new ArrayList<Format>();
    }

    public Movie(int id, String owner, boolean seen, Integer movieYear) {
        this.id = id;
        this.owner = owner;
        this.seen = seen;
        this.movieYear = movieYear;
        this.names = new EnumMap<LangId, String>(LangId.class);
        this.genres = new ArrayList<String>();
        this.formats = new ArrayList<Format>();
    }

    public Movie(int id, Map<LangId, String> names, String owner, boolean seen, int movieYear, List<String> genres, List<Format> formats) {
        this.id = id;
        this.names = names;
        this.owner = owner;
        this.seen = seen;
        this.movieYear = movieYear;
        this.genres = genres;
        this.formats = formats;
    }

    public Movie(Map<LangId, String> names, boolean seen, Integer movieYear, List<String> genres, List<Format> formats) {
        this.names = names;
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

    public Integer getMovieYear() {
        return movieYear;
    }

    public void setMovieYear(Integer movieYear) {
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

    public String getNameEng() {
        return getName(LangId.eng);
    }

    public String getNameFi() {
        return getName(LangId.fi);
    }

    public String getNameSwe() {
        return getName(LangId.swe);
    }

    public String getNameOther() {
        return getName(LangId.other);
    }

    public void addName(String langId, String name) {
        LangId lid = LangId.getLangId(langId);
        names.put(lid, name);
    }

    public void addGenre(String genre) {
        genres.add(genre);
    }

    @Override
    public String toString() {
        return "movieid:\t" + id + "\n"
                + "names:\t" + names + "\n"
                + "owner:\t" + owner + "\n"
                + "year:\t" + movieYear + "\n"
                + "genres:\t" + genres + "\n"
                + "formats:\t" + formats + "\n"
                + "seen:\t" + seen;
    }

    @Override
    public int compareTo(Movie o) {
        String otherString = o.getOwner();
        if (!owner.equals(otherString)) {
            return owner.compareTo(otherString);
        }
        String thisString = ignoreThe(getNameEng());
        otherString = ignoreThe(o.getNameEng());
        if (thisString == null && otherString != null && !otherString.isEmpty()) {
            return 1;
        }
        if (otherString == null && thisString != null && !thisString.isEmpty()) {
            return -1;
        }
        if (thisString != null && otherString != null && !thisString.equals(otherString)) {
            return thisString.compareToIgnoreCase(otherString);
        }
        thisString = getNameFi();
        otherString = o.getNameFi();
        if (thisString == null && otherString != null && !otherString.isEmpty()) {
            return 1;
        }
        if (otherString == null && thisString != null && !thisString.isEmpty()) {
            return -1;
        }
        if (thisString != null && otherString != null && !thisString.equals(otherString)) {
            return thisString.compareToIgnoreCase(otherString);
        }
        thisString = getNameSwe();
        otherString = o.getNameSwe();
        if (thisString == null && otherString != null && !otherString.isEmpty()) {
            return 1;
        }
        if (otherString == null && thisString != null && !thisString.isEmpty()) {
            return -1;
        }
        if (thisString != null && otherString != null && !thisString.equals(otherString)) {
            return thisString.compareToIgnoreCase(otherString);
        }
        thisString = getNameOther();
        otherString = o.getNameOther();
        if (thisString == null && otherString != null && !otherString.isEmpty()) {
            return 1;
        }
        if (otherString == null && thisString != null && !thisString.isEmpty()) {
            return -1;
        }
        if (thisString != null && otherString != null && !thisString.equals(otherString)) {
            return thisString.compareToIgnoreCase(otherString);
        }
        return 0;
    }

    private String ignoreThe(String nameEng) {
        if (nameEng == null) {
            return null;
        }
        if (nameEng.toLowerCase().startsWith("the ")) {
            return nameEng.substring(4);
        }
        return nameEng;
    }
}
