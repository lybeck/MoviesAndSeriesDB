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
public class Series extends AbstractMediaEntity {

    public Series(Map<LangId, String> names, List<String> genres) {
        super(names, genres);
    }

    public Series(int id, Map<LangId, String> names, String owner, List<String> genres) {
        super(id, names, owner, genres);
    }

    public Series(int id, String owner) {
        super(id, owner);
    }

    @Override
    public String toString() {
        return "seriesid:\t" + getId() + "\n"
                + "names:\t" + getNames() + "\n"
                + "owner:\t" + getOwner() + "\n"
                + "genres:\t" + getGenres() + "\n";
    }
}
