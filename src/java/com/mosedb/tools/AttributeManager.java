package com.mosedb.tools;

import com.mosedb.models.Movie;
import com.mosedb.models.Series;
import com.mosedb.models.User;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Roope
 */
public class AttributeManager {
    
    private static String adminSessionKey = "adminSessionKey";
    private static String episodeDropboxValues = "episodeDropboxValues";
    private static String errorMessage = "errorMessage";
    private static String formatList = "formatList";
    private static String genreList = "genreList";
    private static String movie = "movie";
    private static String movieList = "movieList";
    private static String seasonDropboxValues = "seasonDropboxValues";
    private static String series= "series";
    private static String seriesList = "seriesList";
    private static String successMessage = "successMessage";
    private static String userList = "userList";
    private static String userSessionKey = "userSessionKey";
    private static String yearList = "yearList";

    public static boolean getAdminSessionKey(HttpSession session) {
        return (Boolean) session.getAttribute(adminSessionKey);
    }
    
    public static List<Integer> getEpisodeDropbox(HttpSession session){
        return (List<Integer>) session.getAttribute(episodeDropboxValues);
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
        
    public static Movie getMovie(HttpSession session) {
        return (Movie) session.getAttribute(movie);
    }
    
    public static List<Movie> getMovieList(HttpSession session) {
        return (List<Movie>) session.getAttribute(movieList);
    }
    
    public static List<Integer> getSeasonDropbox(HttpSession session){
        return (List<Integer>) session.getAttribute(seasonDropboxValues);
    }
    
    public static Series getSeries(HttpSession session){
        return (Series) session.getAttribute(series);
    }
    
    public static List<User> getUserList(HttpSession session) {
        return (List<User>) session.getAttribute(userList);
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
    
    public static void setEpisodeDropbox(HttpSession session, List<Integer> episodes ){
        session.setAttribute(episodeDropboxValues, episodes);
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
    
    public static void setMovie(HttpSession session, Movie mov) {
        session.setAttribute(movie, mov);
    }
    
    public static void setMovieList(HttpSession session, List<Movie> movies) {
        session.setAttribute(movieList, movies);
    }
    
    public static void setSeasonDropbox(HttpSession session, List<String> seasons){
        session.setAttribute(seasonDropboxValues, seasons);
    }
    
    public static void setSeries(HttpSession session, Series serie){
        session.setAttribute(series, serie);
    }
    
    public static void setUserList(HttpSession session, List<User> users) {
        session.setAttribute(userList, users);
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
    
    public static void setEpisodeDropbox(HttpServletRequest request, List<Integer> episodes ){
        request.setAttribute(episodeDropboxValues, episodes);
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
    
    public static void setMovie(HttpServletRequest request, Movie mov) {
        request.setAttribute(movie, mov);
    }
    
    public static void setMovieList(HttpServletRequest request, List<Movie> movies) {
        request.setAttribute(movieList, movies);
    }
    
    public static void setSeasonDropbox(HttpServletRequest request, List<Integer> episodes ){
        request.setAttribute(seasonDropboxValues, episodes);
    }
    
    public static void setSeries(HttpServletRequest request, Series serie){
        request.setAttribute(series, serie);
    }

    public static void setSeriesList(HttpServletRequest request, List<Series> series) {
        request.setAttribute(seriesList, series);
    }
    
    public static void setUserList(HttpServletRequest request, List<User> users) {
        request.setAttribute(userList, users);
    }

    public static void setUserSessionKey(HttpServletRequest request, User user) {
        request.setAttribute(userSessionKey, user);
    }

    public static void setYearList(HttpServletRequest request, List<String> years) {
        request.setAttribute(yearList, years);
    }

    public static void setSuccessMessage(HttpServletRequest request, String message) {
        request.setAttribute(successMessage, message);
    }
    
    public static void removeErrorMessage(HttpServletRequest request) {
        request.removeAttribute(errorMessage);
    }
    
    public static void removeSuccessMessage(HttpServletRequest request) {
        request.removeAttribute(successMessage);
    }
    
    public static void removeAll(HttpSession session){
        session.removeAttribute(adminSessionKey);
        session.removeAttribute(episodeDropboxValues);
        session.removeAttribute(errorMessage);
        session.removeAttribute(formatList);
        session.removeAttribute(genreList);
        session.removeAttribute(movie);
        session.removeAttribute(movieList);
        session.removeAttribute(seasonDropboxValues);
        session.removeAttribute(successMessage);
        session.removeAttribute(userList);
        session.removeAttribute(userSessionKey);
        session.removeAttribute(yearList);
    }
    
    public static void removeMovie(HttpSession session){
        session.removeAttribute(movie);
    }

    public static void removeSeries(HttpSession session) {
        session.removeAttribute(series);
    }

}
