package com.mosedb.servlets;

import com.mosedb.business.MovieService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Easysimulation
 */
public class MovieInfoServlet extends MosedbServlet{
    
     @Override
     protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isUserLoggedIn(request)) {
            int id=getMovieId();
            MovieService movieService = new MovieService();
            movieService.getById(id);
        }
     }

    private int getMovieId() {
        return 1;
    }
    
}
