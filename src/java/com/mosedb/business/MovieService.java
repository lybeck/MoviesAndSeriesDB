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
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author llybeck
 */
public class MovieService {

    public List<Movie> getMovies(User user) {
        long start = System.currentTimeMillis();
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
        long end = System.currentTimeMillis();
        double time = (end - start) * 1.0 / 1000;
        System.out.println("Search for movies took " + time + " seconds.");
        return movies;
    }

    private void addInfoToMovies(List<Movie> movies) {
        addNames(movies);
//        addGenres(movies);
//        addFormats(movies);

//        printMovies(movies);

    }

    private void addNames(List<Movie> movies) {
        MovieNameDao movieNameDao = new MovieNameDao();
        for (Movie movie : movies) {
            try {
                movie.setNames(movieNameDao.getMovieNames(movie.getId()));
            } catch (SQLException ex) {
                System.err.println("Error while trying to retirieve names for movie with id: " + movie.getId());
                System.err.println("Error:");
                System.err.println(ex);
            }
        }
    }

    private void addGenres(List<Movie> movies) {
        MovieGenreDao movieGenreDao = new MovieGenreDao();
        for (Movie movie : movies) {
            try {
                movie.setGenres(movieGenreDao.getMovieGenres(movie.getId()));
            } catch (SQLException ex) {
                System.err.println("Error while trying to retirieve genres for movie with id: " + movie.getId());
                System.err.println("Error:");
                System.err.println(ex);
            }
        }
    }

    private void addFormats(List<Movie> movies) {
        MovieFormatDao movieFormatDao = new MovieFormatDao();
        FormatDao formatDao = new FormatDao();
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

    public List<Movie> getByName(User user, String name) {
        MovieNameDao movieNameDao = new MovieNameDao();
        Set<Integer> movieIds;
        try {
            movieIds = movieNameDao.getMovieIdsByName(name);
        } catch (SQLException ex) {
            System.err.println("Error while trying to get movieids by name from movienamedao.");
            System.err.println("Error:");
            System.err.println(ex);
            return new ArrayList<Movie>();
        }

        return getMoviesByIds(user, movieIds);
    }

    public List<Movie> getByGenre(User user, String genre) {
        MovieGenreDao movieGenreDao = new MovieGenreDao();
        Set<Integer> movieIds;
        try {
            movieIds = movieGenreDao.getMovieIdsByGenre(genre);
        } catch (SQLException ex) {
            System.err.println("Error while trying to get movieids by name from movienamedao.");
            System.err.println("Error:");
            System.err.println(ex);
            return new ArrayList<Movie>();
        }

        return getMoviesByIds(user, movieIds);
    }

    /**
     * TODO: implement this method!!
     */
    public List<Movie> getByMediaFormat(User user, String mediaformat) {
        MovieNameDao movieNameDao = new MovieNameDao();
        Set<Integer> movieIds;
        try {
            movieIds = movieNameDao.getMovieIdsByName(mediaformat);
        } catch (SQLException ex) {
            System.err.println("Error while trying to get movieids by name from movienamedao.");
            System.err.println("Error:");
            System.err.println(ex);
            return new ArrayList<Movie>();
        }

        return getMoviesByIds(user, movieIds);
    }

    private List<Movie> getMoviesByIds(User user, Set<Integer> movieIds) {
        MovieDao movieDao = new MovieDao();
        List<Movie> movies;
        try {
            if (user.isAdmin()) {
                movies = movieDao.getMovies(movieIds);
            } else {
                movies = movieDao.getMovies(user.getUsername(), movieIds);
            }
        } catch (SQLException ex) {
            System.err.println("Error while retrieving movies by username. Error:");
            System.err.println(ex);
            return null;
        }

        addInfoToMovies(movies);

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
        MovieDao movieDao = new MovieDao();
        int id = -1;
        try {
            if (movie.getMovieYear() == null) {
                id = movieDao.addMovie(user, movie.isSeen());
            } else {
                id = movieDao.addMovie(user, movie.getMovieYear(), movie.isSeen());
            }
        } catch (SQLException ex) {
            System.err.println("Failed to add movie to table mosedb.movie.");
            System.err.println("Error:");
            System.err.println(ex);
        }
        return id;

    }

    private boolean addToTableMoviename(Movie movie) {
        MovieNameDao movieNameDao = new MovieNameDao();
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
            System.err.println("Failed to add movie to table mosedb.moviename.");
            System.err.println("Error:");
            System.err.println(ex);
            return false;
        }
        return true;
    }

