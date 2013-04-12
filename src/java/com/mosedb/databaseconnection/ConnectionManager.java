/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.databaseconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author llybeck
 */
public class ConnectionManager {

    public static Connection getConnection() throws SQLException {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost/llybeck", "llybeck", "7efa67a6d7dd527f");

        return connection;
    }
}
