/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.business;

import com.mosedb.models.Format;
import com.mosedb.models.Movie;
import com.mosedb.models.User;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author llybeck
 */
public class MovieService {

    public List<Movie> getMovies(User user) {

        List<Movie> list = new ArrayList<Movie>();

        Map<Movie.LangId, String> names;
        List<String> genres;
        List<Format> formats;

        names = new EnumMap<Movie.LangId, String>(Movie.LangId.class);
        names.put(Movie.LangId.eng, "Snow white");
        names.put(Movie.LangId.fi, "Lumikki");
        names.put(Movie.LangId.swe, "Snövit");
        genres = new ArrayList<String>();
        genres.add("Animation");
        genres.add("Family");
        genres.add("Fantasy");
        formats = new ArrayList<Format>();
        formats.add(new Format(1, Format.MediaFormat.bd));
        formats.add(new Format(2, Format.MediaFormat.dc, "mkv", 1920, 1080));
        list.add(new Movie(1, names, "roope", true, 1937, genres, formats));

        names = new EnumMap<Movie.LangId, String>(Movie.LangId.class);
        names.put(Movie.LangId.eng, "Terminator 2");
        genres = new ArrayList<String>();
        genres.add("Action");
        genres.add("Sci-fi");
        genres.add("Thriller");
        formats = new ArrayList<Format>();
        formats.add(new Format(1, Format.MediaFormat.vhs));
        list.add(new Movie(1, names, "roope", true, 1991, genres, formats));

        if (user.getUsername().equals("roope")) {
            names = new EnumMap<Movie.LangId, String>(Movie.LangId.class);
            names.put(Movie.LangId.eng, "Back to the Future");
            names.put(Movie.LangId.fi, "Paluu tulevaisuuteen");
            genres = new ArrayList<String>();
            genres.add("Adventure");
            genres.add("Comedy");
            genres.add("Sci-fi");
            formats = new ArrayList<Format>();
            formats.add(new Format(2, Format.MediaFormat.dc, "avi", 480, 360));
            list.add(new Movie(1, names, "roope", true, 1985, genres, formats));
        }

        return list;
    }
}
