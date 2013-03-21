/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.databaseconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author llybeck
 */
public class ConnectionManager {

    public static Connection getConnection() {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        Connection connection;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost/llybeck", "llybeck", "7efa67a6d7dd527f");
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        return connection;
    }

    static void testQuery() throws SQLException {
        Connection connection = getConnection();
        if (connection == null) {
            System.err.println("Could not connect to database...");
            return;
        }
        String stm = "select * from mosedb.users";
        PreparedStatement pst = connection.prepareStatement(stm);
        ResultSet result = pst.executeQuery();
        while (result.next()) {
            System.out.println("Result:");
            for (int i = 1; i <= 4; i++) {
                System.out.println("  " + result.getString(i));
            }
            System.out.println("  " + result.getBoolean(5));
        }
        connection.close();
    }

    public static void main(String[] args) throws SQLException {
        System.out.println("Query test:");
        testQuery();
    }
}
