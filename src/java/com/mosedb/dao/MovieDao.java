/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.dao;

import com.mosedb.business.UserService;
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

    public int addMovie(User user, boolean seen) throws SQLException {
        String sql = "insert into mosedb.movie (owner,seen) values (?,?)";
        executeUpdate(sql, user.getUsername(), seen);
        return getMovieId(user);
    }
    
    public static void main(String[] args) throws SQLException {
        User lasse = new UserService().getUser("lasse", "salasana");
        new MovieDao().addMovie(lasse, 1989, true);
    }

    public int addMovie(User user, int movieyear, boolean seen) throws SQLException {
        String sql = "insert into mosedb.movie (owner,movieyear,seen) values (?,?,?)";
        executeUpdate(sql, user.getUsername(), movieyear, seen);
        return getMovieId(user);
    }
    
    public void removeMovie(int movieid){
        
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
