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
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author llybeck
 */
public class MovieService extends AbstractService {

    public List<Movie> getMovies(User user, Boolean seen) {
        long start = System.currentTimeMillis();
        MovieDao movieDao;
        try {
            movieDao = new MovieDao();
        } catch (SQLException ex) {
            reportError("Error while connecting to database!", ex);
            return null;
        }
        List<Movie> movies;
        try {
            if (user.isAdmin()) {
                movies = movieDao.getAllMovies(seen);
            } else {
                movies = movieDao.getMovies(user.getUsername(), seen);
            }
        } catch (SQLException ex) {
            reportError("Error while retrieving movies by username.", ex);
            return null;
        }

        addNames(movies);
        long end = System.currentTimeMillis();
        double time = (end - start) * 1.0 / 1000;
        System.out.println("Search for movies took " + time + " seconds.");

        movieDao.closeConnection();

        Collections.sort(movies);

        return movies;
    }

    private void addNames(List<Movie> movies) {
        MovieNameDao movieNameDao;
        try {
            movieNameDao = new MovieNameDao();
        } catch (SQLException ex) {
            reportError("Error while connecting to database!", ex);
            return;
        }
        for (Movie movie : movies) {
            try {
                movie.setNames(movieNameDao.getMovieNames(movie.getId()));
            } catch (SQLException ex) {
                reportError("Error while trying to retirieve names for movie with id: " + movie.getId(), ex);
            }
        }
        movieNameDao.closeConnection();
    }

    public List<Movie> getByName(User user, String search, Boolean seen) {
        search = search.trim();
        search = search.replaceAll("\\s+", " ");
        List<String> searchList = Arrays.asList(search.split(" "));
        MovieNameDao movieNameDao;
        try {
            movieNameDao = new MovieNameDao();
        } catch (SQLException ex) {
            reportError("Error while connecting to database!", ex);
            return null;
        }
        Set<Integer> movieIds;
        try {
            if (searchList.size() == 1) {
                movieIds = movieNameDao.getMovieIdsByName(search);
            } else {
                movieIds = movieNameDao.getMovieIdsByName(searchList);
            }
        } catch (SQLException ex) {
            reportError("Error while trying to get movieids by name from movienamedao.", ex);
            return null;
        }

        movieNameDao.closeConnection();

        return getMoviesByIds(user, movieIds, seen);
    }

    public List<Movie> getByGenre(User user, String genre, Boolean seen) {
        MovieGenreDao movieGenreDao;
        try {
            movieGenreDao = new MovieGenreDao();
        } catch (SQLException ex) {
            reportError("Error while connecting to database!", ex);
            return null;
        }
        Set<Integer> movieIds;
        try {
            movieIds = movieGenreDao.getMovieIdsByGenre(genre);
        } catch (SQLException ex) {
            reportError("Error while trying to get movieids by name from movienamedao.", ex);
            return null;
        }

        movieGenreDao.closeConnection();

        return getMoviesByIds(user, movieIds, seen);
    }

    public List<Movie> getByMediaFormat(User user, String mediaformat, Boolean seen) {
        MovieFormatDao movieFormatDao;
        try {
            movieFormatDao = new MovieFormatDao();
        } catch (SQLException ex) {
            reportError("Error while connecting to database!", ex);
            return null;
        }
        Set<Integer> movieIds;
        try {
            movieIds = movieFormatDao.getMovieIdsByMediaFormat(mediaformat);
        } catch (SQLException ex) {
            reportError("Error while trying to get movieids by name from movienamedao.", ex);
            return null;
        }

        movieFormatDao.closeConnection();

        return getMoviesByIds(user, movieIds, seen);
    }

    private List<Movie> getMoviesByIds(User user, Set<Integer> movieIds, Boolean seen) {
        MovieDao movieDao;
        try {
            movieDao = new MovieDao();
        } catch (SQLException ex) {
            reportError("Error while connecting to database!", ex);
            return null;
        }
        List<Movie> movies;
        try {
            if (user.isAdmin()) {
                movies = movieDao.getMovies(movieIds, seen);
            } else {
                movies = movieDao.getMovies(user.getUsername(), movieIds, seen);
            }
        } catch (SQLException ex) {
            reportError("Error while retrieving movies by username.", ex);
            return null;
        }

        addNames(movies);

        movieDao.closeConnection();

        return movies;
    }

    public boolean addMovie(User user, Movie movie) {

        int movieId = addToTableMovie(movie, user);
        if (movieId < 0) {
            return false;
        }
        movie.setId(movieId);

        boolean nameSuccess = addToTableMoviename(movie);
        if (!nameSuccess) {
            removeMovieDueToNameAdditionError(movieId);
            return false;
        }

        addToTableMoviegenre(movie);

        addToTablesFormatInfo(movie);

        return true;
    }

