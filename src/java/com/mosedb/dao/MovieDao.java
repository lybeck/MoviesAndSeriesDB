/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.dao;

import com.mosedb.models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author llybeck
 */
public class MovieDao extends AbstractDao {

    /**
     * Adds a movie entry to the database.
     *
     * @param user The owner of the movie.
     * @param seen Status of the movie, seen or not.
     * @return The movieid of the movie.
     * @throws SQLException
     */
    public int addMovie(User user, boolean seen) throws SQLException {
        String sql = "insert into mosedb.movie (owner,seen) values (?,?)";
        executeUpdate(sql, user.getUsername(), seen);
        return getMovieId(user);
    }

    /**
     * Adds a movie entry to the database.
     *
     * @param user The owner of the movie.
     * @param movieyear Year the movie was released.
     * @param seen Status of the movie, seen or not.
     * @return The movieid of the movie.
     * @throws SQLException
     */
    public int addMovie(User user, int movieyear, boolean seen) throws SQLException {
        String sql = "insert into mosedb.movie (owner,movieyear,seen) values (?,?,?)";
        executeUpdate(sql, user.getUsername(), movieyear, seen);
        return getMovieId(user);
    }

    public boolean removeMovie(int movieid) throws SQLException {
        String sql = "delete from mosedb.movie where movieid=?";
        return executeUpdate(sql, movieid);
    }

    public boolean updateMovieSeen(int movieid, boolean seen) throws SQLException {
        String sql = "update mosedb.movie set (seen)=(?) where movieid=?";
        return executeUpdate(sql, seen, movieid);
    }

    public boolean updateMovieYear(int movieid, int newyear) throws SQLException {
        String sql = "update mosedb.movie set (movieyear)=(?) where movieid=?";
        return executeUpdate(sql, newyear, movieid);
    }

    /**
     * Finds the last (greatest) movieid associated with {@code user} from the
     * movie table.
     *
     * @param user User whose greatest movieid is searched for.
     * @return The greatest movieid in mosedb.movie with
     * {@code owner=user.username}.
     * @throws SQLException
     */
    private int getMovieId(User user) throws SQLException {
        Connection connection = getConnection();
        String sql = "select max(movieid) as id from mosedb.movie where owner=?";
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setString(1, user.getUsername());
        ResultSet result = pst.executeQuery();
        int id = -1;
        if (result.next()) {
            id = result.getInt("id");
        }
        connection.close();
        return id;
    }
}
