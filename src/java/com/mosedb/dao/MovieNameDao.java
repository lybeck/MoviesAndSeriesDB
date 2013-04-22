/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.dao;

import com.mosedb.models.Movie;
import com.mosedb.models.Movie.LangId;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author llybeck
 */
public class MovieNameDao extends AbstractDao {

    public MovieNameDao() throws SQLException {
        super();
    }

    public boolean addMovieName(int movieid, Movie.LangId langid, String moviename) throws SQLException {
        String sql = "insert into mosedb.moviename (movieid, langid, moviename) values (?,cast(? as mosedb.langid),?)";
        return executeUpdate(sql, movieid, langid, moviename);
    }

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

    public boolean updateMovieNames(int movieid, Map<LangId, String> names) throws SQLException {
        boolean success = removeMovieNames(movieid);
        if (!success) {
            return false;
        }
        return addMovieNames(movieid, names);
    }

    public boolean removeMovieNames(int movieid) throws SQLException {
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
        result.close();
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
        result.close();
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
        result.close();
        return set;
    }

    public Set<Integer> getMovieIdsByName(List<String> list) throws SQLException {
        if (list.isEmpty()) {
            return null;
        }
        if (list.size() == 1) {
            return getMovieIdsByName(list.get(0));
        }
        String sql = "select movieid from mosedb.moviename "
                + "where lower(moviename) like lower('%" + list.get(0) + "%')";
        for (int i = 1; i < list.size(); i++) {
            sql += " or lower(moviename) like lower('%" + list.get(i) + "%')";
        }
        ResultSet result = executeQuery(sql);
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
}