    private int addToTableMovie(Movie movie, User user) {
        MovieDao movieDao;
        try {
            movieDao = new MovieDao();
        } catch (SQLException ex) {
            reportError("Error while connecting to database!", ex);
            return -1;
        }
        int id = -1;
        try {
            if (movie.getMovieYear() == null) {
                id = movieDao.addMovie(user, movie.isSeen());
            } else {
                id = movieDao.addMovie(user, movie.getMovieYear(), movie.isSeen());
            }
        } catch (SQLException ex) {
            reportError("Failed to add movie to table mosedb.movie.", ex);
        }

        movieDao.closeConnection();

        return id;

    }

    private boolean addToTableMoviename(Movie movie) {
        MovieNameDao movieNameDao;
        try {
            movieNameDao = new MovieNameDao();
        } catch (SQLException ex) {
            reportError("Error while connecting to database!", ex);
            return false;
        }
        String name;
        try {
            name = movie.getNameEng();
            if (name != null) {
                movieNameDao.addMovieName(movie.getId(), Movie.LangId.eng, name);
            }
            name = movie.getNameFi();
            if (name != null) {
                movieNameDao.addMovieName(movie.getId(), Movie.LangId.fi, name);
            }
            name = movie.getNameSwe();
            if (name != null) {
                movieNameDao.addMovieName(movie.getId(), Movie.LangId.swe, name);
            }
            name = movie.getNameOther();
            if (name != null) {
                movieNameDao.addMovieName(movie.getId(), Movie.LangId.other, name);
            }
        } catch (SQLException ex) {
            reportError("Failed to add movie to table mosedb.moviename.", ex);
            return false;
        }

        movieNameDao.closeConnection();

        return true;
    }

    private void removeMovieDueToNameAdditionError(int movieId) {
        MovieNameDao movieNameDao;
        MovieDao movieDao;
        try {
            movieNameDao = new MovieNameDao();
            movieDao = new MovieDao();
        } catch (SQLException ex) {
            reportError("Error while connecting to database!", ex);
            return;
        }
        try {
            movieNameDao.removeMovieName(movieId);
        } catch (SQLException ex) {
            reportError("Failed to remove movienames from table mosedb.moviename.", ex);
        }
        try {
            movieDao.removeMovie(movieId);
        } catch (SQLException ex) {
            reportError("Failed to remove movie from table mosedb.movie.", ex);
        }

        movieNameDao.closeConnection();
        movieDao.closeConnection();
    }

    private void addToTableMoviegenre(Movie movie) {
        if (movie.getGenres() == null) {
            return;
        }
        MovieGenreDao movieGenreDao;
        try {
            movieGenreDao = new MovieGenreDao();
        } catch (SQLException ex) {
            reportError("Error while connecting to database!", ex);
            return;
        }
        try {
            movieGenreDao.addMovieGenres(movie.getId(), movie.getGenres());
        } catch (SQLException ex) {
            reportError("Failed to add genres to table mosedb.moviegenre.", ex);
        }

        movieGenreDao.closeConnection();
    }

    private void addToTablesFormatInfo(Movie movie) {
        if (movie.getFormats() == null) {
            return;
        }
        FormatDao formatDao;
        MovieFormatDao movieFormatDao;
        try {
            formatDao = new FormatDao();
            movieFormatDao = new MovieFormatDao();
        } catch (SQLException ex) {
            reportError("Error while connecting to database!", ex);
            return;
        }

        try {
            int formatId;
            for (Format format : movie.getFormats()) {
                formatId = -1;
                if (format.hasFileInfo()) {
                    if (format.hasResoInfo()) {
                        formatId = formatDao.addFormatDigitalCopy(format.getFileType(), format.getResoX(), format.getResoX());
                    } else {
                        formatId = formatDao.addFormatDigitalCopy(format.getFileType());
                    }
                } else {
                    formatId = formatDao.addFormat(format.getMediaFormat());
                }

                if (formatId == -1) {
                    continue;
                }

                movieFormatDao.addMovieFormat(movie.getId(), formatId);
            }
        } catch (SQLException ex) {
            reportError("Failed to add movieformats.", ex);
        }

        formatDao.closeConnection();
        movieFormatDao.closeConnection();
    }

    public Movie getById(int id) {
        MovieDao movieDao;
        MovieNameDao movieNameDao;
        MovieGenreDao movieGenreDao;
        MovieFormatDao movieFormatDao;
        try {
            movieDao = new MovieDao();
            movieNameDao = new MovieNameDao();
            movieGenreDao = new MovieGenreDao();
            movieFormatDao = new MovieFormatDao();
        } catch (SQLException ex) {
            reportError("Error while connecting to database!", ex);
            return null;
        }
        try {
            Movie movie = movieDao.getMovieById(id);
            movieDao.closeConnection();
            if(movie == null)
                return null;
            movie.setNames(movieNameDao.getMovieNames(id));
            movieNameDao.closeConnection();
            movie.setGenres(movieGenreDao.getMovieGenres(id));
            movieGenreDao.closeConnection();
            movie.setFormats(movieFormatDao.getFormats(id));
            movieFormatDao.closeConnection();
            return movie;
        } catch (SQLException ex) {
            reportError("Error while retrieving movie by id.", ex);
            return null;
        }
    }
}
