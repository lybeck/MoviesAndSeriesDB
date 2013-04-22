/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.models;

import com.mosedb.models.LangId;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author llybeck
 */
public class Series {

    private int id;
    private String owner;
    private Map<LangId, String> names;
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

    public Series(int id, String owner) {
        this.id = id;
        this.names = new EnumMap<LangId, String>(LangId.class);
        this.owner = owner;
        this.genres = new ArrayList<String>();
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

    public Map<LangId, String> getNames() {
        return names;
    }

    public void setNames(Map<LangId, String> names) {
        this.names = names;
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

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        return "seriesid:\t" + id + "\n"
                + "names:\t" + names + "\n"
                + "owner:\t" + owner + "\n"
                + "genres:\t" + genres + "\n";
    }
}
