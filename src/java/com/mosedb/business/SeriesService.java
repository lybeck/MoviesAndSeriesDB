/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.business;

import com.mosedb.dao.seriesDao.EpisodeDao;
import com.mosedb.dao.seriesDao.SeriesDao;
import com.mosedb.dao.seriesDao.SeriesGenreDao;
import com.mosedb.dao.seriesDao.SeriesNameDao;
import com.mosedb.models.Episode;
import com.mosedb.models.LangId;
import com.mosedb.models.Series;
import com.mosedb.models.User;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Lasse
 */
public class SeriesService extends AbstractService {

    /**
     * Retrieves a list of series the user has stored, or a list of all users
     * series if the user is admin.
     *
     * @param user User, whose series are retrieved. If user is admin
     * ({@link User#isAdmin()} returns {@code true}) all users' series are
     * retrieved.
     * @param seen Retrieves only series with the same value on {@code seen}. If
     * parameter is {@code null} it is ignored.
     * @return A list of series, or {@code null} if the database query fails.
     */
    public List<Series> getSeries(User user, Boolean seen) {
        SeriesDao seriesDao;
        try {
            seriesDao = new SeriesDao();
        } catch (SQLException ex) {
            reportConnectionError(ex);
            return null;
        }
        List<Series> seriesList;
        try {
            if (user.isAdmin()) {
                seriesList = seriesDao.getAllSeries();
            } else {
                seriesList = seriesDao.getSeries(user.getUsername());
            }
        } catch (SQLException ex) {
            reportError("Error while retrieving seriess by username.", ex);
            return null;
        }

        addNames(seriesList);
        addEpisodes(seriesList);

        seriesDao.closeConnection();

        seriesList = filterBySeen(seriesList, seen);
        Collections.sort(seriesList);

        return seriesList;
    }

    /**
     * Retrieves a list of the user's series with a name corresponding to the
     * search term, or a list of all users series if the user is admin.
     *
     * @param user User, whose series are retrieved. If user is admin
     * ({@link User#isAdmin()} returns {@code true}) all users series are
     * retrieved.
     * @param search Search term, which is compared to series names. If the
     * search term consists of many words (separated by white spaces) all the
     * search terms are matches (by AND).
     * @param seen Retrieves only series with the same value on {@code seen}. If
     * parameter is {@code null} it is ignored.
     * @return A list of series, or {@code null} if the database query fails.
     */
    public List<Series> getByName(User user, String search, Boolean seen) {
        search = search.trim();
        search = search.replaceAll("\\s+", " ");
        List<String> searchList = Arrays.asList(search.split(" "));
        SeriesNameDao seriesNameDao;
        try {
            seriesNameDao = new SeriesNameDao();
        } catch (SQLException ex) {
            reportConnectionError(ex);
            return null;
        }
        List<Series> seriesList;
        try {
            if (searchList.size() == 1) {
                seriesList = seriesNameDao.getSeriesByName(search, user);
            } else {
                seriesList = seriesNameDao.getSeriesByName(searchList, user);
            }
            addEpisodes(seriesList);
        } catch (SQLException ex) {
            reportError("Error while trying to get seriesids by name from seriesnamedao.", ex);
            return null;
        }

        seriesNameDao.closeConnection();

        addEpisodes(seriesList);
        seriesList = filterBySeen(seriesList, seen);
        Collections.sort(seriesList);

        return seriesList;
    }

    /**
     * Retrieves a list of the user's series with a genre corresponding to the
     * search term, or a list of all users series if the user is admin.
     *
     * @param user User, whose series are retrieved. If user is admin
     * ({@link User#isAdmin()} returns {@code true}) all users series are
     * retrieved.
     * @param genre Search term, which is compared to series genres.
     * @param seen Retrieves only series with the same value on {@code seen}. If
     * parameter is {@code null} it is ignored.
     * @return A list of series, or {@code null} if the database query fails.
     */
    public List<Series> getByGenre(User user, String search, Boolean seen) {
        SeriesGenreDao seriesGenreDao;
        try {
            seriesGenreDao = new SeriesGenreDao();
        } catch (SQLException ex) {
            reportConnectionError(ex);
            return null;
        }
        List<Series> seriesList;
        try {
            seriesList = seriesGenreDao.getSeriesByGenre(search, user);
        } catch (SQLException ex) {
            reportError("Error while retrieving seriess by genre from database!", ex);
            return null;
        }
        seriesGenreDao.closeConnection();

        addNames(seriesList);
        addEpisodes(seriesList);

        seriesList = filterBySeen(seriesList, seen);
        Collections.sort(seriesList);

        return seriesList;
    }

