/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.servlets;

import com.mosedb.business.GenreService;
import com.mosedb.business.MovieService;
import com.mosedb.models.Format;
import com.mosedb.models.Format.MediaFormat;
import com.mosedb.models.Movie;
import com.mosedb.models.Movie.LangId;
import com.mosedb.models.User;
import com.mosedb.tools.AttributeManager;
import com.mosedb.tools.LoginManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author roopekoira
 */
public class AddMovieServlet extends MosedbServlet {

    private static final String ENG_NAME = "engName";
    private static final String FI_NAME = "fiName";
    private static final String SWE_NAME = "sweName";
    private static final String OTHER_NAME = "otherName";
    private static final String YEAR = "yearDropbox";
    private static final String GENRE_SELECT = "genreDropbox";
    private static final String MEDIA_FORMAT_SELECT = "mediaFormatDropbox";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isUserLoggedIn(request)) {
            HttpSession session = request.getSession(true);

            List<String> genreList = new GenreService().getAllGenres();
            AttributeManager.setGenreList(session, genreList);
            List<String> formatList = Format.getAllMediaFormats();
            AttributeManager.setFormatList(session, formatList);
            List<String> yearList = new ArrayList<String>();
            yearList.add("");
            int thisYear = new Date().getYear() + 1900;
            for (int y = thisYear; y >= 1900; --y) {
                yearList.add(y + "");
            }
            AttributeManager.setYearList(session, yearList);

            redirectToPage("addMovie.jsp", request, response);
        } else {
            redirectHome(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<LangId, String> names = getNameMap(request);
        if (names.isEmpty()) {
            AttributeManager.setErrorMessage(request, "One name must be specified!");
            restorePage("addMovie.jsp", request, response);
            return;
        }
        Integer movieYear = getYear(request);
        List<String> genreList = getGenres(request);
        List<Format> formatList = getFormats(request);

        Movie movie = new Movie(names, true, movieYear, genreList, formatList);
        User user = AttributeManager.getUserSessionKey(request.getSession(true));
        boolean success = new MovieService().addMovie(user, movie);

        restorePage("addMovie.jsp", request, response);
    }

    private Map<LangId, String> getNameMap(HttpServletRequest request) {
        String engName = request.getParameter(ENG_NAME).trim();
        String fiName = request.getParameter(FI_NAME).trim();
        String sweName = request.getParameter(SWE_NAME).trim();
        String otherName = request.getParameter(OTHER_NAME).trim();

        Map<LangId, String> names = new EnumMap<LangId, String>(LangId.class);
        if (!engName.isEmpty()) {
            names.put(LangId.eng, engName);
        }
        if (!fiName.isEmpty()) {
            names.put(LangId.fi, engName);
        }
        if (!sweName.isEmpty()) {
            names.put(LangId.swe, engName);
        }
        if (!otherName.isEmpty()) {
            names.put(LangId.other, engName);
        }
        return names;
    }

    private Integer getYear(HttpServletRequest request) throws NumberFormatException {
        String year = request.getParameter(YEAR);
        Integer movieYear = null;
        if (!year.isEmpty()) {
            movieYear = Integer.parseInt(year);
        }
        return movieYear;
    }

    private List<String> getGenres(HttpServletRequest request) {
        List<String> genreList = new ArrayList<String>();
        int i = 1;
        while (true) {
            String genre = request.getParameter(GENRE_SELECT + i);
            if (genre == null) {
                break;
            }
            if (!genreList.contains(genre)) {
                genreList.add(genre);
            }
            ++i;
        }
        Collections.sort(genreList);
        return genreList;
    }

    private List<Format> getFormats(HttpServletRequest request) {
        List<Format> formatList = new ArrayList<Format>();
        int i = 1;
        while (true) {
            String mediaFormatString = request.getParameter(MEDIA_FORMAT_SELECT + i);
            if (mediaFormatString == null) {
                break;
            }
            MediaFormat mediaFormat = Format.getMediaFormat(mediaFormatString);
            formatList.add(new Format(mediaFormat));
            ++i;
        }
        return formatList;
    }
}
