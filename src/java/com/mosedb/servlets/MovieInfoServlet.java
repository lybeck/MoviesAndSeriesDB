package com.mosedb.servlets;

import com.mosedb.business.GenreService;
import com.mosedb.business.MovieService;
import com.mosedb.models.Format;
import com.mosedb.models.Movie;
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
 * @author Easysimulation
 */
public class MovieInfoServlet extends MosedbServlet{
    
     @Override
     protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isUserLoggedIn(request)) {
            HttpSession session=request.getSession(true);
            AttributeManager.removeMovie(session);
            redirectToPage("movieInfo.jsp", request, response);
        } else {
            redirectHome(request, response);
        }
     }
     
     @Override
     protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        if (isUserLoggedIn(request)) {
            HttpSession session = request.getSession(true);
            AttributeManager.removeMovie(session);

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
            
            String id = request.getParameter("Edit");
            if(id != null){
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
