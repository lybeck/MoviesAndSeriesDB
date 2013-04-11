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

    public List<String> getAllGenres() {
        try {
            GenreDao genreDao = new GenreDao();
            List<String> allGenres = genreDao.getAllGenres();
            genreDao.closeConnection();
            return allGenres;
        } catch (SQLException ex) {
            System.err.println("Failed to fetch genres.");
            System.err.println("Error:");
            System.err.println(ex);
            return null;
        }
    }
}
