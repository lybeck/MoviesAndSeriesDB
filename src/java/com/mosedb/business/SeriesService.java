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

    public List<Series> getByMediaFormat(User user, String searchField, Boolean seen) {
        return getSeries(user, seen);
    }

    private void addName(Series series) {
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

    private void addEpisodes(Series series) {
        series.setEpisodes(getAllEpisodes(series.getId()));
    }

    private void addEpisodes(List<Series> series) {
        for (Series singleSeries : series) {
            addEpisodes(singleSeries);
        }
    }

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

    public boolean removeSeries(int id) {
        SeriesDao seriesDao;
        try {
            seriesDao = new SeriesDao();
        } catch (SQLException ex) {
            reportConnectionError(ex);
            return false;
        }
        try {
            seriesDao.removeSeries(id);
            seriesDao.closeConnection();
        } catch (SQLException ex) {
            reportError("Error trying to delete series from database!", ex);
            return false;
        }
        return false;
    }

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

    public Series getById(int id) {
        SeriesDao seriesDao;
        try {
            seriesDao = new SeriesDao();
        } catch (SQLException ex) {
            reportConnectionError(ex);
            return null;
        }
        Series series;
        try {
            series = seriesDao.getById(id);
            seriesDao.closeConnection();
        } catch (SQLException ex) {
            reportError("Error retrieving series from database!", ex);
            return null;
        }
        if (series == null) {
            return null;
        }
        addName(series);
        addGenres(series);
        addEpisodes(series);
        return series;
    }

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

    public boolean addNewSeason(int seriesid, int seasonNumber, int nrOfEpisodes, boolean seen) {
        return addNewSeason(seriesid, seasonNumber, nrOfEpisodes, seen, null);
    }

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
