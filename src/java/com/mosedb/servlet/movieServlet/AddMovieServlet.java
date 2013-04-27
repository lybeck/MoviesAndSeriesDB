/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.servlet.movieServlet;

import com.mosedb.business.GenreService;
import com.mosedb.business.MovieService;
import com.mosedb.models.Format;
import com.mosedb.models.Movie;
import com.mosedb.models.LangId;
import com.mosedb.models.User;
import com.mosedb.servlet.AbstractInfoServlet;
import com.mosedb.tools.AttributeManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author roopekoira
 */
public class AddMovieServlet extends AbstractInfoServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        if (isUserLoggedIn(request)) {
            HttpSession session = request.getSession(true);

            List<String> genreList = new GenreService().getAllGenres();
            AttributeManager.setGenreList(session, genreList);
            List<String> formatList = Format.getAllMediaFormats();
            AttributeManager.setFormatList(session, formatList);
            List<String> yearList = new ArrayList<String>();
            yearList.add("");
            int thisYear = new Date().getYear() + 1900;
            for (int y = thisYear; y >= 1900; --y) {
                yearList.add(y + "");
            }
            AttributeManager.setYearList(session, yearList);

            redirectToPage("addMovie.jsp", request, response);
        } else {
            redirectHome(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        if (isUserLoggedIn(request)) {
            Map<LangId, String> names = getNameMap(request);
            if (names.isEmpty()) {
                AttributeManager.setErrorMessage(request, "One name must be specified!");
                restorePage("addMovie.jsp", request, response);
                return;
            }
            Integer movieYear = getYear(request);
            List<String> genreList = getGenres(request);
            List<Format> formatList = getFormats(request);
            boolean seen = isSeen(request);

            Movie movie = new Movie(names, seen, movieYear, genreList, formatList);
            User user = AttributeManager.getUserInSession(request.getSession(true));
            boolean success = new MovieService().addMovie(user, movie);
            if (success) {
                AttributeManager.setSuccessMessage(request, "Movie successfully added!");
            } else {
                AttributeManager.setErrorMessage(request, "Movie addition caused an unknown error..");
            }

            restorePage("addMovie.jsp", request, response);
        } else {
            redirectHome(request, response);
        }
    }
}
