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

    public boolean addMovieName(int movieid, LangId langid, String moviename) throws SQLException {
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
        removeMovieNames(movieid);
        return addMovieNames(movieid, names);
    }

    public void removeMovieNames(int movieid) throws SQLException {
        String sql = "delete from mosedb.moviename where movieid=?";
        executeUpdate(sql, movieid);
    }

    public void removeMovieName(int movieid, LangId langid) throws SQLException {
        String sql = "delete from mosedb.moviename where movieid=? and langid=cast(? as mosedb.langid)";
        executeUpdate(sql, movieid, langid);
    }

    public String getMovieName(int movieid, LangId langid) throws SQLException {
        String sql = "select moviename from mosedb.moviename where movieid=? and langid=cast(? as mosedb.langid)";
        ResultSet result = executeQuery(sql, movieid, langid);
        String name = null;
        if (result.next()) {
            name = result.getString("moviename");
        }
        result.close();
        return name;
    }

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

    public List<Movie> getMoviesByName(String search, User user, Boolean seenParam) throws SQLException {
        String sql = "select distinct m.movieid, m.owner, m.seen from mosedb.movie m, mosedb.moviename mn "
                + "where m.movieid=mn.movieid and lower(mn.moviename) like lower(?)";
        ResultSet result;
        if (user.isAdmin() && seenParam == null) {
            result = executeQuery(sql, "%" + search + "%");
        } else if (seenParam == null) {
            sql += " and m.owner=?";
            result = executeQuery(sql, "%" + search + "%", user.getUsername());
        } else if (user.isAdmin()) {
            sql += " and m.seen=?";
            result = executeQuery(sql, "%" + search + "%", seenParam);
        } else {
            sql += " and m.owner=? and m.seen=?";
            result = executeQuery(sql, "%" + search + "%", user.getUsername(), seenParam);
        }

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
            searchTerms.add(seenParam + "");
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
