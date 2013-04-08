/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.business;

import com.mosedb.dao.FormatDao;
import com.mosedb.dao.MovieDao;
import com.mosedb.dao.MovieFormatDao;
import com.mosedb.dao.MovieGenreDao;
import com.mosedb.dao.MovieNameDao;
import com.mosedb.models.Format;
import com.mosedb.models.Movie;
import com.mosedb.models.User;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author llybeck
 */
public class MovieService {

    public List<Movie> getMovies(User user) {

        List<Movie> movies;
        try {
            if (user.isAdmin()) {
                movies = new MovieDao().getAllMovies();
            } else {
                movies = new MovieDao().getMovies(user.getUsername());
            }
        } catch (SQLException ex) {
            System.err.println("Error while retrieving movies by username. Error:");
            System.err.println(ex);
            return null;
        }

        addInfoToMovies(movies);

        return movies;
    }

    private void addInfoToMovies(List<Movie> movies) {
        addNames(movies);
        addGenres(movies);
        addFormats(movies);

        printMovies(movies);

    }

    private void addNames(List<Movie> movies) {
        for (Movie movie : movies) {
            try {
                movie.setNames(new MovieNameDao().getMovieNames(movie.getId()));
            } catch (SQLException ex) {
                System.err.println("Error while trying to retirieve names for movie with id: " + movie.getId());
                System.err.println("Error:");
                System.err.println(ex);
            }
        }
    }

    private void addGenres(List<Movie> movies) {
        for (Movie movie : movies) {
            try {
                movie.setGenres(new MovieGenreDao().getMovieGenres(movie.getId()));
            } catch (SQLException ex) {
                System.err.println("Error while trying to retirieve genres for movie with id: " + movie.getId());
                System.err.println("Error:");
                System.err.println(ex);
            }
        }
    }

    private void addFormats(List<Movie> movies) {
        MovieFormatDao movieFormatDao = new MovieFormatDao();
        for (Movie movie : movies) {
            List<Integer> formatIds;
            try {
                formatIds = movieFormatDao.getFormatIds(movie.getId());
            } catch (SQLException ex) {
                System.err.println("Error while trying to retirieve formatIds for movie with id: " + movie.getId());
                System.err.println("Error:");
                System.err.println(ex);
                continue;
            }

            FormatDao formatDao = new FormatDao();
            List<Format> formats = new ArrayList<Format>();
            for (Integer formatid : formatIds) {
                try {
                    formats.add(formatDao.getFormat(formatid));
                } catch (SQLException ex) {
                    System.err.println("Error while trying to retirieve formatIds for movie with id: " + movie.getId());
                    System.err.println("Error:");
                    System.err.println(ex);
                }
            }
            movie.setFormats(formats);
        }
    }

    private void printMovies(List<Movie> movies) {
        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }
}