    private void removeMovieDueToNameAdditionError(int movieId) {
        MovieNameDao movieNameDao = new MovieNameDao();
        try {
            movieNameDao.removeMovieName(movieId);
        } catch (SQLException ex) {
            System.err.println("Failed to remove movienames from table mosedb.moviename.");
            System.err.println("Error:");
            System.err.println(ex);
        }
        MovieDao movieDao = new MovieDao();
        try {
            movieDao.removeMovie(movieId);
        } catch (SQLException ex) {
            System.err.println("Failed to remove movie from table mosedb.movie.");
            System.err.println("Error:");
            System.err.println(ex);
        }
    }

    private void addToTableMoviegenre(Movie movie) {
        if (movie.getGenres() == null) {
            return;
        }
        MovieGenreDao movieGenreDao = new MovieGenreDao();
        try {
            movieGenreDao.addMovieGenres(movie.getId(), movie.getGenres());
        } catch (SQLException ex) {
            System.err.println("Failed to add genres to table mosedb.moviegenre.");
            System.err.println("Error:");
            System.err.println(ex);
        }
    }

    private void addToTablesFormatInfo(Movie movie) {
        if (movie.getFormats() == null) {
            return;
        }
        FormatDao formatDao = new FormatDao();
        MovieFormatDao movieFormatDao = new MovieFormatDao();
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
            System.err.println("Failed to add movieformats.");
            System.err.println("Error:");
            System.err.println(ex);
        }
    }

    public static void main(String[] args) {
        MovieService movieService = new MovieService();
        User user = new User("lasse", "Lasse", "Lybeck", true);
        Movie movie;
        Map<Movie.LangId, String> names = new EnumMap<Movie.LangId, String>(Movie.LangId.class);;
        boolean seen;
        Integer movieYear;
        List<String> genres = new ArrayList<String>();
        List<Format> formats = new ArrayList<Format>();

        names.clear();
        names.put(Movie.LangId.eng, "The Shawshank Redemption");
        seen = false;
        movieYear = 1994;
        genres.clear();
        genres.add("Crime");
        genres.add("Drama");
        formats.clear();
        formats.add(new Format(Format.MediaFormat.dvd));
        movie = new Movie(names, seen, movieYear, genres, formats);
        movieService.addMovie(user, movie);

        names.clear();
        names.put(Movie.LangId.eng, "Pulp Fiction");
        seen = false;
        movieYear = 1994;
        genres.clear();
        genres.add("Crime");
        genres.add("Thriller");
        formats.clear();
        formats.add(new Format(Format.MediaFormat.dc, "avi"));
        formats.add(new Format(Format.MediaFormat.dc, "mkv", 1920, 1080));
        movie = new Movie(names, seen, movieYear, genres, formats);
        movieService.addMovie(user, movie);

        names.clear();
        names.put(Movie.LangId.eng, "The Dark Knight");
        names.put(Movie.LangId.fi, "Yön Ritari");
        seen = true;
        movieYear = 2008;
        genres.clear();
        genres.add("Action");
        genres.add("Crime");
        genres.add("Drama");
        formats.clear();
        formats.add(new Format(Format.MediaFormat.dvd));
        formats.add(new Format(Format.MediaFormat.dc, "avi"));
        formats.add(new Format(Format.MediaFormat.dc, "mkv", 1280, 720));
        movie = new Movie(names, seen, movieYear, genres, formats);
        movieService.addMovie(user, movie);

        for (int i = 0; i < 500; i++) {
            names.put(Movie.LangId.eng, "Test Movie " + (i + 1));
            names.put(Movie.LangId.fi, "Testileffa " + (i + 1));
            names.put(Movie.LangId.swe, "Test Film " + (i + 1));
            names.put(Movie.LangId.other, "Eksperiment Filmi " + (i + 1));
            movie = new Movie(names, seen, movieYear, genres, formats);
            movieService.addMovie(user, movie);
        }
    }
}