    /**
     * Retrieves a list of the user's series with a media format corresponding
     * to the search term, or a list of all users series if the user is admin.
     *
     * @param user User, whose series are retrieved. If user is admin
     * ({@link User#isAdmin()} returns {@code true}) all users series are
     * retrieved.
     * @param mediaformat Search term, which is compared to series' media
     * formats.
     * @param seen Retrieves only series with the same value on {@code seen}. If
     * parameter is {@code null} it is ignored.
     * @return A list of series, or {@code null} if the database query fails.
     */
    public List<Series> getByMediaFormat(User user, String searchField, Boolean seen) {
        return getSeries(user, seen);
    }

    /**
     * Adds names to the series from the database.
     *
     * @param series Series to be added names to.
     */
    private void addNames(Series series) {
        SeriesNameDao seriesNameDao;
        try {
            seriesNameDao = new SeriesNameDao();
        } catch (SQLException ex) {
            reportConnectionError(ex);
            return;
        }
        try {
            series.setNames(seriesNameDao.getSeriesNames(series.getId()));
        } catch (SQLException ex) {
            reportError("Error while trying to retirieve names for series with id: " + series.getId(), ex);
        }
        seriesNameDao.closeConnection();
    }

    /**
     * Adds names to all the series from the database.
     *
     * @param seriesList List of series to be added names to.
     */
    private void addNames(List<Series> seriesList) {
        SeriesNameDao seriesNameDao;
        try {
            seriesNameDao = new SeriesNameDao();
        } catch (SQLException ex) {
            reportConnectionError(ex);
            return;
        }
        for (Series series : seriesList) {
            try {
                series.setNames(seriesNameDao.getSeriesNames(series.getId()));
            } catch (SQLException ex) {
                reportError("Error while trying to retirieve names for series with id: " + series.getId(), ex);
            }
        }
        seriesNameDao.closeConnection();
    }

    /**
     * Adds genres to the series from the database.
     *
     * @param series Series to be added genres to.
     */
    private void addGenres(Series series) {
        SeriesGenreDao seriesGenreDao;
        try {
            seriesGenreDao = new SeriesGenreDao();
        } catch (SQLException ex) {
            reportConnectionError(ex);
            return;
        }
        try {
            series.setGenres(seriesGenreDao.getGenresById(series.getId()));
        } catch (SQLException ex) {
            reportError("Error retrieving series genres from database!", ex);
        }
    }

    /**
     * Adds all episodes to the series from the database.
     *
     * @param series Series to be added episodes to.
     */
    private void addEpisodes(Series series) {
        series.setEpisodes(getAllEpisodes(series.getId()));
    }

    /**
     * Adds all episodes to all the series from the database.
     *
     * @param seriesList List of series to be added episodes to.
     */
    private void addEpisodes(List<Series> seriesList) {
        for (Series singleSeries : seriesList) {
            addEpisodes(singleSeries);
        }
    }

    /**
     * Filters the list of series by the {@code seen} variable. Excludes all
     * series from the list where {@link Series#isSeen()}{@code !=seen}.
     *
     * @param seriesList List to be filtered.
     * @param seen Variable to be matched with series.
     * @return List of series where {@link Series#isSeen()}{@code ==seen} for
     * every series in the list.
     */
    private List<Series> filterBySeen(List<Series> seriesList, Boolean seen) {
        if (seen == null) {
            return seriesList;
        }
        List<Series> newList = new ArrayList<Series>();
        for (Series series : seriesList) {
            if (series.isSeen() == seen) {
                newList.add(series);
            }
        }
        return newList;
    }

    /**
     * Adds a series to the database.
     *
     * @param user The series' owner.
     * @param series Series to be stored.
     * @return {@code true} if series was successfully added, otherwise
     * {@code false}.
     */
    public boolean addSeries(User user, Series series) {
        int id = addToTableSeries(user);
        if (id <= 0) {
            return false;
        }
        series.setId(id);

        boolean success = addToTableSeriesname(series);
        if (!success) {
            removeSeries(series.getId());
            return false;
        }

        return addToTableGenres(series);
    }

