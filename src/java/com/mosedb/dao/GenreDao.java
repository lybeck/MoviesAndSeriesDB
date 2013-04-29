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
 * @author Lasse
 */
public class GenreDao extends AbstractDao {

    public GenreDao() throws SQLException {
        super();
    }

    /**
     * Retrieves a list of all the genres in the database.
     *
     * @return A list of genres stored in the database.
     * @throws SQLException
     */
    public List<String> getAllGenres() throws SQLException {
        String sql = "select genrename from mosedb.genre order by genrename";
        ResultSet result = executeQuery(sql);
        List<String> list = new ArrayList<String>();
        while (result.next()) {
            list.add(result.getString("genrename"));
        }
        result.close();
        return list;
    }
}
