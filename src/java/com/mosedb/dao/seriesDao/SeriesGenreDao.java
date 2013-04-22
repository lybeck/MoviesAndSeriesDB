/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.dao.seriesDao;

import com.mosedb.dao.AbstractDao;
import java.sql.SQLException;
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
}
