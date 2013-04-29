/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.servlet.seriesServlet;

import com.mosedb.business.GenreService;
import com.mosedb.business.SeriesService;
import com.mosedb.models.LangId;
import com.mosedb.models.Series;
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
 *
 * @author Lasse
 */
public class AddSeriesServlet extends AbstractInfoServlet {

    private static final int MAX_EPISODE_COUNT = 50;
    private static final int MAX_SEASON_COUNT = 50;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        if (isUserLoggedIn(request)) {
            HttpSession session = request.getSession(true);

            List<String> genreList = new GenreService().getAllGenres();
            AttributeManager.setGenreList(session, genreList);

            redirectToPage("addSeries.jsp", request, response);
        } else {
            redirectHome(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        if (isUserLoggedIn(request)) {
            HttpSession session = request.getSession(true);
            Map<LangId, String> names = getNameMap(request);
            if (names.isEmpty()) {
                AttributeManager.setErrorMessage(request, "One name must be specified!");
                restorePage("addSeries.jsp", request, response);
                return;
            }
            List<String> genreList = getGenres(request);

            Series series = new Series(names, genreList);
            User user = AttributeManager.getUserInSession(request.getSession(true));
            boolean success = new SeriesService().addSeries(user, series);
            if (!success) {
                AttributeManager.setErrorMessage(request, "Series addition caused an unknown error..");
                restorePage("addSeries.jsp", request, response);
                return;
            }
            AttributeManager.setSeries(session, series);
            AttributeManager.setEpisodeDropbox(session, getEpisodeDropboxValues());
            AttributeManager.setSeasonDropbox(session, getSeasonDropboxValues(series));
            redirectToPage("seriesInfo", request, response);
        } else {
            redirectHome(request, response);
        }
    }
}
