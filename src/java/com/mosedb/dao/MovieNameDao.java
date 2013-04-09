/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.dao;

import com.mosedb.models.Movie;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author llybeck
 */
public class MovieNameDao extends AbstractDao {

    public boolean addMovieName(int movieid, Movie.LangId langid, String moviename) throws SQLException {
        String sql = "insert into mosedb.moviename (movieid, langid, moviename) values (?,cast(? as mosedb.langid),?)";
        return executeUpdate(sql, movieid, langid, moviename);
    }

    public boolean updateMovieName(int movieid, Movie.LangId langid, String moviename) throws SQLException {
        String sql = "update mosedb.moviename set (moviename)=(?) where movieid=? and langid=cast(? as mosedb.langid)";
        return executeUpdate(sql, moviename, movieid, langid);
    }

    public boolean removeMovieName(int movieid) throws SQLException {
        String sql = "delete from mosedb.moviename where movieid=?";
        return executeUpdate(sql, movieid);
    }

    public boolean removeMovieName(int movieid, Movie.LangId langid) throws SQLException {
        String sql = "delete from mosedb.moviename where movieid=? and langid=cast(? as mosedb.langid)";
        return executeUpdate(sql, movieid, langid);
    }

    public String getMovieName(int movieid, Movie.LangId langid) throws SQLException {
        String sql = "select moviename from mosedb.moviename where movieid=? and langid=cast(? as mosedb.langid)";
        ResultSet result = executeQuery(sql, movieid, langid);
        String name = null;
        if (result.next()) {
            name = result.getString("moviename");
        }
        return name;
    }

    public Map<Movie.LangId, String> getMovieNames(int movieid) throws SQLException {
        String sql = "select langid, moviename from mosedb.moviename where movieid=?";
        ResultSet result = executeQuery(sql, movieid);
        Map<Movie.LangId, String> map = new EnumMap<Movie.LangId, String>(Movie.LangId.class);
        while (result.next()) {
            Movie.LangId id = Movie.getLangId(result.getString("langid"));
            String name = result.getString("moviename");
            map.put(id, name);
        }
        return map;
    }

    public Set<Integer> getMovieIdsByName(String name) throws SQLException {
        String sql = "select movieid from mosedb.moviename "
                + "where lower(moviename) like lower('%" + name + "%')";
        ResultSet result = executeQuery(sql);
        Set<Integer> set = new HashSet<Integer>();
        while (result.next()) {
            int id = result.getInt("movieid");
            if (!set.contains(id)) {
                set.add(id);
            }
        }
        return set;
    }
}
