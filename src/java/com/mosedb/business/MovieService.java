/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.business;

import com.mosedb.dao.FormatDao;
import com.mosedb.dao.movieDao.MovieDao;
import com.mosedb.dao.movieDao.MovieFormatDao;
import com.mosedb.dao.movieDao.MovieGenreDao;
import com.mosedb.dao.movieDao.MovieNameDao;
import com.mosedb.models.Format;
import com.mosedb.models.LangId;
import com.mosedb.models.Movie;
import com.mosedb.models.User;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Lasse
 */
public class MovieService extends AbstractService {

    /**
     * Retrieves a list of movies the user has stored, or a list of all users
     * movies if the user is admin.
     *
     * @param user User, whose movies are retrieved. If user is admin
     * ({@link User#isAdmin()} returns {@code true}) all users' movies are
     * retrieved.
     * @param seen Retrieves only movies with the same value on {@code seen}. If
     * parameter is {@code null} it is ignored.
     * @return A list of movies, or {@code null} if the database query fails.
     */
    public List<Movie> getMovies(User user, Boolean seen) {
        MovieDao movieDao;
        try {
            movieDao = new MovieDao();
        } catch (SQLException ex) {
            reportConnectionError(ex);
            return null;
        }
        List<Movie> movies;
        try {
            if (user.isAdmin()) {
                movies = movieDao.getAllMovies(seen);
            } else {
                movies = movieDao.getMovies(seen, user.getUsername());
            }
        } catch (SQLException ex) {
            reportError("Error while retrieving movies by username.", ex);
            return null;
        }

        addNames(movies);

        movieDao.closeConnection();

        Collections.sort(movies);

        return movies;
    }

