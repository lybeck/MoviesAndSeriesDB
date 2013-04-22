/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.business;

import com.mosedb.dao.seriesDao.SeriesDao;
import com.mosedb.dao.seriesDao.SeriesGenreDao;
import com.mosedb.dao.seriesDao.SeriesNameDao;
import com.mosedb.models.Series;
import com.mosedb.models.User;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author llybeck
 */
public class SeriesService extends AbstractService {

    public List<Series> getSeries(User user) {
        long start = System.currentTimeMillis();
        SeriesDao seriesDao;
        try {
            seriesDao = new SeriesDao();
        } catch (SQLException ex) {
            reportError("Error while connecting to database!", ex);
            return null;
        }
        List<Series> series;
        try {
            if (user.isAdmin()) {
                series = seriesDao.getAllSeries();
            } else {
                series = seriesDao.getSeries(user.getUsername());
            }
        } catch (SQLException ex) {
            reportError("Error while retrieving movies by username.", ex);
            return null;
        }

        addNames(series);
        long end = System.currentTimeMillis();
        double time = (end - start) * 1.0 / 1000;
        System.out.println("Search for series took " + time + " seconds.");

        seriesDao.closeConnection();

//        Collections.sort(series);

        return series;
    }

    private void addNames(List<Series> seriesList) {
        SeriesNameDao seriesNameDao;
        try {
            seriesNameDao = new SeriesNameDao();
        } catch (SQLException ex) {
            reportError("Error while connecting to database!", ex);
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
            reportError("Error connecting to database!", ex);
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
            reportError("Error connecting to database!", ex);
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
            reportError("Error connnecting to database!", ex);
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

    private void removeSeries(int id) {
        SeriesDao seriesDao;
        try {
            seriesDao = new SeriesDao();
        } catch (SQLException ex) {
            reportError("Error connecting to database!", ex);
            return;
        }
        try {
            seriesDao.removeSeries(id);
            seriesDao.closeConnection();
        } catch (SQLException ex) {
            reportError("Error trying to delete series from database!", ex);
        }
    }
}
