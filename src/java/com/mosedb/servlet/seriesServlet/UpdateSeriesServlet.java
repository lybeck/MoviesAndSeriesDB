/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.servlet.seriesServlet;

import com.mosedb.business.SeriesService;
import com.mosedb.models.Episode;
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
    private static final String NEW_SEASON_NUMBER_DROPBOX = "new_season_select";
    private static final String NEW_SEASON_EPISODE_NUMBER_DROPBOX = "new_season_episode_select";
    private static final String NEW_SEASON_YEAR_DROPBOX = "new_season_year_select";
    private static final String NEW_SEASON_SEEN_CHECKBOX = "new_season_seen_checkbox";
    private static final String EPISODE_NAME_INPUT = "episode_name_";

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
                removeSelectedEpisodes(request, response);
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

        boolean success;
        boolean totalSuccess = true;

        /*
         * update names
         */
        Map<LangId, String> names = getNameMap(request);
        if (names.isEmpty()) {
            totalSuccess = false;
        } else {
            success = seriesService.updateNames(series, names);
            if (success) {
                series.setNames(names);
            } else {
                totalSuccess = false;
            }
        }

        /*
         * update genres
         */
        List<String> genreList = getGenres(request);
        success = seriesService.updateGenres(series, genreList);
        if (success) {
            series.setGenres(genreList);
        } else {
            totalSuccess = false;
        }

        /*
         * update episode info
         */
        updateEpisodes(request, series);

        /*
         * add season
         */
        success = addNewSeason(request, series);
        if (!success) {
            totalSuccess = false;
        }

        if (totalSuccess) {
            AttributeManager.setSuccessMessage(request, "Changes updated successfully!");
        } else {
            AttributeManager.setErrorMessage(request, "Failed to update series information!");
        }
        restorePage("seriesInfo.jsp", request, response);
    }

    private void removeSeries(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void removeSelectedEpisodes(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private boolean addNewSeason(HttpServletRequest request, Series series) {
        String seasonNrString = request.getParameter(NEW_SEASON_NUMBER_DROPBOX);
        if (seasonNrString == null) {
            return true;
        }
        int seasonNumber = Integer.parseInt(seasonNrString);
        int nrOfEpisodes = Integer.parseInt(request.getParameter(NEW_SEASON_EPISODE_NUMBER_DROPBOX));
        String yearString = request.getParameter(NEW_SEASON_YEAR_DROPBOX);
        Integer year = null;
        if (!yearString.isEmpty()) {
            year = Integer.parseInt(yearString);
        }
        boolean seen;
        if (request.getParameter(NEW_SEASON_SEEN_CHECKBOX) != null) {
            seen = true;
        } else {
            seen = false;
        }

        boolean success;
        SeriesService seriesService = new SeriesService();
        if (year != null) {
            success = seriesService.addNewSeason(series.getId(), seasonNumber, nrOfEpisodes, seen, year);
        } else {
            success = seriesService.addNewSeason(series.getId(), seasonNumber, nrOfEpisodes, seen);
        }
        if (!success) {
            return false;
        }
        series.setEpisodes(seriesService.getAllEpisodes(series.getId()));
        AttributeManager.setSeasonDropbox(request.getSession(true), getSeasonDropboxValues(series));
        return true;
    }

    private boolean updateEpisodes(HttpServletRequest request, Series series) {
        boolean success = true;
        for (Episode episode : series.getEpisodes()) {
            success = updateEpisode(request, episode) && success;
        }
        return success;
    }

    private boolean updateEpisode(HttpServletRequest request, Episode episode) {
        boolean changes = false;
        String episodeTag = episode.getSeriesId() + "_" + episode.getSeasonNumber() + "_" + episode.getEpisodeNumber();
        String newName = request.getParameter(EPISODE_NAME_INPUT + episodeTag);
        if (!newName.equals(episode.getEpisodeName())) {
            episode.setEpisodeName(newName);
            changes = true;
        }

        if (changes) {
            return new SeriesService().updateEpisode(episode);
        }
        return true;
    }
}
