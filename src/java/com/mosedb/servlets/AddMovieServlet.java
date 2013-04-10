/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.servlets;

import com.mosedb.business.GenreService;
import com.mosedb.models.Format;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author roopekoira
 */
public class AddMovieServlet extends MosedbServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isUserLoggedIn(request)) {
            HttpSession session = request.getSession(true);
            
            List<String> genreList = new GenreService().getAllGenres();
            session.setAttribute("genreList", genreList);
            List<String> formatList = Format.getAllMediaFormats();
            session.setAttribute("formatList", formatList);
            
            redirectToPage("addMovie.jsp", request, response);
        } else {
            redirectHome(request, response);
        }
    }
}
