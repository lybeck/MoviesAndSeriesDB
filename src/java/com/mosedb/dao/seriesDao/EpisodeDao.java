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

    /**
     * Retrieves all episodes of a series.
     *
     * @param seriesid The id of the series.
     * @return A list of episodes.
     * @throws SQLException
     */
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
        result.close();
        return episodeList;
    }

    /**
     * Adds an episode to the database.
     *
     * @param seriesid Id of the series.
     * @param seasonnumber Number of the season.
     * @param episodenumber Number of the episode.
     * @param seen Tag if the episode is seen or not.
     * @param year The year of the episode.
     * @return {@code true} if the addition succeeded, {@code false} otherwise.
     * @throws SQLException
     */
    public boolean addEpisode(int seriesid, int seasonnumber, int episodenumber, boolean seen, Integer year) throws SQLException {
        String sql = "insert into mosedb.episode (seriesid,seasonnumber,episodenumber,episodeyear,seen) "
                + "values (?,?,?,?,?)";
        return executeUpdate(sql, seriesid, seasonnumber, episodenumber, year, seen);
    }

    /**
     * Updates the info of the episode.
     *
     * @param episode Episode to be updated, containing the new data.
     * @return {@code true} if the update succeeded, {@code false} otherwise.
     * @throws SQLException
     */
    public boolean updateEpisode(Episode episode) throws SQLException {
        String sql = "update mosedb.episode set (episodename,episodeyear,seen)=(?,?,?) "
                + "where seriesid=? and seasonnumber=? and episodenumber=?";
        return executeUpdate(sql, episode.getEpisodeName(), episode.getEpisodeYear(), episode.isSeen(),
                episode.getSeriesId(), episode.getSeasonNumber(), episode.getEpisodeNumber());
    }
}
