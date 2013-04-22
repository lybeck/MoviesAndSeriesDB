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
 * @author llybeck
 */
public class SeriesDao extends AbstractDao {

    public SeriesDao() throws SQLException {
        super();
    }

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

    public void removeSeries(int id) throws SQLException {
        String sql = "delete from mosedb.series where seriesid=?";
        executeUpdate(sql, id);
    }
}
