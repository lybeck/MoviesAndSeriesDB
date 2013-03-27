/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.dao;

import com.mosedb.databaseconnection.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author llybeck
 */
public abstract class AbstractDao {

    protected Connection getConnection() throws SQLException {
        return ConnectionManager.getConnection();
    }

    protected ResultSet processStatement(PreparedStatement pst) throws SQLException {
        try {
            return pst.executeQuery();
        } catch (SQLException e) {
            if (e.getClass() == SQLTimeoutException.class) {
                System.err.println("Connection to database timed out!");
            } else {
                System.err.println("Connection to database could not be made!");
            }
            throw e;
        }
    }
}
