<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@include file="topNav.jspf" %>
<script src="JavaScript/seriesInfoJS.js"></script>

<h3>Series-info</h3>
<form action="updateSeries" method="post">
    <fieldset class="styledFS" style='text-align: left; width: 40%; float:right;'>
        <legend>Genre</legend>
            Add / remove genre:
            <input type="button" class="button" value="+" id="plusButton"
                   onclick="addGenreDropbox();">
            <input type="button" class="button" value="-" id="minusButton"
                   onclick="removeGenreDropbox();">

            <p></p>

            <select id='genreSelect0' name='hidden' style="display: none;">
                <c:if test='${genreList != null}'>
                    <c:forEach var='genre' items='${genreList}'>
                        <option>${genre}</option>
                    </c:forEach>
                </c:if>
            </select>
            <div id='genreDropboxDiv1'></div>
            
            
    </fieldset>
    
    <fieldset class="styledFS" id="leftFields">
        <legend>Names</legend>
        <c:if test="${errorMessage != null}">
            <p id="errorMessage">${errorMessage}</p>
        </c:if>
        <c:if test="${successMessage != null}">
            <p id="successMessage">${successMessage}</p>
        </c:if>
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
    
    <fieldset class="styledFS" style="width: 75%; margin: 0 auto;">
        <legend>Seasons</legend>
        
        <select id='seasonSelect0' style='display: none;'>
            <option></option>
                <c:if test='${seasonDropboxValues != null}'>
                    <c:forEach var='seNum' items='${seasonDropboxValues}'>
                        <option>${seNum}</option>
                    </c:forEach>
                </c:if>
        </select>
        <select id='episodeSelect0' style='display: none;'>
            <option></option>
                <c:if test='${episodeDropboxValues != null}'>
                    <c:forEach var='epNum' items='${episodeDropboxValues}'>
                        <option>${epNum}</option>
                    </c:forEach>
                </c:if>
        </select>
        <select id='yearSelect0' style='display: none'>
            <c:if test='${yearList != null}'>
                <c:forEach var='year' items='${yearList}'>
                    <option>${year}</option>
                </c:forEach>
            </c:if>
        </select>
        
         Add season:
        <input type="button" class="button" value="+" id="plusButton"
               onclick="addSeasonFS();">
        <p></p>
        
        <div id="seasonfieldsDiv1"></div>
    </fieldset>
    
    <button type="submit" class="button" name="update_series" value="update_series" >
        Update Series</button>
</form>

<%@include file="bottom.jspf" %>
