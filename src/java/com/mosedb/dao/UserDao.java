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
     * @return List of all users.
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

    /**
     * Retrieves the user with the specified {@code username} and
     * {@code password} from the database.
     *
     * @param username Username of the user.
     * @param password Password of the user.
     * @return The user's information, or {@code null} if username and password
     * do not match.
     * @throws SQLException
     */
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

    /**
     * Adds a new user to the database.
     *
     * @param user User to be added.
     * @param password The user's password to be stored.
     * @return {@code true} if the update succeeded, otherwise {@code false}.
     * @throws SQLException
     */
    public boolean addUser(User user, String password) throws SQLException {
        String sql = "insert into mosedb.users (username, password, firstname, lastname, admin) "
                + "values (?,?,?,?,?)";
        return executeUpdate(sql, user.getUsername(), password, user.getFirstName(), user.getLastName(), user.isAdmin());
    }

    /**
     * Deletes a user from the database.
     *
     * @param username Username of the user to be deleted.
     * @throws SQLException
     */
    public void deleteUser(String username) throws SQLException {
        String sql = "delete from mosedb.users where username=?";
        executeUpdate(sql, username);
    }

    /**
     * Updates the information of a user.
     *
     * @param username Username of the user to be updated.
     * @param updatedUser Information to be updated.
     * @param newPassword New password for the user.
     * @return {@code true} if information was successfully added, otherwise
     * {@code false}.
     * @throws SQLException
     */
    public boolean updateUser(String username, User updatedUser, String newPassword) throws SQLException {
        String sql = "update mosedb.users set (username,password,firstname,lastname,admin)="
                + "(?,?,?,?,?) where username=?";
        return executeUpdate(sql, updatedUser.getUsername(), newPassword, updatedUser.getFirstName(),
                updatedUser.getLastName(), updatedUser.isAdmin(), username);
    }
}
