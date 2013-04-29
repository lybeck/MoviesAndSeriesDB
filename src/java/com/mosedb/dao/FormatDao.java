/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.dao;

import com.mosedb.models.Format;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Lasse
 */
public class FormatDao extends AbstractDao {

    public FormatDao() throws SQLException {
        super();
    }

    /**
     * Adds a mediaformat to the database.
     *
     * @param mediaformat The mediaformat to be added.
     * @return The formatid of the format stored, or {@code -1} if the database
     * connection fails.
     * @throws SQLException
     */
    public int addFormat(Format.MediaFormat mediaformat) throws SQLException {
        String sql = "insert into mosedb.format (mediaformat) values (cast(? as mosedb.mediaformat)) returning formatid";
        ResultSet result = executeQuery(sql, mediaformat);
        if (!result.next()) {
            return -1;
        }
        int id = result.getInt("formatid");
        result.close();
        return id;
    }

    /**
     * Adds a digital copy mediaformat to the database.
     *
     * @param filetype The filetype of the digital copy.
     * @return The formatid of the format stored, or {@code -1} if the database
     * connection fails.
     * @throws SQLException
     */
    public int addFormatDigitalCopy(String filetype) throws SQLException {
        String sql = "insert into mosedb.format (mediaformat, filetype) "
                + "values (cast(? as mosedb.mediaformat),?) "
                + "returning formatid";
        ResultSet result = executeQuery(sql, Format.MediaFormat.dc, filetype);
        if (!result.next()) {
            return -1;
        }
        int id = result.getInt("formatid");
        result.close();
        return id;
    }

    /**
     * Adds a digital copy mediaformat to the database.
     *
     * @param filetype The filetype of the digital copy.
     * @param resox The width of the file in pixels.
     * @param resoy The height of the file in pixels.
     * @return The formatid of the format stored, or {@code -1} if the database
     * connection fails.
     * @throws SQLException
     */
    public int addFormatDigitalCopy(String filetype, int resox, int resoy) throws SQLException {
        String sql = "insert into mosedb.format (mediaformat, filetype, resox, resoy) "
                + "values (cast(? as mosedb.mediaformat),?,?,?) "
                + "returning formatid";
        ResultSet result = executeQuery(sql, Format.MediaFormat.dc, filetype, resox, resoy);
        if (!result.next()) {
            return -1;
        }
        int id = result.getInt("formatid");
        result.close();
        return id;
    }
}
