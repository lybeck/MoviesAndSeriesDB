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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author llybeck
 */
public class MovieDao extends AbstractDao {

    public MovieDao() throws SQLException {
        super();
    }

    /**
     * Adds a movie entry to the database.
     *
     * @param user The owner of the movie.
     * @param seen Status of the movie, seen or not.
     * @return The movieid of the movie, -1 if failed.
     * @throws SQLException
     */
    public int addMovie(User user, boolean seen) throws SQLException {
        String sql = "insert into mosedb.movie (owner,seen) values (?,?) returning movieid";
        ResultSet result = executeQuery(sql, user.getUsername(), seen);
        if (!result.next()) {
            return -1;
        }
        int id = result.getInt("movieid");
        result.close();
        return id;
    }

    /**
     * Adds a movie entry to the database.
     *
     * @param user The owner of the movie.
     * @param movieyear Year the movie was released.
     * @param seen Status of the movie, seen or not.
     * @return The movieid of the movie, -1 if failed.
     * @throws SQLException
     */
    public int addMovie(User user, int movieyear, boolean seen) throws SQLException {
        String sql = "insert into mosedb.movie (owner,movieyear,seen) values (?,?,?) returning movieid";
        ResultSet result = executeQuery(sql, user.getUsername(), movieyear, seen);
        if (!result.next()) {
            return -1;
        }
        int id = result.getInt("movieid");
        result.close();
        return id;
    }

    /**
     * Removes the movie with the given {@code movieid} from the database. <p>
     * Note that this method <b>only</b> removes the entry from the mosedb.movie
     * table, and that the removal will most likely fail if entries from the
     * other movie related tables are not removed first.
     *
     * @param movieid Id of the movie to be removed.
     * @throws SQLException
     */
    public void removeMovie(int movieid) throws SQLException {
        String sql = "delete from mosedb.movie where movieid=?";
        executeUpdate(sql, movieid);
    }

    public boolean updateMovieSeen(int movieid, boolean seen) throws SQLException {
        String sql = "update mosedb.movie set (seen)=(?) where movieid=?";
        return executeUpdate(sql, seen, movieid);
    }

    public boolean updateMovieYear(int movieid, Integer newyear) throws SQLException {
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
        result.close();
        return list;
    }

    public List<Movie> getMovies(String owner, Boolean seenSearch) throws SQLException {
        if (seenSearch == null) {
            return getMovies(owner);
        }
        String sql = "select movieid, movieyear, seen from mosedb.movie where owner=? and seen=?";
        ResultSet result = executeQuery(sql, owner, seenSearch);
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
        result.close();
        return list;
    }

    public List<Movie> getAllMovies() throws SQLException {
        String sql = "select movieid, owner, movieyear, seen from mosedb.movie order by owner";
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
        result.close();
        return list;
    }

    public List<Movie> getAllMovies(Boolean seenSearch) throws SQLException {
        if (seenSearch == null) {
            return getAllMovies();
        }
        String sql = "select movieid, owner, movieyear, seen from mosedb.movie where seen=? order by owner";
        ResultSet result = executeQuery(sql, seenSearch);
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
        result.close();
        return list;
    }

    public List<Movie> getMovies(String owner, Set<Integer> movieids) throws SQLException {
        if (movieids == null || movieids.isEmpty()) {
            return new ArrayList<Movie>();
        }
        String sql = "select movieid, movieyear, seen from mosedb.movie where owner=?";
        sql += " and (";
        boolean first = true;
        for (Integer id : movieids) {
            if (first) {
                sql += "movieid=" + id;
                first = false;
            } else {
                sql += " or movieid=" + id;
            }
        }
        sql += ")";
//        System.out.println(sql);
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
        result.close();
        return list;
    }

    public List<Movie> getMovies(String owner, Set<Integer> movieids, Boolean seenSearch) throws SQLException {
        if (seenSearch == null) {
            return getMovies(owner, movieids);
        }
        if (movieids == null || movieids.isEmpty()) {
            return new ArrayList<Movie>();
        }
        String sql = "select movieid, movieyear, seen from mosedb.movie where owner=? and seen=?";
        sql += " and (";
        boolean first = true;
        for (Integer id : movieids) {
            if (first) {
                sql += "movieid=" + id;
                first = false;
            } else {
                sql += " or movieid=" + id;
            }
        }
        sql += ")";
//        System.out.println(sql);
        ResultSet result = executeQuery(sql, owner, seenSearch);
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
        result.close();
        return list;
    }

    public List<Movie> getMovies(Set<Integer> movieids) throws SQLException {
        if (movieids == null || movieids.isEmpty()) {
            return new ArrayList<Movie>();
        }
        String sql = "select movieid, owner, movieyear, seen from mosedb.movie";
        sql += " where ";
        boolean first = true;
        for (Integer id : movieids) {
            if (first) {
                sql += "movieid=" + id;
                first = false;
            } else {
                sql += " or movieid=" + id;
            }
        }
        sql += " order by owner";
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
        result.close();
        return list;
    }

    public List<Movie> getMovies(Set<Integer> movieids, Boolean seenSearch) throws SQLException {
        if (seenSearch == null) {
            return getMovies(movieids);
        }
        if (movieids == null || movieids.isEmpty()) {
            return new ArrayList<Movie>();
        }
        String sql = "select movieid, owner, movieyear, seen from mosedb.movie";
        sql += " where (";
        boolean first = true;
        for (Integer id : movieids) {
            if (first) {
                sql += "movieid=" + id;
                first = false;
            } else {
                sql += " or movieid=" + id;
            }
        }
        sql += ") and seen=? order by owner";
        ResultSet result = executeQuery(sql, seenSearch);
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
        result.close();
        return list;
    }

    public Movie getMovieById(int id) throws SQLException {
        String sql = "select owner, movieyear, seen from mosedb.movie where movieid=?";
        ResultSet result = executeQuery(sql, id);
        if (!result.next()) {
            return null;
        }
        String owner = result.getString("owner");
        Integer movieYear = result.getInt("movieyear");
        if (movieYear == 0) {
            movieYear = null;
        }
        boolean seen = result.getBoolean("seen");
        return new Movie(id, owner, seen, movieYear);
    }
}
