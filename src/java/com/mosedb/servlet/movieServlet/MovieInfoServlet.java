package com.mosedb.servlet.movieServlet;

import com.mosedb.business.GenreService;
import com.mosedb.business.MovieService;
import com.mosedb.models.Format;
import com.mosedb.models.Movie;
import com.mosedb.servlet.AbstractInfoServlet;
import com.mosedb.servlet.MosedbServlet;
import com.mosedb.tools.AttributeManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Roope
 */
public class MovieInfoServlet extends AbstractInfoServlet{

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        if (isUserLoggedIn(request)) {
            HttpSession session = request.getSession(true);
            AttributeManager.removeMovie(session);

            List<String> genreList = new GenreService().getAllGenres();
            AttributeManager.setGenreList(session, genreList);
            List<String> formatList = Format.getAllMediaFormats();
            AttributeManager.setFormatList(session, formatList);
            List<String> yearList = getYearList();
            AttributeManager.setYearList(session, yearList);

            String id = request.getParameter("Edit");
            if (id != null) {
                MovieService movieService = new MovieService();
                Movie movie = movieService.getById(Integer.parseInt(id));
                AttributeManager.setMovie(session, movie);
            }

            redirectToPage("movieInfo.jsp", request, response);
        } else {
            redirectHome(request, response);
        }
    }
}
