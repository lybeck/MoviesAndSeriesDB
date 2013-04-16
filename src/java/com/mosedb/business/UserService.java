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
 * @author llybeck
 */
public class UserService extends AbstractService {

    public List<User> getAllUsers() {
        try {
            UserDao userDao = new UserDao();
            List<User> allUsers = userDao.getAllUsers();
            userDao.closeConnection();
            return allUsers;
        } catch (SQLException ex) {
            reportError("Error while trying to retrieve users.", ex);
            return null;
        }
    }

    public User getUser(String username, String password) {
        try {
            UserDao userDao = new UserDao();
            User user = userDao.getUser(username, password);
            userDao.closeConnection();
            return user;
        } catch (SQLException ex) {
            reportError("Error while trying to retrieve user.", ex);
            return null;
        }
    }

    public void addUser(User user, String password) {
        try {
            UserDao userDao = new UserDao();
            userDao.addUser(user, password);
            userDao.closeConnection();
        } catch (SQLException ex) {
            reportError("Error while trying to add user.", ex);
        }
    }
}
