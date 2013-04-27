/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.dao;

import com.mosedb.models.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lasse
 */
public class UserDao extends AbstractDao {

    public UserDao() throws SQLException {
        super();
    }

    /**
     * Retrieves all users from the database.
     *
     * @return List of all users, null if connection failed.
     */
    public List<User> getAllUsers() throws SQLException {
        String sql = "select username, firstname, lastname, admin from mosedb.users order by admin, username";
        ResultSet result = executeQuery(sql);
        List<User> list = new ArrayList<User>();
        String usr, fst, lst;
        boolean adm;
        while (result.next()) {
            usr = result.getString("username");
            fst = result.getString("firstname");
            lst = result.getString("lastname");
            adm = result.getBoolean("admin");
            list.add(new User(usr, fst, lst, adm));
        }
        result.close();
        return list;
    }

    public User getUser(String username, String password) throws SQLException {
        String sql = "select username, firstname, lastname, admin from mosedb.users"
                + " where username = ? and password = ?";
        ResultSet result = executeQuery(sql, username, password);
        String usr, fst, lst;
        boolean adm;
        User user = null;
        if (result.next()) {
            usr = result.getString("username");
            fst = result.getString("firstname");
            lst = result.getString("lastname");
            adm = result.getBoolean("admin");
            user = new User(usr, fst, lst, adm);
        }
        result.close();
        return user;
    }

    public boolean addUser(User user, String password) throws SQLException {
        String sql = "insert into mosedb.users (username, password, firstname, lastname, admin) "
                + "values (?,?,?,?,?)";
        return executeUpdate(sql, user.getUsername(), password, user.getFirstName(), user.getLastName(), user.isAdmin());
    }
    
    public void deleteUser(String username) throws SQLException {
        String sql = "delete from mosedb.users where username=?";
        executeUpdate(sql, username);
    }

    public boolean updateUser(String username, User updatedUser, String newPassword) throws SQLException {
        deleteUser(username);
        return addUser(updatedUser, newPassword);
    }
}
