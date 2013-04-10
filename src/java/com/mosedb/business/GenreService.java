/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.business;

import com.mosedb.dao.GenreDao;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author llybeck
 */
public class GenreService {

    private GenreDao genreDao;

    public GenreService() {
        this.genreDao = new GenreDao();
    }

    public List<String> getAllGenres() {
        try {
            return genreDao.getAllGenres();
        } catch (SQLException ex) {
            System.err.println("Failed to fetch genres.");
            System.err.println("Error:");
            System.err.println(ex);
            return null;
        }
    }
}
