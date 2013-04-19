package com.mosedb.servlets;

import com.mosedb.business.MovieService;
import com.mosedb.models.Movie;
import com.mosedb.tools.AttributeManager;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Easysimulation
 */
public class MovieInfoServlet extends MosedbServlet{
    
     @Override
     protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isUserLoggedIn(request)) {
            HttpSession session = request.getSession(true);
            int id=getMovieId();
            MovieService movieService = new MovieService();
            Movie movie = movieService.getById(id);
            AttributeManager.setMovie(session, movie);
            System.out.println(movie);
            redirectToPage("movieInfo.jsp", request, response);
        } else {
            redirectHome(request, response);
        }
     }

    private int getMovieId() {
        return 4;
    }
    
}
