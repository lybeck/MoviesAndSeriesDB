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
 * @author Lasse
 */
public class Series extends AbstractMediaEntity {

    private List<Episode> episodes;

    public Series(Map<LangId, String> names, List<String> genres) {
        this(0, names, null, genres);
    }

    public Series(int id, String owner) {
        this(id, null, owner, null);
    }

    public Series(int id, Map<LangId, String> names, String owner, List<String> genres) {
        super(id, names, owner, genres);
        episodes = new ArrayList<Episode>();
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }

    @Override
    public String toString() {
        return "seriesid:\t" + getId() + "\n"
                + "names:\t" + getNames() + "\n"
                + "owner:\t" + getOwner() + "\n"
                + "genres:\t" + getGenres() + "\n";
    }
}
