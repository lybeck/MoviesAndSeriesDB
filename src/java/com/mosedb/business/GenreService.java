/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.business;

import com.mosedb.dao.GenreDao;
import java.util.List;

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
        
        return null;
    }
}
