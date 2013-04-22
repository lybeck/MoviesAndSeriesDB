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
import java.util.List;

/**
 *
 * @author llybeck
 */
public class SeriesDao extends AbstractDao {

    public SeriesDao() throws SQLException {
        super();
    }

    public int addSeries(User user) throws SQLException {
        String sql = "insert into mosedb.series (owner) values (?) returning seriesid";
        ResultSet result = executeQuery(sql, user.getUsername());
        if (!result.next()) {
            return -1;
        }
        return result.getInt("seriesid");
    }

    public void removeSeries(int id) throws SQLException {
        String sql = "delete from mosedb.series where seriesid=?";
        executeUpdate(sql, id);
    }

    public List<Series> getAllSeries() throws SQLException {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public List<Series> getSeries(String username) throws SQLException {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
