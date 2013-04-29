/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.dao.movieDao;

import com.mosedb.dao.AbstractDao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Lasse
 */
public class MovieGenreDao extends AbstractDao {

    public MovieGenreDao() throws SQLException {
        super();
    }

    /**
     * Adds a genre to a movie.
     *
     * @param movieid Id of the movie.
     * @param genrename Name of the genre.
     * @return {@code true} if the addition succeeded, {@code false} otherwise.
     * @throws SQLException
     */
    public boolean addMovieGenre(int movieid, String genrename) throws SQLException {
        String sql = "insert into mosedb.moviegenre (movieid, genrename) values (?,?)";
        return executeUpdate(sql, movieid, genrename);
    }

    /**
     * Adds genres to a movie.
     *
     * @param movieid Id of the movie.
     * @param genres Names of the genres.
     * @return {@code true} if the addition succeeded, {@code false} otherwise.
     * @throws SQLException
     */
    public boolean addMovieGenres(int movieid, List<String> genres) throws SQLException {
        boolean success = true;
        for (String genrename : genres) {
            success = addMovieGenre(movieid, genrename);
        }
        return success;
    }

    /**
     * Removes all genre information associated with a movie.
     *
     * @param movieid Id of the movie.
     * @throws SQLException
     */
    public void removeMovieGenres(int movieid) throws SQLException {
        String sql = "delete from mosedb.moviegenre where movieid=?";
        executeUpdate(sql, movieid);
    }

    /**
     * Retrieves the genre information associated with the movie.
     *
     * @param movieid Id of the movie.
     * @return A list containing the movie's genres.
     * @throws SQLException
     */
    public List<String> getMovieGenres(int movieid) throws SQLException {
        String sql = "select genrename from mosedb.moviegenre where movieid=? order by genrename";
        ResultSet result = executeQuery(sql, movieid);
        List<String> list = new ArrayList<String>();
        while (result.next()) {
            list.add(result.getString("genrename"));
        }
        result.close();
        return list;
    }

    /**
     * Retrieves the movieids associated with the specified {@code genrename}.
     *
     * @param genrename Genrename to be queried.
     * @return A set of movieids.
     * @throws SQLException
     */
    public Set<Integer> getMovieIdsByGenre(String genrename) throws SQLException {
        String sql = "select movieid from mosedb.moviegenre where lower(genrename)=lower(?)";
        ResultSet result = executeQuery(sql, genrename);
        Set<Integer> set = new HashSet<Integer>();
        while (result.next()) {
            int id = result.getInt("movieid");
            if (!set.contains(id)) {
                set.add(id);
            }
        }
        result.close();
        return set;
    }

    /**
     * Updates the movies genre information.
     *
     * @param movieid The id of the movie.
     * @param genres The new genre information.
     * @return {@code true} if the update succeeded, {@code false} otherwise.
     * @throws SQLException
     */
    public boolean updateMovieGenres(int movieid, List<String> genres) throws SQLException {
        removeMovieGenres(movieid);
        if (genres.isEmpty()) {
            return true;
        }
        return addMovieGenres(movieid, genres);
    }
}
