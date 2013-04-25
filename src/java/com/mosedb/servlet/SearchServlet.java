/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.servlet;

import com.mosedb.business.MovieService;
import com.mosedb.business.SeriesService;
import com.mosedb.models.Movie;
import com.mosedb.models.Series;
import com.mosedb.models.User;
import com.mosedb.tools.AttributeManager;
import com.mosedb.tools.LoginManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Lasse
 */
public class SearchServlet extends MosedbServlet {

    private static final String SEARCH_FIELD_NAME = "search_field";
    private static final String SEARCH_FIELD_DEFAULT = "Search";
    private static final String DROP_BOX_NAME = "drop_box";
    private static final String NAME_SEARCH = "Name";
    private static final String GENRE_SEARCH = "Genre";
    private static final String MEDIAFORMAT_SEARCH = "Media format";
    private static final String MOVIE_OR_SERIES_SEARCH = "movie_series_radio";
    private static final String MOVIE_SEARCH = "movie";
    private static final String SERIES_SEARCH = "series";
    private static final String SEEN_RADIO = "seen_radio";
    private static final String SEEN_CHECKED = "seen";
    private static final String NOT_SEEN_CHECKED = "not_seen";
    private static final String BOTH_SEEN_CHECKED = "both";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        redirectToPage("search.jsp", request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        if (isUserLoggedIn(request)) {
            HttpSession session = request.getSession(true);
            User user = AttributeManager.getUserSessionKey(session);


            String searchField = request.getParameter(SEARCH_FIELD_NAME);
            String dropBox = request.getParameter(DROP_BOX_NAME);

            String searchType = request.getParameter(MOVIE_OR_SERIES_SEARCH);
            String seenCheck = request.getParameter(SEEN_RADIO);

            Boolean seenParameter = null;
            if (seenCheck.equals(SEEN_CHECKED)) {
                seenParameter = true;
            } else if (seenCheck.equals(NOT_SEEN_CHECKED)) {
                seenParameter = false;
            }


            if (searchType.equals(MOVIE_SEARCH)) {
                List<Movie> movieList = getMovieList(searchField, dropBox, user, seenParameter);
                AttributeManager.setMovieList(request, movieList);
                restorePage("search.jsp", request, response);
            } else {
                List<Series> seriesList = getSeriesList(searchField, dropBox, user);
                AttributeManager.setSeriesList(request, seriesList);
                restorePage("search.jsp", request, response);
            }
        } else {
            redirectHome(request, response);
        }


    }

    private List<Movie> getMovieList(String searchField, String dropBox, User user, Boolean seenParameter) {
        MovieService movieService = new MovieService();
        List<Movie> movieList;
        if (searchField == null || searchField.isEmpty() || searchField.equals(SEARCH_FIELD_DEFAULT)
                || dropBox == null) {
            movieList = movieService.getMovies(user, seenParameter);
        } else {
            if (dropBox.equals(NAME_SEARCH)) {
                movieList = movieService.getByName(user, searchField, seenParameter);
            } else if (dropBox.equals(GENRE_SEARCH)) {
                movieList = movieService.getByGenre(user, searchField, seenParameter);
            } else if (dropBox.equals(MEDIAFORMAT_SEARCH)) {
                movieList = movieService.getByMediaFormat(user, searchField, seenParameter);
            } else {
                movieList = movieService.getMovies(user, seenParameter);
            }
        }
        return movieList;
    }

    private List<Series> getSeriesList(String searchField, String dropBox, User user) {
        /* Series search */
        SeriesService seriesService = new SeriesService();
        List<Series> seriesList = new ArrayList<Series>();
        if (searchField == null || searchField.isEmpty() || searchField.equals(SEARCH_FIELD_DEFAULT)
                || dropBox == null) {
            seriesList = new SeriesService().getSeries(user);
        } else {
            if (dropBox.equals(NAME_SEARCH)) {
                seriesList = seriesService.getByName(user, searchField);
            } else if (dropBox.equals(GENRE_SEARCH)) {
                seriesList = seriesService.getByGenre(user, searchField);
            } else if (dropBox.equals(MEDIAFORMAT_SEARCH)) {
                seriesList = seriesService.getByMediaFormat(user, searchField);
            } else {
                seriesList = seriesService.getSeries(user);
            }
        }
        return seriesList;
    }
}
