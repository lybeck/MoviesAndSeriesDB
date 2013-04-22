/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.dao.seriesDao;

import com.mosedb.dao.AbstractDao;
import com.mosedb.models.LangId;
import com.mosedb.models.Series;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EnumMap;
import java.util.Map;

/**
 *
 * @author llybeck
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
}
