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
public abstract class AbstractMediaEntity implements Comparable<AbstractMediaEntity> {

    private int id;
    private String owner;
    private Map<LangId, String> names;
    private List<String> genres;

    public AbstractMediaEntity(Map<LangId, String> names, List<String> genres) {
        this.names = names;
        this.genres = genres;
    }

    public AbstractMediaEntity(int id, Map<LangId, String> names, String owner, List<String> genres) {
        this.id = id;
        this.names = names;
        this.owner = owner;
        this.genres = genres;
    }

    public AbstractMediaEntity(int id, String owner) {
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
    public int compareTo(AbstractMediaEntity o) {
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
