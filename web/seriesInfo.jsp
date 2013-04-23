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
            <c:if test="${series != null}">
                <% int indx = 1;%>
                <div id='genreDropboxDiv<%out.print(indx);%>'>
                <c:forEach var='selectedGenre' items='${series.genres}'>
                    Genre #<%out.print(indx);%>
                    <div class='styled-select' style='margin: 0 0 0 0'>
                        <select name='genreDropbox<%out.print(indx);%>' id='genreSelect<%out.print(indx);%>'>
                                <c:forEach var='genre' items='${genreList}'>
                                    <c:choose>
                                        <c:when test="${selectedGenre == genre}">
                                            <option selected="true">${genre}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option>${genre}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                        </select>
                    </div>
                    <p></p>
                    <% indx++;%>
                    <div id='genreDropboxDiv<%out.print(indx);%>'>
                </c:forEach>
            <%for(int i = 0; i<indx ; i++){%>
            </div>
            <%}%>
            </c:if>
            
            
    </fieldset>
    
    <fieldset class="styledFS" id="leftFields">
        <legend>Names</legend>
        <c:if test="${errorMessage != null}">
            <p id="errorMessage">${errorMessage}</p>
        </c:if>
        <c:if test="${successMessage != null}">
            <p id="successMessage">${successMessage}</p>
        </c:if>
        <br>
        <c:choose>
            <c:when test="${series.nameEng != null}">
                <input type="text" class="styled-textfield" id="namefields"
                       value="${series.nameEng}" name="engName"/>
            </c:when>
            <c:otherwise>
                <input type="text" class="styled-textfield" id="namefields"
                       name="engName"/>
            </c:otherwise>
        </c:choose>
        <p></p>
        &emsp; Fin
        <br>
        <c:choose>
            <c:when test="${series.nameFi != null}">
                <input type="text" class="styled-textfield" id="namefields"
                       value="${series.nameFi}" name="fiName"/>
            </c:when>
            <c:otherwise>
                <input type="text" class="styled-textfield" id="namefields"
                       name="fiName"/>
            </c:otherwise>
        </c:choose>
        <p></p>
        &emsp; Swe
        <br>
        <c:choose>
            <c:when test="${series.nameSwe != null}">
                <input type="text" class="styled-textfield" id="namefields"
                       value="${series.nameSwe}" name="sweName"/>
            </c:when>
            <c:otherwise>
                <input type="text" class="styled-textfield" id="namefields"
                       name="sweName"/>
            </c:otherwise>
        </c:choose>
        <p></p>
        &emsp; Other
        <br>
        <c:choose>
            <c:when test="${series.nameOther != null}">
                <input type="text" class="styled-textfield" id="namefields"
                       value="${series.nameOther}" name="otherName"/>
            </c:when>
            <c:otherwise>
                <input type="text" class="styled-textfield" id="namefields"
                       name="otherName"/>
            </c:otherwise>
        </c:choose>
    </fieldset>
        
    <div style="width: 100%; overflow: hidden;">
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
        <input name='number_of_season_divs' id='number_of_season_divs' 
               style="display: none;" value="0">
        
         Add season:
        <input type="button" class="button" value="+" id="plusButton"
               onclick="addSeasonFS();">
        <p></p>
        
        <div id="seasonfieldsHolder"></div>
        </div>
    
    <button type="submit" class="button" name="update_series" value="update_series" >
        Update Series</button>
</form>

<%@include file="bottom.jspf" %>