    /**
     * Adds an empty series to the table 'series' in the database.
     *
     * @param user The series' owner.
     * @return {@code true} if information was successfully added, otherwise
     * {@code false}.
     */
    private int addToTableSeries(User user) {
        SeriesDao seriesDao;
        try {
            seriesDao = new SeriesDao();
        } catch (SQLException ex) {
            reportConnectionError(ex);
            return -1;
        }
        try {
            int id = seriesDao.addSeries(user);
            seriesDao.closeConnection();
            return id;
        } catch (SQLException ex) {
            reportError("Error trying to add series to database!", ex);
            return -1;
        }
    }

    /**
     * Adds information from the series to the table 'seriesname' in the
     * database.
     *
     * @param series Series to be stored.
     * @return {@code true} if information was successfully added, otherwise
     * {@code false}.
     */
    private boolean addToTableSeriesname(Series series) {
        SeriesNameDao seriesNameDao;
        try {
            seriesNameDao = new SeriesNameDao();
        } catch (SQLException ex) {
            reportConnectionError(ex);
            return false;
        }
        try {
            boolean success = seriesNameDao.addNames(series.getId(), series.getNames());
            seriesNameDao.closeConnection();
            return success;
        } catch (SQLException ex) {
            reportError("Error adding series names to database!", ex);
            return false;
        }
    }

    /**
     * Adds information from the series to the table 'seriesgenre' in the
     * database.
     *
     * @param series Series to be stored.
     * @return {@code true} if information was successfully added, otherwise
     * {@code false}.
     */
    private boolean addToTableGenres(Series series) {
        SeriesGenreDao seriesGenreDao;
        try {
            seriesGenreDao = new SeriesGenreDao();
        } catch (SQLException ex) {
            reportConnectionError(ex);
            return false;
        }
        try {
            boolean success = seriesGenreDao.addGenres(series.getId(), series.getGenres());
            seriesGenreDao.closeConnection();
            if (!success) {
                return false;
            }
        } catch (SQLException ex) {
            reportError("Error adding series genres to database!", ex);
            return false;
        }
        return true;
    }

    /**
     * Permanently deletes the series with the given {@code seriesid} from the
     * database.
     *
     * @param seriesid Id of the series.
     * @return {@code true} if information was successfully added, otherwise
     * {@code false}.
     */
    public boolean removeSeries(int seriesid) {
        SeriesDao seriesDao;
        try {
            seriesDao = new SeriesDao();
        } catch (SQLException ex) {
            reportConnectionError(ex);
            return false;
        }
        try {
            seriesDao.removeSeries(seriesid);
            seriesDao.closeConnection();
        } catch (SQLException ex) {
            reportError("Error trying to delete series from database!", ex);
            return false;
        }
        return false;
    }

    /**
     * Retrieves all episodes to the series with the given {@code seriesid} from
     * the database.
     *
     * @param seriesid Id of the series.
     * @return A list of episodes, or {@code null} if the database query fails.
     */
    public List<Episode> getAllEpisodes(int seriesid) {
        EpisodeDao episodeDao;
        try {
            episodeDao = new EpisodeDao();
        } catch (SQLException ex) {
            reportConnectionError(ex);
            return null;
        }
        List<Episode> episodeList;
        try {
            episodeList = episodeDao.getAllEpisodes(seriesid);
            episodeDao.closeConnection();
        } catch (SQLException ex) {
            reportError("Error retrieving episodes for series!", ex);
            return null;
        }
        return episodeList;
    }

    /**
     * Retrieves the series with the given {@code seriesid} from the database.
     *
     * @param seriesid Id of the series.
     * @return Series, or {@code null} if the database query fails.
     */
    public Series getById(int seriesid) {
        SeriesDao seriesDao;
        try {
            seriesDao = new SeriesDao();
        } catch (SQLException ex) {
            reportConnectionError(ex);
            return null;
        }
        Series series;
        try {
            series = seriesDao.getById(seriesid);
            seriesDao.closeConnection();
        } catch (SQLException ex) {
            reportError("Error retrieving series from database!", ex);
            return null;
        }
        if (series == null) {
            return null;
        }
        addNames(series);
        addGenres(series);
        addEpisodes(series);
        return series;
    }

