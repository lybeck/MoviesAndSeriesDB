package com.mosedb.servlet.seriesServlet;

import com.mosedb.business.GenreService;
import com.mosedb.business.SeriesService;
import com.mosedb.models.Format;
import com.mosedb.models.Series;
import com.mosedb.servlet.AbstractInfoServlet;
import com.mosedb.tools.AttributeManager;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Handles the connection between search.jsp and seriesInfo.jsp.
 *
 * @author Roope
 */
public class SeriesInfoServlet extends AbstractInfoServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        if (isUserLoggedIn(request)) {
            restorePage("seriesInfo.jsp", request, response);
        } else {
            redirectHome(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        if (isUserLoggedIn(request)) {
            HttpSession session = request.getSession(true);
            AttributeManager.removeSeries(session);

            List<String> genreList = new GenreService().getAllGenres();
            AttributeManager.setGenreList(session, genreList);
            List<String> formatList = Format.getAllMediaFormats();
            AttributeManager.setFormatList(session, formatList);
            AttributeManager.setGenreList(session, genreList);
            AttributeManager.setYearList(session, getYearList());

            String id = request.getParameter("Edit");
            if (id != null) {
                SeriesService seriesService = new SeriesService();
                Series series = seriesService.getById(Integer.parseInt(id));
                AttributeManager.setSeries(session, series);
                AttributeManager.setEpisodeDropbox(session, getEpisodeDropboxValues());
                AttributeManager.setSeasonDropbox(session, getSeasonDropboxValues(series));
                redirectToPage("seriesInfo.jsp", request, response);
                return;
            }
        }
        redirectHome(request, response);
    }
}
