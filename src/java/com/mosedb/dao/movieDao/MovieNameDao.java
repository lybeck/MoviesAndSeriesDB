/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.dao.movieDao;

import com.mosedb.dao.AbstractDao;
import com.mosedb.models.LangId;
import com.mosedb.models.Movie;
import com.mosedb.models.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Lasse
 */
public class MovieNameDao extends AbstractDao {

    public MovieNameDao() throws SQLException {
        super();
    }

    /**
     * Adds a name to the movie to the database.
     *
     * @param movieid Id of the movie.
     * @param langid The names language.
     * @param moviename The name of the movie.
     * @return {@code true} if the addition succeeded, {@code false} otherwise.
     * @throws SQLException
     */
    public boolean addMovieName(int movieid, LangId langid, String moviename) throws SQLException {
        String sql = "insert into mosedb.moviename (movieid, langid, moviename) values (?,cast(? as mosedb.langid),?)";
        return executeUpdate(sql, movieid, langid, moviename);
    }

    /**
     * Adds the names specified in the {@code names} map to the movie to
     * database.
     *
     * @param movieid Id of the movie.
     * @param names Map containing the movies names.
     * @return {@code true} if the addition succeeded, {@code false} otherwise.
     * @throws SQLException
     */
    private boolean addMovieNames(int movieid, Map<LangId, String> names) throws SQLException {
        boolean success;
        for (LangId langId : names.keySet()) {
            success = addMovieName(movieid, langId, names.get(langId));
            if (!success) {
                return false;
            }
        }
        return true;
    }

    /**
     * Updates the movie's names.
     *
     * @param movieid Id of the movie.
     * @param names Map containing the movies new names.
     * @return {@code true} if the update succeeded, {@code false} otherwise.
     * @throws SQLException
     */
    public boolean updateMovieNames(int movieid, Map<LangId, String> names) throws SQLException {
        removeMovieNames(movieid);
        return addMovieNames(movieid, names);
    }

    /**
     * Removes all the names associated with the movie.
     *
     * @param movieid Id of the movie.
     * @throws SQLException
     */
    public void removeMovieNames(int movieid) throws SQLException {
        String sql = "delete from mosedb.moviename where movieid=?";
        executeUpdate(sql, movieid);
    }

    /**
     * Removes a specified name from a movie, if it exists.
     *
     * @param movieid Id of the movie.
     * @param langid The language of the name to be deleted.
     * @throws SQLException
     */
    public void removeMovieName(int movieid, LangId langid) throws SQLException {
        String sql = "delete from mosedb.moviename where movieid=? and langid=cast(? as mosedb.langid)";
        executeUpdate(sql, movieid, langid);
    }

    /**
     * Retrieves all the names associated with the movie.
     *
     * @param movieid Id of the movie.
     * @return A map containing all the movie's names.
     * @throws SQLException
     */
    public Map<LangId, String> getMovieNames(int movieid) throws SQLException {
        String sql = "select langid, moviename from mosedb.moviename where movieid=?";
        ResultSet result = executeQuery(sql, movieid);
        Map<LangId, String> map = new EnumMap<LangId, String>(LangId.class);
        while (result.next()) {
            LangId id = LangId.getLangId(result.getString("langid"));
            String name = result.getString("moviename");
            map.put(id, name);
        }
        result.close();
        return map;
    }

    /**
     * Retrieves all the movies with a name corresponding to the search term.
     * The search is <b>not</b> case sensitive.
     *
     * @param search The name to be searched for.
     * @param user The user whose movies are queried. If the user is an admin
     * all users' movies are queried.
     * @param seenParam If not {@code null}, movies with the same {@code seen}
     * value are queried.
     * @return A list of movies.
     * @throws SQLException
     */
    public List<Movie> getMoviesByName(String search, User user, Boolean seenParam) throws SQLException {
        return getMoviesByName(Arrays.asList(search), user, seenParam);
    }

    /**
     * Retrieves all the movies with a name corresponding to <b>all</b> the
     * search terms. The search is <b>not</b> case sensitive.
     *
     * @param search The name to be searched for.
     * @param user The user whose movies are queried. If the user is an admin
     * all users' movies are queried.
     * @param seenParam If not {@code null}, movies with the same {@code seen}
     * value are queried.
     * @return A list of movies.
     * @throws SQLException
     */
    public List<Movie> getMoviesByName(List<String> searchList, User user, Boolean seenParam) throws SQLException {
        String sql = "select distinct m.movieid, m.owner, m.seen from mosedb.movie m, mosedb.moviename mn "
                + "where m.movieid=mn.movieid and lower(mn.moviename) like lower(?)";
        for (int i = 1; i < searchList.size(); i++) {
            sql += " and lower(mn.moviename) like lower(?)";
        }
        List<Object> searchTerms = new ArrayList<Object>();
        for (String string : searchList) {
            searchTerms.add("%" + string + "%");
        }
        if (!user.isAdmin()) {
            sql += " and m.owner=?";
            searchTerms.add(user.getUsername());
        }
        if (seenParam != null) {
            sql += " and m.seen=?";
            searchTerms.add(seenParam);
        }
        ResultSet result = executeQuery(sql, searchTerms.toArray());
        List<Movie> movieList = new ArrayList<Movie>();
        while (result.next()) {
            int id = result.getInt("movieid");
            String owner = result.getString("owner");
            boolean seen = result.getBoolean("seen");
            Movie movie = new Movie(id, owner, seen);
            movie.setNames(getMovieNames(id));
            movieList.add(movie);
        }
        return movieList;
    }
}
