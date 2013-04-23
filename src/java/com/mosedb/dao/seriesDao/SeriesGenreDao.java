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
public class SeriesGenreDao extends AbstractDao {

    public SeriesGenreDao() throws SQLException {
        super();
    }

    public boolean addGenres(int id, List<String> genreList) throws SQLException {
        boolean success;
        for (String genre : genreList) {
            success = addGenre(id, genre);
            if (!success) {
                return false;
            }
        }
        return true;
    }

    public boolean addGenre(int id, String genre) throws SQLException {
        String sql = "insert into mosedb.seriesgenre (seriesid,genrename) values (?,?)";
        return executeUpdate(sql, id, genre);
    }

    public List<Series> getSeriesByGenre(String search, User user) throws SQLException {
        String sql = "select distinct s.seriesid, s.owner from mosedb.series s, mosedb.seriesgenre sg "
                + "where s.seriesid=sg.seriesid and lower(genrename) like lower(?)";
        ResultSet result;
        if (user.isAdmin()) {
            result = executeQuery(sql, search);
        } else {
            sql += " and s.owner=?";
            result = executeQuery(sql, search, user.getUsername());
        }List<Series> seriesList = new ArrayList<Series>();
        while (result.next()) {
            int id = result.getInt("seriesid");
            String owner = result.getString("owner");
            seriesList.add(new Series(id, owner));
        }
        return seriesList;
    }
}
