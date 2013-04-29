/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.dao.movieDao;

import com.mosedb.dao.AbstractDao;
import com.mosedb.models.Movie;
import com.mosedb.models.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Lasse
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
     * Removes the movie with the given {@code movieid} from the database.
     *
     * @param movieid Id of the movie to be removed.
     * @throws SQLException
     */
    public void removeMovie(int movieid) throws SQLException {
        String sql = "delete from mosedb.movie where movieid=?";
        executeUpdate(sql, movieid);
    }

    /**
     * Updates the {@code seen} column for the specified movie to the database.
     *
     * @param movieid Id of the movie.
     * @param seen The new value of the {@code seen} column.
     * @return {code true} if the update succeeded, {@code false} otherwise.
     * @throws SQLException
     */
    public boolean updateMovieSeen(int movieid, boolean seen) throws SQLException {
        String sql = "update mosedb.movie set (seen)=(?) where movieid=?";
        return executeUpdate(sql, seen, movieid);
    }

    /**
     * Updates the {@code year} column for the specified movie in the database.
     *
     * @param movieid Id of the movie.
     * @param year New value for the {@code year} column.
     * @return {code true} if the update succeeded, {@code false} otherwise.
     * @throws SQLException
     */
    public boolean updateMovieYear(int movieid, Integer year) throws SQLException {
        String sql = "update mosedb.movie set (movieyear)=(?) where movieid=?";
        return executeUpdate(sql, year, movieid);
    }

    /**
     * Retrieves the user's movies from the 'movie' table in the database.
     *
     * @param owner User, whose movies are queried.
     * @return A list of movies.
     * @throws SQLException
     */
    public List<Movie> getMovies(String owner) throws SQLException {
        return getMovies(null, owner);
    }

    /**
     * Retrieves the user's movies from the 'movie' table in the database.
     *
     * @param seenSearch If not {@code null}, movies with the same {@code seen}
     * value are queried.
     * @param owner User, whose movies are queried.
     * @return A list of movies.
     * @throws SQLException
     */
    public List<Movie> getMovies(Boolean seenSearch, String owner) throws SQLException {
        List<Object> searchTerms = new ArrayList<Object>(2);
        searchTerms.add(owner);
        String sql = "select movieid, movieyear, seen from mosedb.movie where owner=?";
        if (seenSearch != null) {
            sql += " and seen=?";
            searchTerms.add(seenSearch);
        }
        ResultSet result = executeQuery(sql, searchTerms.toArray());
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

    /**
     * Retrieves all stored movies from the database.
     *
     * @return A list of movies.
     * @throws SQLException
     */
    public List<Movie> getAllMovies() throws SQLException {
        return getAllMovies(null);
    }

    /**
     * Retrieves all stored movies corresponding to the {@code seen} variable
     * from the database.
     *
     * @param seenSearch If not {@code null}, movies with the same {@code seen}
     * value are queried.
     * @return A list of movies.
     * @throws SQLException
     */
    public List<Movie> getAllMovies(Boolean seenSearch) throws SQLException {
        List<Object> searchTerms = new ArrayList<Object>(1);
        String sql = "select movieid, owner, movieyear, seen from mosedb.movie";
        if (seenSearch != null) {
            sql += " where seen=?";
            searchTerms.add(seenSearch);
        }
        sql += " order by owner";
        ResultSet result = executeQuery(sql, searchTerms.toArray());
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

    /**
     * Retrieves a list of movies corresponding to the {@code movieids}.
     *
     * @param movieids A set of movieids to retrieve movies by.
     * @return A list of movies.
     * @throws SQLException
     */
    public List<Movie> getMovies(Set<Integer> movieids) throws SQLException {
        return getMovies(null, movieids, null);
    }

    /**
     * Retrieves a list of movies corresponding to the {@code movieids}.
     *
     * @param owner Owner of the movies.
     * @param movieids A set of movieids to retrieve movies by.
     * @return A list of movies.
     * @throws SQLException
     */
    public List<Movie> getMovies(String owner, Set<Integer> movieids) throws SQLException {
        return getMovies(owner, movieids, null);
    }

    /**
     * Retrieves a list of movies corresponding to the {@code movieids}.
     *
     * @param movieids A set of movieids to retrieve movies by.
     * @param seenSearch If not {@code null}, movies with the same {@code seen}
     * value are queried.
     * @return A list of movies.
     * @throws SQLException
     */
    public List<Movie> getMovies(Set<Integer> movieids, Boolean seenSearch) throws SQLException {
        return getMovies(null, movieids, seenSearch);
    }

    /**
     * Retrieves a list of movies corresponding to the {@code movieids}.
     *
     * @param owner Owner of the movies.
     * @param movieids A set of movieids to retrieve movies by.
     * @param seenSearch If not {@code null}, movies with the same {@code seen}
     * value are queried.
     * @return A list of movies.
     * @throws SQLException
     */
    public List<Movie> getMovies(String owner, Set<Integer> movieids, Boolean seenSearch) throws SQLException {
        if (seenSearch == null) {
            return getMovies(owner, movieids);
        }
        if (movieids == null || movieids.isEmpty()) {
            return new ArrayList<Movie>();
        }
        List<Object> searchTerms = new ArrayList<Object>();
        searchTerms.add(owner);
        String sql = "select movieid, movieyear, seen from mosedb.movie";
        sql += " where (";
        boolean first = true;
        for (Integer id : movieids) {
            if (first) {
                sql += "movieid=?" + id;
                first = false;
            } else {
                sql += " or movieid=?" + id;
            }
            searchTerms.add(id);
        }
        sql += ")";
        if (owner != null) {
            sql += " and owner=?";
            searchTerms.add(owner);
        }
        if (seenSearch != null) {
            sql += " and seen=?";
            searchTerms.add(seenSearch);
        }

        ResultSet result = executeQuery(sql, searchTerms.toArray());
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

    /**
     * Retrieves a movie's information from the 'movie' table by id.
     *
     * @param movieid Id of the movie.
     * @return A movie containing the information from the 'movie' table in the
     * database.
     * @throws SQLException
     */
    public Movie getMovieById(int movieid) throws SQLException {
        String sql = "select owner, movieyear, seen from mosedb.movie where movieid=?";
        ResultSet result = executeQuery(sql, movieid);
        if (!result.next()) {
            return null;
        }
        String owner = result.getString("owner");
        Integer movieYear = result.getInt("movieyear");
        if (movieYear == 0) {
            movieYear = null;
        }
        boolean seen = result.getBoolean("seen");
        result.close();
        return new Movie(movieid, owner, seen, movieYear);
    }
}
