package com.mosedb.servlet.seriesServlet;

import com.mosedb.business.GenreService;
import com.mosedb.business.SeriesService;
import com.mosedb.models.Format;
import com.mosedb.models.Series;
import com.mosedb.servlet.AbstractInfoServlet;
import com.mosedb.tools.AttributeManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Easysimulation
 */
public class SeriesInfoServlet extends AbstractInfoServlet{
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        if (isUserLoggedIn(request)) {
            HttpSession session = request.getSession(true);

            List<String> genreList = new GenreService().getAllGenres();
            AttributeManager.setGenreList(session, genreList);
            AttributeManager.setEpisodeDropbox(session, getEpisodeDropboxValues());
            AttributeManager.setSeasonDropbox(session, getSeasonDropboxValues());
            AttributeManager.setYearList(session, getYearList());
            
            redirectToPage("seriesInfo.jsp", request, response);
        } else {
            redirectHome(request, response);
        }
    }
    
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
            AttributeManager.setGenreList(session, genreList);
            AttributeManager.setEpisodeDropbox(session, getEpisodeDropboxValues());
            AttributeManager.setSeasonDropbox(session, getSeasonDropboxValues());
            AttributeManager.setYearList(session, getYearList());

            String id = request.getParameter("Edit");
            if (id != null) {
                SeriesService seriesService = new SeriesService();
                Series series = seriesService.getById(Integer.parseInt(id));
                AttributeManager.setSeries(session, series);
            }

            redirectToPage("seriesInfo.jsp", request, response);
        } else {
            redirectHome(request, response);
        }
    }
    
    private List<Integer> getEpisodeDropboxValues(){
        List<Integer> epVals=new ArrayList<Integer>();
        for (int i = 1; i < 51; i++) {
            epVals.add(i);
        }
        return epVals;
    }
    
    private List<Integer> getSeasonDropboxValues(){
        List<Integer> seasonVals=new ArrayList<Integer>();
        for (int i = 1; i < 51; i++) {
            seasonVals.add(i);
        }
        return seasonVals;
    }
    
}
