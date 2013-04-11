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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author llybeck
 */
public abstract class AbstractDao {

    private Connection connection;

    public AbstractDao() throws SQLException {
        this.connection = ConnectionManager.getConnection();
    }

    protected boolean executeUpdate(String sql, Object... values) throws SQLException {
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
        return result != 0;
    }

    protected ResultSet executeQuery(String sql, Object... values) throws SQLException {
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
        return result;
    }
    
    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(AbstractDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
