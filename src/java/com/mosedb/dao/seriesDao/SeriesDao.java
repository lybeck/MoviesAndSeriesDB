/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.dao.seriesDao;

import com.mosedb.dao.AbstractDao;
import com.mosedb.models.Series;
import com.mosedb.models.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lasse
 */
public class SeriesDao extends AbstractDao {

    public SeriesDao() throws SQLException {
        super();
    }

    /**
     * Retrieves all series information from the 'series' table in the database.
     *
     * @return A list of series.
     * @throws SQLException
     */
    public List<Series> getAllSeries() throws SQLException {
        String sql = "select seriesid, owner from mosedb.series order by owner";
        ResultSet result = executeQuery(sql);
        List<Series> seriesList = new ArrayList<Series>();
        while (result.next()) {
            int id = result.getInt("seriesid");
            String owner = result.getString("owner");
            seriesList.add(new Series(id, owner));
        }
        result.close();
        return seriesList;
    }

    /**
     * Retrieves all series information associated with {@code owner} from the
     * 'series' table in the database.
     *
     * @param owner user, whose series are queried.
     * @return A list of series.
     * @throws SQLException
     */
    public List<Series> getSeries(String owner) throws SQLException {
        String sql = "select seriesid from mosedb.series where owner=?";
        ResultSet result = executeQuery(sql, owner);
        List<Series> seriesList = new ArrayList<Series>();
        while (result.next()) {
            int id = result.getInt("seriesid");
            seriesList.add(new Series(id, owner));
        }
        result.close();
        return seriesList;
    }

    /**
     * Adds an empty entry to the table 'series' in the database.
     *
     * @param user The owner of the series.
     * @return The seriesid of the added series if the addition succeeded,
     * {@code -1} otherwise.
     * @throws SQLException
     */
    public int addSeries(User user) throws SQLException {
        String sql = "insert into mosedb.series (owner) values (?) returning seriesid";
        ResultSet result = executeQuery(sql, user.getUsername());
        if (!result.next()) {
            return -1;
        }
        int id = result.getInt("seriesid");
        result.close();
        return id;
    }

    /**
     * Removes a series from the database.
     *
     * @param seriesid Id of the series.
     * @throws SQLException
     */
    public void removeSeries(int seriesid) throws SQLException {
        String sql = "delete from mosedb.series where seriesid=?";
        executeUpdate(sql, seriesid);
    }

    /**
     * Retrieves the information associated with the {@code seriesid} from the
     * table 'series' in the database.
     *
     * @param seriesid The id of the series.
     * @return A series.
     * @throws SQLException
     */
    public Series getById(int seriesid) throws SQLException {
        String sql = "select owner from mosedb.series where seriesid=?";
        ResultSet result = executeQuery(sql, seriesid);
        if (!result.next()) {
            return null;
        }
        return new Series(seriesid, result.getString("owner"));
    }

    /**
     * Removes all episodes from a season from the database.
     *
     * @param seriesid Id of the series whose season is to be deleted.
     * @param seasonnumber Number of the season to be deleted.
     * @throws SQLException
     */
    public void removeSeason(int seriesid, int seasonnumber) throws SQLException {
        String sql = "delete from mosedb.episode where seriesid=? and seasonnumber=?";
        executeUpdate(sql, seriesid, seasonnumber);
    }
}
