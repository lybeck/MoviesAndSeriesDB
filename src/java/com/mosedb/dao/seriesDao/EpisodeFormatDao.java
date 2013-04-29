/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.dao.seriesDao;

import com.mosedb.dao.AbstractDao;
import com.mosedb.models.Episode;
import com.mosedb.models.Format;
import com.mosedb.models.Format.MediaFormat;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Lasse
 */
public class EpisodeFormatDao extends AbstractDao {

    public EpisodeFormatDao() throws SQLException {
    }

    /**
     * Adds format information to an episode.
     *
     * @param seriesid Id of the series.
     * @param seasonnumber Number of the season.
     * @param episodenumber Number of the episode.
     * @param formatid Id of the format in table 'format' in the database.
     * @return {@code true} if addition succeeded, {@code false} otherwise.
     * @throws SQLException
     */
    public boolean addEpisodeFormat(int seriesid, int seasonnumber, int episodenumber, int formatid) throws SQLException {
        String sql = "insert into mosedb.episodeformat (seriesid,seasonnumber,episodenumber,formatid) values (?,?,?,?)";
        return executeUpdate(sql, seriesid, seasonnumber, episodenumber, formatid);
    }

    /**
     * Retrieves format information associated with the episode.
     *
     * @param episode Episode, whose format info is queried.
     * @return Format information.
     * @throws SQLException
     */
    public Format getFormat(Episode episode) throws SQLException {
        String sql = "select f.mediaformat from mosedb.format f, mosedb.episodeformat ef "
                + "where f.formatid=ef.formatid and ef.seriesid=? and ef.seasonnumber=? and ef.episodenumber=?";
        ResultSet result = executeQuery(sql, episode.getSeriesId(), episode.getSeasonNumber(), episode.getEpisodeNumber());
        if (!result.next()) {
            return null;
        }
        MediaFormat mediaFormat = MediaFormat.valueOf(result.getString("mediaformat"));
        result.close();
        return new Format(mediaFormat);
    }

    /**
     * Removes all format information associated with the episode.
     *
     * @param episode Episode whose format info is to be removed.
     * @throws SQLException
     */
    public void removeFormats(Episode episode) throws SQLException {
        String sql = "delete from mosedb.format f using mosedb.episodeformat ef "
                + "where f.formatid=ef.formatid and ef.seriesid=? and ef.seasonnumber=? and ef.episodenumber=?";
        executeUpdate(sql, episode.getSeriesId(), episode.getSeasonNumber(), episode.getEpisodeNumber());
    }

    public void removeFormats(int seriesid, int seasonnumber) throws SQLException {
        String sql = "delete from mosedb.format f using mosedb.episodeformat ef "
                + "where f.formatid=ef.formatid and ef.seriesid=? and ef.seasonnumber=?";
        executeUpdate(sql, seriesid, seasonnumber);
    }
}
