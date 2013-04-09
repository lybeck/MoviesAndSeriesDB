/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.dao;

import com.mosedb.databaseconnection.ConnectionManager;
import com.mosedb.models.Format;
import com.mosedb.models.Movie;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author llybeck
 */
public abstract class AbstractDao {

    protected Connection getConnection() throws SQLException {
        return ConnectionManager.getConnection();
    }

    protected boolean executeUpdate(String sql, Object... values) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement pst = connection.prepareStatement(sql);
        int i = 1;
        for (Object value : values) {
            if (value.getClass() == Movie.LangId.class || value.getClass() == Format.MediaFormat.class) {
                pst.setObject(i++, value.toString());
            } else {
                pst.setObject(i++, value);
            }
        }
//        System.out.println(pst);
        int result = pst.executeUpdate();
        connection.close();
        return result != 0;
    }

    protected ResultSet executeQuery(String sql, Object... values) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement pst = connection.prepareStatement(sql);
        int i = 1;
        for (Object value : values) {
            if (value.getClass() == Movie.LangId.class || value.getClass() == Format.MediaFormat.class) {
                pst.setObject(i++, value.toString());
            } else {
                pst.setObject(i++, value);
            }
        }
        ResultSet result = pst.executeQuery();
        connection.close();
        return result;
    }
}
