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
public class SeriesGenreDao extends AbstractDao {

    public SeriesGenreDao() throws SQLException {
        super();
    }

    /**
     * Adds genres to a series.
     *
     * @param seriesid Id of the series.
     * @param genreList The genres to be associated with the series.
     * @return {@code true} if the addition succeeded, {@code false} otherwise.
     * @throws SQLException
     */
    public boolean addGenres(int seriesid, List<String> genreList) throws SQLException {
        boolean success;
        for (String genre : genreList) {
            success = addGenre(seriesid, genre);
            if (!success) {
                return false;
            }
        }
        return true;
    }

    /**
     * Adds a genre to the series.
     *
     * @param seriesid Id of the series.
     * @param genre Genre to be associated with the series.
     * @return {@code true} if the addition succeeded, {@code false} otherwise.
     * @throws SQLException
     */
    public boolean addGenre(int seriesid, String genre) throws SQLException {
        String sql = "insert into mosedb.seriesgenre (seriesid,genrename) values (?,?)";
        return executeUpdate(sql, seriesid, genre);
    }

    /**
     * Retrieves all series corresponding to the genre from the database.
     *
     * @param search Genre to be queried by.
     * @param user User whose series are queried. If the user is admin all
     * users' series are queried.
     * @return A list of series.
     * @throws SQLException
     */
    public List<Series> getSeriesByGenre(String search, User user) throws SQLException {
        String sql = "select distinct s.seriesid, s.owner from mosedb.series s, mosedb.seriesgenre sg "
                + "where s.seriesid=sg.seriesid and lower(genrename) like lower(?)";
        ResultSet result;
        if (user.isAdmin()) {
            result = executeQuery(sql, search);
        } else {
            sql += " and s.owner=?";
            result = executeQuery(sql, search, user.getUsername());
        }
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
     * Retrieves all genres associated with the series.
     *
     * @param seriesid Id of the series.
     * @return A list containing all genres associated with the series.
     * @throws SQLException
     */
    public List<String> getGenresById(int seriesid) throws SQLException {
        String sql = "select genrename from mosedb.seriesgenre where seriesid=?";
        ResultSet result = executeQuery(sql, seriesid);
        List<String> genres = new ArrayList<String>();
        while (result.next()) {
            genres.add(result.getString("genrename"));
        }
        result.close();
        return genres;
    }

    /**
     * Updates the genres associated with the series.
     *
     * @param seriesid Id of the series.
     * @param genreList List of the new genres associated with the series.
     * @return {@code true} if the addition succeeded, {@code false} otherwise.
     * @throws SQLException
     */
    public boolean updateGenres(int seriesid, List<String> genreList) throws SQLException {
        removeGenres(seriesid);
        if (genreList.isEmpty()) {
            return true;
        }
        return addGenres(seriesid, genreList);
    }

    /**
     * Removes all genre information associated with the series.
     *
     * @param seriesid Id of the series.
     * @throws SQLException
     */
    private void removeGenres(int seriesid) throws SQLException {
        String sql = "delete from mosedb.seriesgenre where seriesid=?";
        executeUpdate(sql, seriesid);
    }
}
