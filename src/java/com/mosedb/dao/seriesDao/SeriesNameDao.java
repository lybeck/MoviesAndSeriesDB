/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.dao.seriesDao;

import com.mosedb.dao.AbstractDao;
import com.mosedb.models.LangId;
import com.mosedb.models.Series;
import com.mosedb.models.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Lasse
 */
public class SeriesNameDao extends AbstractDao {

    public SeriesNameDao() throws SQLException {
        super();
    }

    /**
     * Adds the names to be associated with the series.
     *
     * @param seriesid Id of the series.
     * @param names Map containing the names associated with the series.
     * @return {@code true} if the addition was successful, {@code false}
     * otherwise.
     * @throws SQLException
     */
    public boolean addNames(int seriesid, Map<LangId, String> names) throws SQLException {
        boolean success;
        for (LangId langId : names.keySet()) {
            success = addName(seriesid, langId, names.get(langId));
            if (!success) {
                return false;
            }
        }
        return true;
    }

    /**
     * Adds a name to a series.
     *
     * @param seriesid Id of the series.
     * @param langId Language of the name.
     * @param name Name to be associated with the series.
     * @return {@code true} if the addition was successful, {@code false}
     * otherwise.
     * @throws SQLException
     */
    public boolean addName(int seriesid, LangId langId, String name) throws SQLException {
        String sql = "insert into mosedb.seriesname (seriesid,langid,seriesname) values (?,cast(? as mosedb.langid),?)";
        return executeUpdate(sql, seriesid, langId, name);
    }

    /**
     * Retrieves the names associated with the series.
     *
     * @param seriesid Id of the series.
     * @return A map containing all names of the series.
     * @throws SQLException
     */
    public Map<LangId, String> getSeriesNames(int seriesid) throws SQLException {
        String sql = "select langid, seriesname from mosedb.seriesname where seriesid=?";
        ResultSet result = executeQuery(sql, seriesid);
        Map<LangId, String> map = new EnumMap<LangId, String>(LangId.class);
        while (result.next()) {
            LangId langid = LangId.getLangId(result.getString("langid"));
            String name = result.getString("seriesname");
            map.put(langid, name);
        }
        result.close();
        return map;
    }

    /**
     * Searches series with a name corresponding to the search term. The search
     * is <b>not</b> case sensitive.
     *
     * @param search Name to be queried.
     * @param user User whose series are queried. If the user is admin all
     * users' series are queried.
     * @return A list of series.
     * @throws SQLException
     */
    public List<Series> getSeriesByName(String search, User user) throws SQLException {
        return getSeriesByName(Arrays.asList(search), user);
    }

    /**
     * Searches series with a name corresponding to <b>all</b> the search terms.
     * The search is <b>not</b> case sensitive.
     *
     * @param searchList Names to be queried.
     * @param user User whose series are queried. If the user is admin all
     * users' series are queried.
     * @return A list of series.
     * @throws SQLException
     */
    public List<Series> getSeriesByName(List<String> searchList, User user) throws SQLException {
        String sql = "select distinct s.seriesid, s.owner from mosedb.series s, mosedb.seriesname sn "
                + "where s.seriesid=sn.seriesid and lower(sn.seriesname) like lower(?)";
        for (int i = 1; i < searchList.size(); i++) {
            sql += " and lower(sn.seriesname) like lower(?)";
        }
        List<String> searchTerms = new ArrayList<String>();
        for (String string : searchList) {
            searchTerms.add("%" + string + "%");
        }
        if (!user.isAdmin()) {
            sql += " and s.owner=?";
            searchTerms.add(user.getUsername());
        }
        ResultSet result = executeQuery(sql, searchTerms.toArray());
        List<Series> seriesList = new ArrayList<Series>();
        while (result.next()) {
            int id = result.getInt("seriesid");
            String owner = result.getString("owner");
            Series series = new Series(id, owner);
            series.setNames(getSeriesNames(id));
            seriesList.add(series);
        }
        result.close();
        return seriesList;
    }

    /**
     * Removes all names associated with the series.
     *
     * @param seriesid Id of the series.
     * @throws SQLException
     */
    public void removeNames(int seriesid) throws SQLException {
        String sql = "delete from mosedb.seriesname where seriesid=?";
        executeUpdate(sql, seriesid);
    }
}
