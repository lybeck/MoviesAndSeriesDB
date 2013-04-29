/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.models;

import com.mosedb.models.LangId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        Collections.sort(episodes);
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }

    public boolean isSeen() {
        for (Episode episode : episodes) {
            if (!episode.isSeen()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "seriesid:\t" + getId() + "\n"
                + "names:\t" + getNames() + "\n"
                + "owner:\t" + getOwner() + "\n"
                + "genres:\t" + getGenres() + "\n";
    }

    public Set<Integer> getSeasonNumbers() {
        Set<Integer> seasons = new HashSet<Integer>();
        for (Episode episode : episodes) {
            if (!seasons.contains(episode.getSeasonNumber())) {
                seasons.add(episode.getSeasonNumber());
            }
        }
        return seasons;
    }
    
    public List<Integer> getSeasonNumbersAsList() {
        List<Integer> seasons = new ArrayList<Integer>(getSeasonNumbers());
        Collections.sort(seasons);
        return seasons;
    }
}
