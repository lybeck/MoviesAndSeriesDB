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
public class UserService {

    private UserDao userDao;

    public UserService() {
        this.userDao = new UserDao();
    }

    public List<User> getAllUsers() {
        return null;
    }

    public User getUser(String username, String password) {
        try {
            return userDao.getUser(username, password);
        } catch (SQLException ex) {
            System.err.println("Error while trying to retrieve user. Error:");
            System.err.println(ex);
            return null;
        }
    }
}
