/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.business;

import com.mosedb.dao.UserDao;
import com.mosedb.models.User;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Lasse
 */
public class UserService extends AbstractService {

    /**
     * Retrieves a list of all users from the database.
     *
     * @return A list of users, or {@code null} if the database query fails.
     */
    public List<User> getAllUsers() {
        UserDao userDao;
        try {
            userDao = new UserDao();
        } catch (SQLException ex) {
            reportConnectionError(ex);
            return null;
        }
        try {
            List<User> allUsers = userDao.getAllUsers();
            userDao.closeConnection();
            return allUsers;
        } catch (SQLException ex) {
            reportError("Error while trying to retrieve users.", ex);
            return null;
        }
    }

    /**
     * Retrieves the user with the specified {@code username} and
     * {@code password} from the database.
     *
     * @param username Username of the user.
     * @param password Password of the user.
     * @return The user's information, or {@code null} if username and password
     * do not match or the database collection fails.
     */
    public User getUser(String username, String password) {
        UserDao userDao;
        try {
            userDao = new UserDao();
        } catch (SQLException ex) {
            reportConnectionError(ex);
            return null;
        }
        try {
            User user = userDao.getUser(username, password);
            userDao.closeConnection();
            return user;
        } catch (SQLException ex) {
            reportError("Error while trying to retrieve user.", ex);
            return null;
        }
    }

    /**
     * Adds a user to the database.
     *
     * @param user User to be added.
     * @param password The user's password.
     * @return {@code true} if information was successfully added, otherwise
     * {@code false}.
     */
    public boolean addUser(User user, String password) {
        UserDao userDao;
        try {
            userDao = new UserDao();
        } catch (SQLException ex) {
            reportConnectionError(ex);
            return false;
        }
        try {
            boolean success = userDao.addUser(user, password);
            userDao.closeConnection();
            return success;
        } catch (SQLException ex) {
            reportError("Error while trying to add user.", ex);
            return false;
        }
    }

    /**
     * Permanently deletes a user from the database.
     *
     * @param username Username of the user to be removed.
     */
    public void deleteUser(String username) {
        UserDao userDao;
        try {
            userDao = new UserDao();
        } catch (SQLException ex) {
            reportConnectionError(ex);
            return;
        }
        try {
            userDao.deleteUser(username);
            userDao.closeConnection();
        } catch (SQLException ex) {
            reportError("Error while trying to delete user.", ex);
        }
    }

    /**
     * Updates the information of a user.
     *
     * @param username Username of the user to be updated.
     * @param updatedUser Information to be updated.
     * @param newPassword New password for the user.
     * @return {@code true} if information was successfully added, otherwise
     * {@code false}.
     */
    public boolean updateUser(String username, User updatedUser, String newPassword) {
        UserDao userDao;
        try {
            userDao = new UserDao();
        } catch (SQLException ex) {
            reportConnectionError(ex);
            return false;
        }
        try {
            boolean success = userDao.updateUser(username, updatedUser, newPassword);
            userDao.closeConnection();
            return success;
        } catch (SQLException ex) {
            reportError("Error while trying to update user.", ex);
            return false;
        }
    }
}
