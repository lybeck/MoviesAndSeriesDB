/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.servlets;

import com.mosedb.business.MovieService;
import com.mosedb.models.Movie;
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        redirectToPage("search.jsp", request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        if (isUserLoggedIn(request)) {
            HttpSession session = request.getSession(true);
            MovieService movies=new MovieService();
            List<Movie> movieList=movies.getMovies(LoginManager.getLoggedUser(session));
            request.setAttribute("movieList", movieList);
            restorePage("search.jsp", request, response);
        } else {
            redirectHome(request, response);
        }
    }
}
