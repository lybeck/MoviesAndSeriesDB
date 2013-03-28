/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.dao;

import com.mosedb.models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author llybeck
 */
public class UserDao extends AbstractDao {

    /**
     * Retrieves all users from the database.
     *
     * @return List of all users, null if connection failed.
     */
    public List<User> getAllUsers() throws SQLException {
        List<User> list = null;
        Connection connection = getConnection();
        String sql = "select username, firstname, lastname, admin from mosedb.users;";
        PreparedStatement pst = connection.prepareStatement(sql);
        ResultSet result = processStatement(pst);
        list = new ArrayList<User>();
        String usr, fst, lst;
        boolean adm;
        while (result.next()) {
            usr = result.getString("username");
            fst = result.getString("firstname");
            lst = result.getString("lastname");
            adm = result.getBoolean("admin");
            list.add(new User(usr, fst, lst, adm));
        }
        connection.close();
        return list;
    }

    public User getUser(String username, String password) throws SQLException {
        
        User user = null;
        
        Connection connection = getConnection();
        String sql = "select username, firstname, lastname, admin from mosedb.users"
                + " where username = ? and password = ?";
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setString(1, username);
        pst.setString(2, password);
        ResultSet result = processStatement(pst);
        String usr, fst, lst;
        boolean adm;
        if (result.next()) {
            usr = result.getString("username");
            fst = result.getString("firstname");
            lst = result.getString("lastname");
            adm = result.getBoolean("admin");
            user = new User(usr, fst, lst, adm);
        }

        return user;
    }

    private static void printAllUsers() {
        List<User> list;
        try {
            list = new UserDao().getAllUsers();
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        if (list == null) {
            return;
        }
        System.out.println("Users in the database:");
        System.out.println();
        for (User user : list) {
            System.out.println(user);
            System.out.println();
        }
    }

    private static void printUser(String username, String password) {
        User user = null;
        try {
            user = new UserDao().getUser(username, password);
        } catch (SQLException ex) {
            System.err.println("Error trying to search user from database.");
        }
        System.out.println("With username: " + username + ", password: " + password);
        if (user == null) {
            System.out.println("No user found!");
        } else {
            System.out.println(user);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        printAllUsers();
        printUser("lasse", "salasana");
        printUser("roope", "koira");
        printUser("lasse", "gouasndf");
    }
}
