/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.business;

import com.mosedb.dao.GenreDao;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Lasse
 */
public class GenreService extends AbstractService {

    /**
     * Retrieves a list of all genres stored in the database.
     *
     * @return List of genres. {@code null} if connection to database fails.
     */
    public List<String> getAllGenres() {
        try {
            GenreDao genreDao = new GenreDao();
            List<String> allGenres = genreDao.getAllGenres();
            genreDao.closeConnection();
            return allGenres;
        } catch (SQLException ex) {
            reportError("Failed to fetch genres.", ex);
            return null;
        }
    }
}
