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
    
    protected void executeUpdate(String sql, Object... values) throws SQLException{
        Connection connection = getConnection();
        PreparedStatement pst = connection.prepareStatement(sql);
        int i = 1;
        for (Object value : values) {
            pst.setObject(i++, value);
        }
        pst.executeUpdate();
        connection.close();
//        System.out.println(pst.toString());
    }
}
