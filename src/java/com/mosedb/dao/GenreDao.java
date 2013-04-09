/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author llybeck
 */
public class GenreDao extends AbstractDao {

    public List<String> getAllGenres() throws SQLException {
        String sql = "select genrename from mosedb.genre order by genrename";
        ResultSet result = executeQuery(sql);
        List<String> list = new ArrayList<String>();
        while (result.next()) {
            list.add(result.getString("genrename"));
        }
        return list;
    }
}
