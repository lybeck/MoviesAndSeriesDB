/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.servlet;

import com.mosedb.models.Format;
import com.mosedb.models.LangId;
import com.mosedb.models.Movie;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Lasse
 */
public abstract class AbstractInfoServlet extends MosedbServlet {

    private static final String ENG_NAME = "engName";
    private static final String FI_NAME = "fiName";
    private static final String SWE_NAME = "sweName";
    private static final String OTHER_NAME = "otherName";
    private static final String YEAR = "yearDropbox";
    private static final String GENRE_SELECT = "genreDropbox";
    private static final String MEDIA_FORMAT_SELECT = "mediaFormatDropbox";
    private static final String SEEN_CHECKBOX = "seenCheckbox";
    private static final String FILE_TYPE = "fileType";
    private static final String RESOLUTION_X = "resox";
    private static final String RESOLUTION_Y = "resoy";
    

    protected Map<LangId, String> getNameMap(HttpServletRequest request) {
        String engName = request.getParameter(ENG_NAME).trim();
        String fiName = request.getParameter(FI_NAME).trim();
        String sweName = request.getParameter(SWE_NAME).trim();
        String otherName = request.getParameter(OTHER_NAME).trim();

        Map<LangId, String> names = new EnumMap<LangId, String>(LangId.class);
        if (!engName.isEmpty()) {
            names.put(LangId.eng, engName);
        }
        if (!fiName.isEmpty()) {
            names.put(LangId.fi, fiName);
        }
        if (!sweName.isEmpty()) {
            names.put(LangId.swe, sweName);
        }
        if (!otherName.isEmpty()) {
            names.put(LangId.other, otherName);
        }
        return names;
    }

    protected Integer getYear(HttpServletRequest request) throws NumberFormatException {
        String year = request.getParameter(YEAR);
        Integer movieYear = null;
        if (!year.isEmpty()) {
            movieYear = Integer.parseInt(year);
        }
        return movieYear;
    }

    protected List<String> getGenres(HttpServletRequest request) {
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

    protected List<Format> getFormats(HttpServletRequest request) {
        List<Format> formatList = new ArrayList<Format>();
        int i = 1;
        while (true) {
            String mediaFormatString = request.getParameter(MEDIA_FORMAT_SELECT + i);
            if (mediaFormatString == null) {
                break;
            }
            Format.MediaFormat mediaFormat = Format.getMediaFormat(mediaFormatString);
            if (mediaFormat == Format.MediaFormat.dc) {
                formatList.add(getDigitalFormat(request, i));
            } else {
                formatList.add(new Format(mediaFormat));
            }
            ++i;
        }
        return formatList;
    }

    protected Format getDigitalFormat(HttpServletRequest request, int i) {
        String fileType = request.getParameter(FILE_TYPE + i).trim();
        if (fileType.isEmpty()) {
            fileType = null;
        }
        String resoXString = request.getParameter(RESOLUTION_X + i).trim();
        String resoYString = request.getParameter(RESOLUTION_Y + i).trim();
        if (resoXString == null || resoXString.isEmpty() || resoYString == null || resoYString.isEmpty()) {
            return new Format(Format.MediaFormat.dc, fileType);
        } else {
            try {
                int resoX = Integer.parseInt(resoXString);
                int resoY = Integer.parseInt(resoYString);
                return new Format(Format.MediaFormat.dc, fileType, resoX, resoY);
            } catch (NumberFormatException e) {
                return new Format(Format.MediaFormat.dc, fileType);
            }
        }
    }

    protected boolean isSeen(HttpServletRequest request) {
        return request.getParameter(SEEN_CHECKBOX) != null;
    }

    protected List<String> getYearList() {
        List<String> yearList = new ArrayList<String>();
        yearList.add("");
        int thisYear = new Date().getYear() + 1900;
        for (int y = thisYear; y >= 1900; --y) {
            yearList.add(y + "");
        }
        return yearList;
    }
}
