/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.dao;

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
public class MovieGenreDao extends AbstractDao {

    public boolean addMovieGenre(int movieid, String genrename) throws SQLException {
        String sql = "insert into mosedb.moviegenre (movieid, genrename) values (?,?)";
        return executeUpdate(sql, movieid, genrename);
    }

    public boolean addMovieGenres(int movieid, List<String> genres) throws SQLException {
        boolean success = true;
        for (String genrename : genres) {
            success = addMovieGenre(movieid, genrename);
        }
        return success;
    }

    public boolean removeMovieGenre(int movieid, String genrename) throws SQLException {
        String sql = "delete from mosedb.moviegenre where movieid=? and genrename=?";
        return executeUpdate(sql, movieid, genrename);
    }

    public boolean removeMovieGenres(int movieid) throws SQLException {
        String sql = "delete from mosedb.moviegenre where movieid=?";
        return executeUpdate(sql, movieid);
    }

    public List<String> getMovieGenres(int movieid) throws SQLException {
        String sql = "select genrename from mosedb.moviegenre where movieid=? order by genrename";
        ResultSet result = executeQuery(sql, movieid);
        List<String> list = new ArrayList<String>();
        while (result.next()) {
            list.add(result.getString("genrename"));
        }
        return list;
    }

    public boolean isMovieGenre(int movieid, String genrename) throws SQLException {
        String sql = "select count(*) as isgenre from mosedb.moviegenre where movieid=? and genrename=?";
        ResultSet result = executeQuery(sql, movieid, genrename);
        if (!result.next()) {
            return false;
        }
        return result.getInt("isgenre") == 1;
    }

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
        return set;
    }
}
