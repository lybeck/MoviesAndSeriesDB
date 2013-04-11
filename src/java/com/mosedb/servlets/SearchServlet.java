/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.servlets;

import com.mosedb.business.MovieService;
import com.mosedb.models.Movie;
import com.mosedb.models.User;
import com.mosedb.tools.AttributeManager;
import com.mosedb.tools.LoginManager;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author llybeck
 */
public class SearchServlet extends MosedbServlet {

    private static final String NAME_SEARCH = "Name";
    private static final String GENRE_SEARCH = "Genre";
    private static final String MEDIAFORMAT_SEARCH = "Media format";

    private static String searchFieldDefault(int i) {
        return "Search #" + i;
    }

    private static String searchFieldName(int i) {
        return "txt" + i;
    }

    private static String dropMenuName(int i) {
        return "select" + i;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        redirectToPage("search.jsp", request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (isUserLoggedIn(request)) {
            HttpSession session = request.getSession(true);
            User user = AttributeManager.getUserSessionKey(session);
            MovieService movieService = new MovieService();


            String search1 = request.getParameter(searchFieldName(1));
            String drop1 = request.getParameter(dropMenuName(1));
            List<Movie> movieList;

            if (search1 == null || search1.isEmpty() || search1.equalsIgnoreCase(searchFieldDefault(1))
                    || drop1 == null) {
                movieList = movieService.getMovies(user);
            } else {
                if (drop1.equals(NAME_SEARCH)) {
                    movieList = movieService.getByName(user, search1);
                } else if (drop1.equals(GENRE_SEARCH)) {
                    movieList = movieService.getByGenre(user, search1);
                } else if (drop1.equals(MEDIAFORMAT_SEARCH)) {
                    movieList = movieService.getByMediaFormat(user, search1);
                } else {
                    movieList = movieService.getMovies(user);
                }
            }

            AttributeManager.setMovieList(request, movieList);
            
            restorePage("search.jsp", request, response);
        } else {
            redirectHome(request, response);
        }
    }
}
