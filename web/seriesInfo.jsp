<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@include file="topNav.jspf" %>
<script src="JavaScript/seriesInfoJS.js"></script>

<h3>Series-info</h3>
<form action="updateSeries" method="post">
    <c:if test="${errorMessage != null}">
        <p id="errorMessage">${errorMessage}</p>
    </c:if>
    <c:if test="${successMessage != null}">
        <p id="successMessage">${successMessage}</p>
    </c:if>
    <c:if test="${series == null}">
        <p id="errorMessage">Failed to load series from database!</p>
    </c:if>

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
                    <%for (int i = 0; i < indx; i++) {%>
                </div>
                <%}%>
            </c:if>


    </fieldset>

    <fieldset class="styledFS" id="leftFields">
        <legend>Names</legend
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
    <br>

    <c:if test="${series != null && !empty series.episodes}">
        <table class="customTable">
            <tr>
                <th>Season</th>
                <th>Episode</th>
                <th>Title</th>
                <th>Year</th>
                <th>Seen</th>
            </tr>
            <% int indx2 = 1;%>
            <c:forEach var="ep" items="${series.episodes}">
                <% if (indx2 % 2 == 1) {%>
                <tr>
                    <%} else {%>
                <tr class="red">
                    <%}%>

                    <td>${ep.seasonNumber}</td>
                    <td>${ep.episodeNumber}</td>
                    <td>
                        <input class="styled-textfield small" 
                               <c:choose>
                                   <c:when test="${ep.episodeName != null}">
                                       value="${ep.episodeName}"
                                   </c:when>
                                   <c:otherwise>
                                       value=""
                                   </c:otherwise>
                               </c:choose>
                               name="episode_name_${ep.seriesId}_${ep.seasonNumber}_${ep.episodeNumber}">
                    </td>
                    <td>
                        <div class="styled-select tableBox">
                            <select name='episode_year_${ep.seriesId}_${ep.seasonNumber}_${ep.episodeNumber}'>
                                <c:if test='${yearList != null}'>
                                    <c:forEach var='year' items='${yearList}'>
                                        <c:choose>
                                            <c:when test="${ep.episodeYear == year}">
                                                <option selected="true">${year}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option>${year}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </c:if>
                            </select>
                        </div>
                    </td>
                    <td class="padded">
                        <input type="checkbox" id="seen_${ep.seriesId}_${ep.seasonNumber}_${ep.episodeNumber}"
                               name="episode_seen_${ep.seriesId}_${ep.seasonNumber}_${ep.episodeNumber}"
                               <c:if test="${ep.seen}">
                                   checked
                               </c:if>
                               >
                        <label class="customCheck" 
                               for="seen_${ep.seriesId}_${ep.seasonNumber}_${ep.episodeNumber}"></label>
                    </td>
                    <%indx2++;%>
                </c:forEach>
        </table>
    </c:if>

    <div style="width: 100%; overflow: hidden;">
        <select id='seasonSelect0' style='display: none;'>
            <c:if test='${seasonDropboxValues != null}'>
                <c:forEach var='seNum' items='${seasonDropboxValues}'>
                    <option>${seNum}</option>
                </c:forEach>
            </c:if>
        </select>
        <select id='episodeSelect0' style='display: none;'>
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
        <select name='hidden_delete_season_select' id='hidden_delete_season_select' style="display: none;">
            <c:if test="${series != null}">
                <c:forEach var="season" items="${series.seasonNumbersAsList}">
                    <option>${season}</option>
                </c:forEach>
            </c:if>
        </select>
        <div id='hiddenDeleteSeasonFS' style='display: none;'>
            <div style='margin: 0 0 0 0; width:100%; text-align: right;'>
                <button onclick='removeDeleteSeasonFS();' class='button small'>Hide</button>
            </div>
            </fieldset>
        </div>

        <div id="plusButtonHolder">
            <input type='button' class='button small' value='Add season' id='addSeasonButton'
                   onclick='addSeasonFS();'>
            <p></p>
        </div>
        <div id="deleteSeasonButtonHolder">
            <input type='button' class='button small' value='Delete season' id='deleteSeasonButton'
                   onclick='deleteSeasonFS();'
                   <c:if test="${series.seasonNumbersAsList == null || empty series.seasonNumbersAsList}">
                       style="display: none;"   
                   </c:if>
                   >
            <br>
        </div>
        <div id="seasonfieldsHolder"></div>
        <div id="deleteSeasonfieldsHolder"></div>
    </div>
    <br>
    <button type="submit" class="button" name="submit" value="update_series" id="update_series"
            <c:if test="${series == null}">
                disabled
            </c:if>
            >
        Update Series</button>
    <button type="submit" class="button" name="submit" value="delete_series" 
            <c:if test="${series == null}">
                disabled
            </c:if>
            onclick="return confirm('This action will delete the series permanently. Are you sure you want to proceed?')">
        Delete series</button>
</form>

<%@include file="bottom.jspf" %>