    /**
     * Retrieves and adds name values from the database to the list of movies.
     *
     * @param movies Movies, whose names are retrieved.
     */
    private void addNames(List<Movie> movies) {
        MovieNameDao movieNameDao;
        try {
            movieNameDao = new MovieNameDao();
        } catch (SQLException ex) {
            reportConnectionError(ex);
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

    /**
     * Retrieves a list of the user's movies with a name corresponding to the
     * search term, or a list of all users movies if the user is admin.
     *
     * @param user User, whose movies are retrieved. If user is admin
     * ({@link User#isAdmin()} returns {@code true}) all users movies are
     * retrieved.
     * @param search Search term, which is compared to movie names. If the
     * search term consists of many words (separated by white spaces) all the
     * search terms are matches (by AND).
     * @param seen Retrieves only movies with the same value on {@code seen}. If
     * parameter is {@code null} it is ignored.
     * @return A list of movies, or {@code null} if the database query fails.
     */
    public List<Movie> getByName(User user, String search, Boolean seen) {
        search = search.trim();
        search = search.replaceAll("\\s+", " ");
        List<String> searchList = Arrays.asList(search.split(" "));
        MovieNameDao movieNameDao;
        try {
            movieNameDao = new MovieNameDao();
        } catch (SQLException ex) {
            reportConnectionError(ex);
            return null;
        }
        List<Movie> movieList;
        try {
            if (searchList.size() == 1) {
                movieList = movieNameDao.getMoviesByName(search, user, seen);
            } else {
                movieList = movieNameDao.getMoviesByName(searchList, user, seen);
            }
        } catch (SQLException ex) {
            reportError("Error while trying to get movieids by name from movienamedao.", ex);
            return null;
        }

        movieNameDao.closeConnection();

        return movieList;
    }

    /**
     * Retrieves a list of the user's movies with a genre corresponding to the
     * search term, or a list of all users movies if the user is admin.
     *
     * @param user User, whose movies are retrieved. If user is admin
     * ({@link User#isAdmin()} returns {@code true}) all users movies are
     * retrieved.
     * @param genre Search term, which is compared to movie genres.
     * @param seen Retrieves only movies with the same value on {@code seen}. If
     * parameter is {@code null} it is ignored.
     * @return A list of movies, or {@code null} if the database query fails.
     */
    public List<Movie> getByGenre(User user, String genre, Boolean seen) {
        MovieGenreDao movieGenreDao;
        try {
            movieGenreDao = new MovieGenreDao();
        } catch (SQLException ex) {
            reportConnectionError(ex);
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

    /**
     * Retrieves a list of the user's movies with a media format corresponding
     * to the search term, or a list of all users movies if the user is admin.
     *
     * @param user User, whose movies are retrieved. If user is admin
     * ({@link User#isAdmin()} returns {@code true}) all users movies are
     * retrieved.
     * @param mediaformat Search term, which is compared to movies' media
     * formats.
     * @param seen Retrieves only movies with the same value on {@code seen}. If
     * parameter is {@code null} it is ignored.
     * @return A list of movies, or {@code null} if the database query fails.
     */
    public List<Movie> getByMediaFormat(User user, String mediaformat, Boolean seen) {
        MovieFormatDao movieFormatDao;
        try {
            movieFormatDao = new MovieFormatDao();
        } catch (SQLException ex) {
            reportConnectionError(ex);
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

    /**
     * Retrieves movies with corresponding movieIds from the database.
     *
     * @param user User, whose movies are retrieved. If user is admin
     * ({@link User#isAdmin()} returns {@code true}) all users movies are
     * retrieved.
     * @param movieIds A set of movieIds, whose corresponding movies are
     * retrieved.
     * @param seen Retrieves only movies with the same value on {@code seen}. If
     * parameter is {@code null} it is ignored.
     * @return A list of movies, or {@code null} if the database query fails.
     */
    private List<Movie> getMoviesByIds(User user, Set<Integer> movieIds, Boolean seen) {
        MovieDao movieDao;
        try {
            movieDao = new MovieDao();
        } catch (SQLException ex) {
            reportConnectionError(ex);
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

    /**
     * Adds a movie to the database.
     *
     * @param user The movie's owner.
     * @param movie Movie to be stored.
     * @return {@code true} if movie was successfully added, otherwise
     * {@code false}.
     */
    public boolean addMovie(User user, Movie movie) {

        int movieId = addToTableMovie(movie, user);
        if (movieId <= 0) {
            return false;
        }
        movie.setId(movieId);

        boolean nameSuccess = addToTableMoviename(movie);
        if (!nameSuccess) {
            removeMovie(movieId);
            return false;
        }

        addToTableMoviegenre(movie);

        addToTablesFormatInfo(movie);

        return true;
    }

    /**
     * Adds information from the movie to the table 'movie' in the database.
     *
     * @param movie Movie to be stored.
     * @param user The movie's owner.
     * @return {@code true} if information was successfully added, otherwise
     * {@code false}.
     */
    private int addToTableMovie(Movie movie, User user) {
        MovieDao movieDao;
        try {
            movieDao = new MovieDao();
        } catch (SQLException ex) {
            reportConnectionError(ex);
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

    /**
     * Adds information from the movie to the table 'moviename' in the database.
     *
     * @param movie Movie to be stored.
     * @return {@code true} if information was successfully added, otherwise
     * {@code false}.
     */
    private boolean addToTableMoviename(Movie movie) {
        MovieNameDao movieNameDao;
        try {
            movieNameDao = new MovieNameDao();
        } catch (SQLException ex) {
            reportConnectionError(ex);
            return false;
        }
        String name;
        try {
            name = movie.getNameEng();
            if (name != null) {
                movieNameDao.addMovieName(movie.getId(), LangId.eng, name);
            }
            name = movie.getNameFi();
            if (name != null) {
                movieNameDao.addMovieName(movie.getId(), LangId.fi, name);
            }
            name = movie.getNameSwe();
            if (name != null) {
                movieNameDao.addMovieName(movie.getId(), LangId.swe, name);
            }
            name = movie.getNameOther();
            if (name != null) {
                movieNameDao.addMovieName(movie.getId(), LangId.other, name);
            }
        } catch (SQLException ex) {
            reportError("Failed to add movie to table mosedb.moviename.", ex);
            return false;
        }

        movieNameDao.closeConnection();

        return true;
    }

    /**
     * Adds information from the movie to the table 'moviegenre' in the
     * database.
     *
     * @param movie Movie to be stored.
     */
    private void addToTableMoviegenre(Movie movie) {
        if (movie.getGenres() == null) {
            return;
        }
        MovieGenreDao movieGenreDao;
        try {
            movieGenreDao = new MovieGenreDao();
        } catch (SQLException ex) {
            reportConnectionError(ex);
            return;
        }
        try {
            movieGenreDao.addMovieGenres(movie.getId(), movie.getGenres());
        } catch (SQLException ex) {
            reportError("Failed to add genres to table mosedb.moviegenre.", ex);
        }

        movieGenreDao.closeConnection();
    }

    /**
     * Adds information from the movie to the table 'movieformat' in the
     * database.
     *
     * @param movie Movie to be stored.
     */
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
            reportConnectionError(ex);
            return;
        }

        try {
            int formatId;
            for (Format format : movie.getFormats()) {
                formatId = -1;
                if (format.hasFileInfo()) {
                    if (format.hasResoInfo()) {
                        formatId = formatDao.addFormatDigitalCopy(format.getFileType(), format.getResoX(), format.getResoY());
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

    /**
     * Retrieves the movie with the given {@code movieid} from the database.
     *
     * @param movieid Id of the movie.
     * @return A movie, or {@code null} if the database query fails.
     */
    public Movie getById(int movieid) {
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
            reportConnectionError(ex);
            return null;
        }
        try {
            Movie movie = movieDao.getMovieById(movieid);
            movieDao.closeConnection();
            if (movie == null) {
                return null;
            }
            movie.setNames(movieNameDao.getMovieNames(movieid));
            movieNameDao.closeConnection();
            movie.setGenres(movieGenreDao.getMovieGenres(movieid));
            movieGenreDao.closeConnection();
            movie.setFormats(movieFormatDao.getFormats(movieid));
            movieFormatDao.closeConnection();
            return movie;
        } catch (SQLException ex) {
            reportError("Error while retrieving movie by id.", ex);
            return null;
        }
    }

    /**
     * Permanently deletes the movie with the given {@code movieid} from the
     * database.
     *
     * @param movieid Id of the movie.
     * @return {@code true} if information was successfully added, otherwise
     * {@code false}.
     */
    public boolean removeMovie(int movieid) {
        MovieDao movieDao;
        try {
            movieDao = new MovieDao();
        } catch (SQLException ex) {
            reportConnectionError(ex);
            return false;
        }
        try {
            movieDao.removeMovie(movieid);
            movieDao.closeConnection();
        } catch (SQLException ex) {
            reportError("Error while trying to remove movie from database!", ex);
            return false;
        }
        return true;
    }

    /**
     * Updates the information of the given movie in the database.
     *
     * @param movie Movie with the information to be updated.
     * @param movieid Id of the movie to be updated.
     * @return {@code true} if information was successfully added, otherwise
     * {@code false}.
     */
    public boolean updateMovie(Movie movie, int movieid) {
        MovieDao movieDao;
        MovieNameDao movieNameDao;
        MovieGenreDao movieGenreDao;
        try {
            movieDao = new MovieDao();
            movieNameDao = new MovieNameDao();
            movieGenreDao = new MovieGenreDao();
        } catch (SQLException ex) {
            reportConnectionError(ex);
            return false;
        }

        try {
            boolean success;

            success = movieDao.updateMovieSeen(movieid, movie.isSeen());
            success = movieDao.updateMovieYear(movieid, movie.getMovieYear()) && success;
            movieDao.closeConnection();
            if (!success) {
                System.err.println("Seen or year update failed!");
                return false;
            }

            success = movieNameDao.updateMovieNames(movieid, movie.getNames());
            movieNameDao.closeConnection();
            if (!success) {
                System.err.println("Name update failed!");
                return false;
            }

            success = movieGenreDao.updateMovieGenres(movieid, movie.getGenres());
            movieGenreDao.closeConnection();
            if (!success) {
                System.err.println("Genre update failed!");
                return false;
            }

            success = updateMovieFormats(movieid, movie);
            if (!success) {
                System.err.println("Format update failed!");
                return false;
            }
        } catch (SQLException ex) {
            reportError("Error trying to update movie!", ex);
        }

        return true;
    }

    /**
     * Updates the movie format information of a movie in the database.
     *
     * @param movieid Id of the movie to be updated.
     * @param movie Movie with the media format information to be updated.
     * @return {@code true} if information was successfully added, otherwise
     * {@code false}.
     */
    private boolean updateMovieFormats(int movieid, Movie movie) {
        removeMovieFormats(movieid);
        movie.setId(movieid);
        addToTablesFormatInfo(movie);
        return true;
    }

    /**
     * Removes all the media format information associated with the movie from
     * the database.
     *
     * @param movieid Id of the movie to be updated.
     * @return {@code true} if information was successfully added, otherwise
     * {@code false}.
     */
    private boolean removeMovieFormats(int movieid) {
        MovieFormatDao movieFormatDao;
        try {
            movieFormatDao = new MovieFormatDao();
        } catch (SQLException ex) {
            reportConnectionError(ex);
            return false;
        }
        try {
            movieFormatDao.removeMovieFormats(movieid);
        } catch (SQLException ex) {
            reportError("Error while removing movieformats from database!", ex);
            return false;
        }
        return true;
    }
}
