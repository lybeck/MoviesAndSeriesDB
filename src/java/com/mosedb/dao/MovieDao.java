/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.dao;

import com.mosedb.models.Movie;
import com.mosedb.models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        String sql = "insert into mosedb.movie (owner,seen) values (?,?) returning movieid";
        ResultSet result = executeQuery(sql, user.getUsername(), seen);
        if (!result.next()) {
            return -1;
        }
        return result.getInt("movieid");
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
        String sql = "insert into mosedb.movie (owner,movieyear,seen) values (?,?,?) returning movieid";
        ResultSet result = executeQuery(sql, user.getUsername(), movieyear, seen);
        if (!result.next()) {
            return -1;
        }
        return result.getInt("movieid");
    }

    /**
     * Removes the movie with the given {@code movieid} from the database. <p>
     * Note that this method <b>only</b> removes the entry from the mosedb.movie
     * table, and that the removal will most likely fail if entries from the
     * other movie related tables are not removed first.
     *
     * @param movieid Id of the movie to be removed.
     * @return True if the movie was successfully removed, otherwise false.
     * @throws SQLException
     */
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

    public List<Movie> getMovies(String owner) throws SQLException {
        String sql = "select movieid, movieyear, seen from mosedb.movie where owner=?";
        ResultSet result = executeQuery(sql, owner);
        List<Movie> list = new ArrayList<Movie>();
        while (result.next()) {
            int movieid = result.getInt("movieid");
            int movieyear = result.getInt("movieyear");
            boolean seen = result.getBoolean("seen");
            if (movieyear != 0) {
                list.add(new Movie(movieid, owner, seen, movieyear));
            } else {
                list.add(new Movie(movieid, owner, seen));
            }
        }
        return list;
    }

    public List<Movie> getAllMovies() throws SQLException {
        String sql = "select movieid, owner, movieyear, seen from mosedb.movie";
        ResultSet result = executeQuery(sql);
        List<Movie> list = new ArrayList<Movie>();
        while (result.next()) {
            int movieid = result.getInt("movieid");
            String owner = result.getString("owner");
            int movieyear = result.getInt("movieyear");
            boolean seen = result.getBoolean("seen");
            if (movieyear != 0) {
                list.add(new Movie(movieid, owner, seen, movieyear));
            } else {
                list.add(new Movie(movieid, owner, seen));
            }
        }
        return list;
    }
}
