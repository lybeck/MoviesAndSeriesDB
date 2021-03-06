/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.servlet.movieServlet;

import com.mosedb.business.MovieService;
import com.mosedb.models.Format;
import com.mosedb.models.LangId;
import com.mosedb.models.Movie;
import com.mosedb.models.User;
import com.mosedb.servlet.AbstractInfoServlet;
import com.mosedb.tools.AttributeManager;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Handles the update-functionality in movieInfo.jsp.
 *
 * @author Lasse
 */
public class UpdateMovieServlet extends AbstractInfoServlet {

    private static final String UPDATE_BUTTON = "update_changes";
    private static final String DELETE_BUTTON = "delete_movie";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String clickedButton = request.getParameter("submit");
        if (clickedButton.equals(UPDATE_BUTTON)) {
            updateMovie(request, response);
        } else if (clickedButton.equals(DELETE_BUTTON)) {
            removeMovie(request, response);
        }
    }

    /**
     * Removes the movie currently in session from the database and the session.
     *
     * @param request The request from which the current session is gotten.
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void removeMovie(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        if (isUserLoggedIn(request)) {
            HttpSession session = request.getSession(true);
            Movie movie = AttributeManager.getMovie(session);
            if (movie != null) {
                MovieService movieService = new MovieService();
                boolean success = movieService.removeMovie(movie.getId());
                if (!success) {
                    AttributeManager.setErrorMessage(request, "Error trying to delete movie...");
                    restorePage("movieInfo.jsp", request, response);
                    return;
                }
                AttributeManager.removeMovie(request.getSession(true));
            }

        }
        redirectHome(request, response);
    }

    /**
     * Updates the info of the movie currently in session according to the
     * corresponding fields in movieInfo.jsp.
     *
     * @param request The request from which the current session and the info
     * from the fields in movieInfo.jsp is gotten.
     * @param response
     * @throws NumberFormatException
     * @throws ServletException
     * @throws IOException
     */
    private void updateMovie(HttpServletRequest request, HttpServletResponse response) throws NumberFormatException, ServletException, IOException {
        if (isUserLoggedIn(request)) {
            Map<LangId, String> names = getNameMap(request);
            if (names.isEmpty()) {
                AttributeManager.setErrorMessage(request, "One name must be specified!");
                restorePage("movieInfo.jsp", request, response);
                return;
            }
            Integer movieYear = getYear(request);
            List<String> genreList = getGenres(request);
            List<Format> formatList = getFormats(request);
            boolean seen = isSeen(request);
            Movie movie = new Movie(names, seen, movieYear, genreList, formatList);
            HttpSession session = request.getSession(true);
            User user = AttributeManager.getUserInSession(session);
            if (user == null) {
                redirectHome(request, response);
                AttributeManager.removeMovie(session);
                return;
            }
            int id = AttributeManager.getMovie(session).getId();
            movie.setId(id);
            boolean success = new MovieService().updateMovie(movie, id);
            if (!success) {
                AttributeManager.setErrorMessage(request, "Movie update caused an unknown error..");
            } else {
                AttributeManager.setSuccessMessage(request, "Movie-information successfully updated!");
                AttributeManager.setMovie(request.getSession(true), new MovieService().getById(id));
            }
            restorePage("movieInfo.jsp", request, response);
            return;
        }
        redirectHome(request, response);
    }
}
