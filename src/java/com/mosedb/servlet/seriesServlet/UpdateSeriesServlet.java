/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.servlet.seriesServlet;

import com.mosedb.business.SeriesService;
import com.mosedb.models.LangId;
import com.mosedb.models.Series;
import com.mosedb.servlet.AbstractInfoServlet;
import com.mosedb.tools.AttributeManager;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author Lasse
 */
public class UpdateSeriesServlet extends AbstractInfoServlet {
    
    private static final String UPDATE_BUTTON = "update_series";
    private static final String DELETE_BUTTON = "delete_series";
    private static final String DELETE_SELECTED = "delete_selected_button";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        if (isUserLoggedIn(request)) {
            String clickedButton = request.getParameter("submit");
            if (clickedButton.equals(UPDATE_BUTTON)) {
                updateSeries(request, response);
            } else if (clickedButton.equals(DELETE_BUTTON)) {
                removeSeries(request, response);
            } else if (clickedButton.equals(DELETE_SELECTED)) {
                removeSelectedSeries(request, response);
            }
            
        } else {
            redirectHome(request, response);
        }
    }

    private void updateSeries(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AttributeManager.removeErrorMessage(request);
            AttributeManager.removeSuccessMessage(request);
            
            SeriesService seriesService = new SeriesService();
            Series series = AttributeManager.getSeries(request.getSession(true));

            Map<LangId, String> names = getNameMap(request);
            if (names.isEmpty()) {
                AttributeManager.setErrorMessage(request, "One name must be specified!");
                restorePage("seriesInfo.jsp", request, response);
                return;
            } else {
                boolean success = seriesService.updateNames(series, names);
                if (success) {
                    series.setNames(names);
                }
            }

            List<String> genreList = getGenres(request);

            boolean success = seriesService.updateGenres(series, genreList);
            if (success) {
                series.setGenres(genreList);
            } else {
                AttributeManager.setErrorMessage(request, "Failed to update series genres!");
            }

            AttributeManager.setSuccessMessage(request, "Changes updated successfully!");
            restorePage("seriesInfo.jsp", request, response);
        }

    private void removeSeries(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void removeSelectedSeries(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
   