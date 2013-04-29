/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.business;

import com.mosedb.dao.UserDao;
import com.mosedb.models.User;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lasse
 */
public class UserService extends AbstractService {

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

    public boolean updateUser(User user, User updatedUser, String newPassword) {
        UserDao userDao;
        try {
            userDao = new UserDao();
        } catch (SQLException ex) {
            reportConnectionError(ex);
            return false;
        }
        try {
            boolean success = userDao.updateUser(user.getUsername(), updatedUser, newPassword);
            userDao.closeConnection();
            return success;
        } catch (SQLException ex) {
            reportError("Error while trying to update user.", ex);
            return false;
        }
    }
}
