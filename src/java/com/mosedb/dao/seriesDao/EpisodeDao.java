/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.dao.seriesDao;

import com.mosedb.dao.AbstractDao;
import com.mosedb.models.Episode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lasse
 */
public class EpisodeDao extends AbstractDao {

    public EpisodeDao() throws SQLException {
        super();
    }

    public List<Episode> getAllEpisodes(int seriesid) throws SQLException {
        String sql = "select seasonnumber, episodenumber, episodename, episodeyear, seen from mosedb.episode "
                + "where seriesid=? order by seasonnumber, episodenumber";
        ResultSet result = executeQuery(sql, seriesid);
        List<Episode> episodeList = new ArrayList<Episode>();
        while (result.next()) {
            int seasonnumber = result.getInt("seasonnumber");
            int episodenumber = result.getInt("episodenumber");
            String name = result.getString("episodename");
            int episodeyear = result.getInt("episodeyear");
            boolean seen = result.getBoolean("seen");
            if (episodeyear != 0) {
                episodeList.add(new Episode(seriesid, seasonnumber, episodenumber, name, episodeyear, seen));
            } else {
                episodeList.add(new Episode(seriesid, seasonnumber, episodenumber, name, seen));
            }
        }
        return episodeList;
    }

    public boolean addEpisode(int seriesid, int seasonnumber, int episodenumber, boolean seen, Integer year) throws SQLException {
        String sql = "insert into mosedb.episode (seriesid,seasonnumber,episodenumber,episodeyear,seen) "
                + "values (?,?,?,?,?)";
        return executeUpdate(sql, seriesid,seasonnumber,episodenumber,year,seen);
    }
}