    /**
     * Updates the names of the series.
     *
     * @param series Series to be updated.
     * @param names Map containing the new name values.
     * @return {@code true} if information was successfully added, otherwise
     * {@code false}.
     */
    public boolean updateNames(Series series, Map<LangId, String> names) {
        SeriesNameDao seriesNameDao;
        try {
            seriesNameDao = new SeriesNameDao();
        } catch (SQLException ex) {
            reportConnectionError(ex);
            return false;
        }
        try {
            seriesNameDao.removeNames(series.getId());
            boolean success = seriesNameDao.addNames(series.getId(), names);
            seriesNameDao.closeConnection();
            if (!success) {
                return false;
            }
        } catch (SQLException ex) {
            reportError("Error while updating series names to database!", ex);
            return false;
        }
        return true;
    }

    /**
     * Updates the genres of the series.
     *
     * @param series Series to be updated.
     * @param genreList List containing the new genre values.
     * @return {@code true} if information was successfully added, otherwise
     * {@code false}.
     */
    public boolean updateGenres(Series series, List<String> genreList) {
        SeriesGenreDao seriesGenreDao;
        try {
            seriesGenreDao = new SeriesGenreDao();
        } catch (SQLException ex) {
            reportConnectionError(ex);
            return false;
        }
        try {
            boolean success = seriesGenreDao.updateGenres(series.getId(), genreList);
            seriesGenreDao.closeConnection();
            if (!success) {
                return false;
            }
        } catch (SQLException ex) {
            reportError("Error while updating series genres!", ex);
            return false;
        }
        return true;
    }

    /**
     * Adds a new season to the series to the database.
     *
     * @param seriesid Id of the series.
     * @param seasonNumber Number of the season to be added.
     * @param nrOfEpisodes Number of episodes in the season.
     * @param seen Default value of the {@code seen} variable for the episodes
     * in the new season.
     * @return {@code true} if information was successfully added, otherwise
     * {@code false}.
     */
    public boolean addNewSeason(int seriesid, int seasonNumber, int nrOfEpisodes, boolean seen) {
        return addNewSeason(seriesid, seasonNumber, nrOfEpisodes, seen, null);
    }

    /**
     * Adds a new season to the series to the database.
     *
     * @param seriesid Id of the series.
     * @param seasonNumber Number of the season to be added.
     * @param nrOfEpisodes Number of episodes in the season.
     * @param seen Default value of the {@code seen} variable for the episodes
     * in the new season.
     * @param year Default value of the {@code year} variable for the episodes
     * in the new season.
     * @return {@code true} if information was successfully added, otherwise
     * {@code false}.
     */
    public boolean addNewSeason(int seriesid, int seasonNumber, int nrOfEpisodes, boolean seen, Integer year) {
        EpisodeDao episodeDao;
        try {
            episodeDao = new EpisodeDao();
        } catch (SQLException ex) {
            reportConnectionError(ex);
            return false;
        }
        try {
            boolean success;
            for (int i = 1; i <= nrOfEpisodes; i++) {
                success = episodeDao.addEpisode(seriesid, seasonNumber, i, seen, year);
                if (!success) {
                    return false;
                }
            }
        } catch (SQLException ex) {
            reportError("Error while adding episodes to database!", ex);
            return false;
        }
        episodeDao.closeConnection();
        return true;
    }

    /**
     * Updates the information of an episode.
     *
     * @param episode Episode to be updated, containing all info to be stored in
     * the database.
     * @return {@code true} if information was successfully added, otherwise
     * {@code false}.
     */
    public boolean updateEpisode(Episode episode) {
        EpisodeDao episodeDao;
        try {
            episodeDao = new EpisodeDao();
        } catch (SQLException ex) {
            reportConnectionError(ex);
            return false;
        }
        try {
            episodeDao.updateEpisode(episode);
            episodeDao.closeConnection();
        } catch (SQLException ex) {
            reportError("Error trying to update episode info!", ex);
            return false;
        }
        return true;
    }

    /**
     * Permanently deletes the specified season from the database.
     *
     * @param seriesid Id of the series.
     * @param seasonnumber Number of the season to be deleted.
     * @return{@code true} if information was successfully added, otherwise
     * {@code false}.
     */
    public boolean removeSeason(int seriesid, int seasonnumber) {
        SeriesDao seriesDao;
        try {
            seriesDao = new SeriesDao();
        } catch (SQLException ex) {
            reportConnectionError(ex);
            return false;
        }
        try {
            seriesDao.removeSeason(seriesid, seasonnumber);
            seriesDao.closeConnection();
        } catch (SQLException ex) {
            reportError("Error trying to remove season from database!", ex);
            return false;
        }
        return true;
    }
}
