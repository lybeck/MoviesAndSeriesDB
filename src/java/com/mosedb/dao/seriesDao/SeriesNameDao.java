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
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Lasse
 */
public class SeriesNameDao extends AbstractDao {

    public SeriesNameDao() throws SQLException {
        super();
    }

    public boolean addNames(int id, Map<LangId, String> names) throws SQLException {
        boolean success;
        for (LangId langId : names.keySet()) {
            success = addName(id, langId, names.get(langId));
            if (!success) {
                return false;
            }
        }
        return true;
    }

    public boolean addName(int id, LangId langId, String name) throws SQLException {
        String sql = "insert into mosedb.seriesname (seriesid,langid,seriesname) values (?,cast(? as mosedb.langid),?)";
        return executeUpdate(sql, id, langId, name);
    }

    public Map<LangId, String> getSeriesNames(int id) throws SQLException {
        String sql = "select langid, seriesname from mosedb.seriesname where seriesid=?";
        ResultSet result = executeQuery(sql, id);
        Map<LangId, String> map = new EnumMap<LangId, String>(LangId.class);
        while (result.next()) {
            LangId langid = LangId.getLangId(result.getString("langid"));
            String name = result.getString("seriesname");
            map.put(langid, name);
        }
        result.close();
        return map;
    }

    public List<Series> getSeriesByName(String search, User user) throws SQLException {
        String sql = "select distinct s.seriesid, s.owner from mosedb.series s, mosedb.seriesname sn "
                + "where s.seriesid=sn.seriesid and lower(sn.seriesname) like lower(?)";
        ResultSet result;
        if (user.isAdmin()) {
            result = executeQuery(sql, "%" + search + "%");
        } else {
            sql += " and s.owner=?";
            result = executeQuery(sql, "%" + search + "%", user.getUsername());
        }

        List<Series> seriesList = new ArrayList<Series>();
        while (result.next()) {
            int id = result.getInt("seriesid");
            String owner = result.getString("owner");
            Series series = new Series(id, owner);
            series.setNames(getSeriesNames(id));
            seriesList.add(series);
        }
        return seriesList;
    }

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
        return seriesList;
    }

    public void removeNames(int id) throws SQLException {
        String sql = "delete from mosedb.seriesname where seriesid=?";
        executeUpdate(sql, id);
    }
}
