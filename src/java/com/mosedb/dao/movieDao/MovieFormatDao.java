/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.dao.movieDao;

import com.mosedb.dao.AbstractDao;
import com.mosedb.models.Format;
import com.mosedb.models.Format.MediaFormat;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Lasse
 */
public class MovieFormatDao extends AbstractDao {

    public MovieFormatDao() throws SQLException {
        super();
    }

    /**
     * Adds a format to the 'movieformat' table in the database.
     *
     * @param movieid Id of the movie.
     * @param formatid Id of the format information.
     * @return {@code true} if the addition succeeded, {@code false} otherwise.
     * @throws SQLException
     */
    public boolean addMovieFormat(int movieid, int formatid) throws SQLException {
        String sql = "insert into mosedb.movieformat (movieid,formatid) values (?,?)";
        return executeUpdate(sql, movieid, formatid);
    }

    /**
     * Removes all the media format information associated with the movie.
     *
     * @param movieid Id of the movie.
     * @throws SQLException
     */
    public void removeMovieFormats(int movieid) throws SQLException {
        String sql = "delete from mosedb.format f using mosedb.movieformat mf "
                + "where mf.movieid=? and f.formatid=mf.formatid";
        executeUpdate(sql, movieid);
    }

    /**
     * Retrieves the movieids associated with the {@code mediaformat}.
     *
     * @param mediaformat Media format to be queried by.
     * @return A set of movieids.
     * @throws SQLException
     */
    public Set<Integer> getMovieIdsByMediaFormat(String mediaformat) throws SQLException {
        String sql = "select movieid from mosedb.format f, mosedb.movieformat mf "
                + "where f.formatid=mf.formatid and f.mediaformat=cast(? as mosedb.mediaformat)";
        ResultSet result = executeQuery(sql, mediaformat);
        Set<Integer> set = new HashSet<Integer>();
        while (result.next()) {
            int id = result.getInt("movieid");
            if (!set.contains(id)) {
                set.add(id);
            }
        }
        result.close();
        return set;
    }

    /**
     * Retrieves the movie's formats from the database.
     *
     * @param movieid Id of the movie.
     * @return A list of formats.
     * @throws SQLException
     */
    public List<Format> getFormats(int movieid) throws SQLException {
        String sql = "select f.mediaformat, f.filetype, f.resox, f.resoy "
                + "from mosedb.movieformat mf, mosedb.format f "
                + "where mf.movieid=? and f.formatid=mf.formatid";
        ResultSet result = executeQuery(sql, movieid);
        List<Format> list = new ArrayList<Format>();
        while (result.next()) {
            Format format;
            MediaFormat mediaFormat = Format.getMediaFormat(result.getString("mediaformat"));
            if (mediaFormat == MediaFormat.dc) {
                String filetype = result.getString("filetype");
                int resox = result.getInt("resox");
                int resoy = result.getInt("resoy");
                if (resox == 0 || resoy == 0) {
                    format = new Format(mediaFormat, filetype);
                } else {
                    format = new Format(mediaFormat, filetype, resox, resoy);
                }
            } else {
                format = new Format(mediaFormat);
            }
            list.add(format);
        }
        return list;
    }
}
