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
 * @author llybeck
 */
public class FormatDao extends AbstractDao {

    public int addFormat(Format.MediaFormat mediaformat) throws SQLException {
        String sql = "insert into mosedb.format (mediaformat) values (cast(? as mosedb.mediaformat)) returning formatid";
        ResultSet result = executeQuery(sql, mediaformat);
        if (!result.next()) {
            return -1;
        }
        return result.getInt("formatid");
    }

    public int addFormatDigitalCopy(String filetype) throws SQLException {
        String sql = "insert into mosedb.format (mediaformat, filetype) "
                + "values (cast(? as mosedb.mediaformat),?) "
                + "returning formatid";
        ResultSet result = executeQuery(sql, Format.MediaFormat.dc, filetype);
        if (!result.next()) {
            return -1;
        }
        return result.getInt("formatid");
    }

    public int addFormatDigitalCopy(String filetype, int resox, int resoy) throws SQLException {
        String sql = "insert into mosedb.format (mediaformat, filetype, resox, resoy) "
                + "values (cast(? as mosedb.mediaformat),?,?,?) "
                + "returning formatid";
        ResultSet result = executeQuery(sql, Format.MediaFormat.dc, filetype, resox, resoy);
        if (!result.next()) {
            return -1;
        }
        return result.getInt("formatid");
    }

    public boolean removeFormat(int formatid) throws SQLException {
        String sql = "delete from mosedb.format where formatid=?";
        return executeUpdate(sql, formatid);
    }

    public Format getFormat(int formatid) throws SQLException {
        String sql = "select mediaformat, filetype, resox, resoy from mosedb.format where formatid=?";
        ResultSet result = executeQuery(sql, formatid);
        Format format = null;
        if (result.next()) {
            Format.MediaFormat mediaformat = Format.getMediaFormat(result.getString("mediaformat"));
            String filetype = result.getString("filetype");
            int resox = result.getInt("resox");
            int resoy = result.getInt("resoy");
            if (resox == 0 || resoy == 0) {
                format = new Format(formatid, mediaformat, filetype);
            } else {
                format = new Format(formatid, mediaformat, filetype, resox, resoy);
            }
        }
        return format;
    }
}
