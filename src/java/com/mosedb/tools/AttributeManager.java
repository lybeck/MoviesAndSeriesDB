package com.mosedb.tools;

import com.mosedb.models.Movie;
import com.mosedb.models.User;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Easysimulation
 */
public class AttributeManager {
    
    private static String adminSessionKey = "adminSessionKey";
    private static String errorMessage = "errorMessage";
    private static String formatList = "formatList";
    private static String genreList = "genreList";
    private static String movieList = "movieList";
    private static String userSessionKey = "userSessionKey";
    private static String yearList = "yearList";

    public static boolean getAdminSessionKey(HttpSession session) {
        return (Boolean) session.getAttribute(adminSessionKey);
    }

    public static String getErrorMessage(HttpSession session) {
        return (String) session.getAttribute(errorMessage);
    }

    public static List<String> getFormatList(HttpSession session) {
        return (List<String>) session.getAttribute(formatList);
    }

    public static List<String> getGenreList(HttpSession session) {
        return (List<String>) session.getAttribute(genreList);
    }
    
    public static List<Movie> getMovieList(HttpSession session) {
        return (List<Movie>) session.getAttribute(movieList);
    }

    public static User getUserSessionKey(HttpSession session) {
        return (User) session.getAttribute(userSessionKey);
    }

    public static List<String> getYearList(HttpSession session) {
        return (List<String>) session.getAttribute(yearList);
    }

    //------------------Setters for Sessions----------------------------------
    
    public static void setAdminSessionKey(HttpSession session, boolean isAdmin) {
        session.setAttribute(adminSessionKey, isAdmin);
    }

    public static void setErrorMessage(HttpSession session, String message) {
        session.setAttribute(errorMessage, message);
    }

    public static void setFormatList(HttpSession session, List<String> formats) {
        session.setAttribute(formatList, formats);
    }

    public static void setGenreList(HttpSession session, List<String> genres) {
        session.setAttribute(genreList, genres);
    }
    
    public static void setMovieList(HttpSession session, List<Movie> movies) {
        session.setAttribute(movieList, movies);
    }

    public static void setUserSessionKey(HttpSession session, User user) {
        session.setAttribute(userSessionKey, user);
    }

    public static void setYearList(HttpSession session, List<String> years) {
        session.setAttribute(yearList, years);
    }
    
    //------------------Setters for Requests----------------------------------
    
    public static void setAdminSessionKey(HttpServletRequest request, boolean isAdmin) {
        request.setAttribute(adminSessionKey, isAdmin);
    }

    public static void setErrorMessage(HttpServletRequest request, String message) {
        request.setAttribute(errorMessage, message);
    }

    public static void setFormatList(HttpServletRequest request, List<String> formats) {
        request.setAttribute(formatList, formats);
    }

    public static void setGenreList(HttpServletRequest request, List<String> genres) {
        request.setAttribute(genreList, genres);
    }
    
    public static void setMovieList(HttpServletRequest request, List<Movie> movies) {
        request.setAttribute(movieList, movies);
    }

    public static void setUserSessionKey(HttpServletRequest request, User user) {
        request.setAttribute(userSessionKey, user);
    }

    public static void setYearList(HttpServletRequest request, List<String> years) {
        request.setAttribute(yearList, years);
    }
    
    public static void removeAll(HttpSession session){
        session.removeAttribute(adminSessionKey);
        session.removeAttribute(errorMessage);
        session.removeAttribute(formatList);
        session.removeAttribute(genreList);
        session.removeAttribute(movieList);
        session.removeAttribute(userSessionKey);
        session.removeAttribute(yearList);
    }

}
