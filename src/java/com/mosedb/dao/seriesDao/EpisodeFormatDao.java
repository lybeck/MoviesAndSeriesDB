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

    /**
     *
     * @throws SQLException
     */
    public EpisodeFormatDao() throws SQLException {
    }

    /**
     *
     * @param seriesid
     * @param seasonnumber
     * @param episodenumber
     * @param formatid
     * @return
     * @throws SQLException
     */
    public boolean addSeriesFormat(int seriesid, int seasonnumber, int episodenumber, int formatid) throws SQLException {
        String sql = "insert into mosedb.episodeformat (seriesid,seasonnumber,episodenumber,formatid) values (?,?,?,?)";
        return executeUpdate(sql, seriesid, seasonnumber, episodenumber, formatid);
    }

    /**
     *
     * @param episode
     * @return
     * @throws SQLException
     */
    public Format getFormat(Episode episode) throws SQLException {
        String sql = "select f.mediaformat from mosedb.format f, mosedb.episodeformat ef "
                + "where f.formatid=ef.formatid and ef.seriesid=? and ef.seasonnumber=? and ef.episodenumber=?";
        ResultSet result = executeQuery(sql, episode.getSeriesId(), episode.getSeasonNumber(), episode.getEpisodeNumber());
        if(!result.next()) {
            return null;
        }
        MediaFormat mediaFormat = MediaFormat.valueOf(result.getString("mediaformat"));
        return new Format(mediaFormat);
    }

    /**
     *
     * @param episode
     * @throws SQLException
     */
    public void removeFormats(Episode episode) throws SQLException {
        String sql = "delete from mosedb.format f using mosedb.episodeformat ef "
                + "where f.formatid=ef.formatid and ef.seriesid=? and ef.seasonnumber=? and ef.episodenumber=?";
        executeUpdate(sql, episode.getSeriesId(), episode.getSeasonNumber(), episode.getEpisodeNumber());
    }
    
}
