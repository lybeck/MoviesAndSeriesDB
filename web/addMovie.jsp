<%-- 
    Document   : addMovie
    Created on : Apr 9, 2013, 2:26:48 PM
    Author     : Easysimulation
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<%@include file="topNav.jspf" %>
<script src="JavaScript/addMovieJS.js"></script>

<h3>Add movie to database</h3>
<form action="addMovieAction" method="post">
    <fieldset class="styledFS" id="leftFields">
        <legend>Names</legend>
        &emsp; Eng
        <br>
        <input type="text" class="styled-textfield" id="namefields"
               name="engName"/>
        <p></p>
        &emsp; Fin
        <br>
        <input type="text" class="styled-textfield" id="namefields"
               name="fiName"/>
        <p></p>
        &emsp; Swe
        <br>
        <input type="text" class="styled-textfield" id="namefields"
               name="sweName"/>
        <p></p>
        &emsp; Other
        <br>
        <input type="text" class="styled-textfield" id="namefields"
               name="otherName"/>
    </fieldset>
    <fieldset class="styledFS" id="leftFields">
        <legend>Movie info</legend>
        Add / remove genre:
        <input type="button" class="button" value="+" id="plusButton"
               onclick="addGenreDropbox();">
        <input type="button" class="button" value="-" id="minusButton"
               onclick="removeGenreDropbox();">
        &emsp;
        year:
        <input type="text" class="styled-textfield" name="year"
               style="width: 70px;">
        <p></p>
        
        <select id='select0' name='hidden' style="display: none;">
                    <c:if test='${genreList != null}'>
                        <c:forEach var='genre' items='${genreList}'>
                            <option>${genre}</option>
                        </c:forEach>
                    </c:if>
        </select>
        <div id='genreDropboxDiv1'></div>
        </fieldset>
    </form>

    <%@include file="bottom.jspf" %>
