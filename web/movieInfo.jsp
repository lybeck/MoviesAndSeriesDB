<%-- 
    Document   : movieInfo
    Created on : Apr 16, 2013, 3:58:21 PM
    Author     : Easysimulation
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<%@include file="topNav.jspf" %>
<script src="JavaScript/addMovieJS.js"></script>

<h3>Edit movie-info</h3>
<form action="updateMovieInfo" method="post">
    <fieldset class="styledFS" style='text-align: left; width: 40%; float:right;'>
        <legend>Movie info</legend>

        <div style="position: relative; right: -150px; z-index: 2; top: 25px;">
            seen
            <input type="checkbox" id="customSeen" name="seenCheckbox" checked>
            <label class="customCheck" for="customSeen"></label>
        </div>

        <div style="position: relative; top:-18px;">
            Year:
            <div class="styled-select" id="yearBar">
                <select name='yearDropbox'>
                    <c:if test='${yearList != null}'>
                        <c:forEach var='year' items='${yearList}'>
                            <option>${year}</option>
                        </c:forEach>
                    </c:if>
                </select>
            </div>
            <p></p>

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
            <c:if test="${movie != null}">
                <% int indx = 1;%>
                <c:forEach var='selectedGenre' items='${movie.genres}'>
                    <div class='styled-select' style='margin: 0 0 0 0'></div>
                        <select name='genreDropbox<%out.println(indx);%>' id='genreSelect<%out.println(indx);%>'>
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
                </c:forEach>
            <div id='genreDropboxDiv<%out.println(indx);%>'></div>
            </c:if>

    </fieldset>

    <fieldset class="styledFS" id="leftFields">
        <legend>Names</legend>
        <c:if test="${errorMessage != null}">
            <p id="errorMessage">${errorMessage}</p>
        </c:if>
        &emsp; Eng
        <br>
        <c:choose>
            <c:when test="${movie.nameEng != null}">
                <input type="text" class="styled-textfield" id="namefields"
                       value="${movie.nameEng}" name="engName"/>
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
            <c:when test="${movie.nameFi != null}">
                <input type="text" class="styled-textfield" id="namefields"
                       value="${movie.nameFi}" name="fiName"/>
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
            <c:when test="${movie.nameSwe != null}">
                <input type="text" class="styled-textfield" id="namefields"
                       value="${movie.nameSwe}" name="sweName"/>
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
            <c:when test="${movie.nameOther != null}">
                <input type="text" class="styled-textfield" id="namefields"
                       value="${movie.nameOther}" name="otherName"/>
            </c:when>
            <c:otherwise>
                <input type="text" class="styled-textfield" id="namefields"
                       name="otherName"/>
            </c:otherwise>
        </c:choose>
    </fieldset>
    <fieldset class="styledFS" id="leftFields">
        <legend>Media info</legend>
        Add / remove Media format:
        <input type="button" class="button" value="+" id="plusButton"
               onclick="addMediaFormatDropbox();">
        <input type="button" class="button" value="-" id="minusButton"
               onclick="removeMediaFormatDropbox();">

        <p></p>
        
        <c:if test="${movie != null}">
            <% int indx2 = 1;%>
            <c:forEach var='selectedFormat' items='${movie.formats}'>
                <% if (indx2 != 1) {%>
                <div id='mediaFormatDropboxDiv1'>
                <%} else {%>
                <%}%>
                format #<%out.println(indx2);%>
                <div class='styled-select' style='margin: 0 0 0 0'>
                    <select name='mediaFormatDropbox<%out.println(indx2);%>' id='formatSelect<%out.println(indx2);%>'
                        onclick='addAdditionalInfo(<%out.println(indx2);%>)'>
                    <c:forEach var='format' items='${formatList}'>
                        <c:choose>
                            <c:when test="${selectedFormat == format}">
                                <option selected="true">${format}</option>
                            </c:when>
                            <c:otherwise>
                                <option>${format}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach> 
                    </select>
                </div>
                <div id="additionalInfoDiv<%out.println(indx2);%>">
                <c:if test="${selectedFormat == 'dc'}">
                    <br>File-type : 
                    <input type='text' class='styled-textfield'
                    id='formatFields' name='fileType<%out.println(indx2);%>'
                    <c:if test="${selectedFormat.fileType != null}">
                    value='${selectedFormat.fileType}'
                    </c:if>
                    >

                    &emsp;Width : 
                    <input type='text' class='styled-textfield'
                    id='formatFields' name='resox<%out.println(indx2);%>'
                    <c:if test="${selectedFormat.resoX != null}">
                    value='${selectedFormat.resoX}'
                    </c:if>
                    >

                    &emsp;Height : 
                    <input type='text' class='styled-textfield'
                    id='formatFields' name='resoy<%out.println(indx2);%>'
                    <c:if test="${selectedFormat.resoY != null}">
                    value='${selectedFormat.resoY}'
                    </c:if>
                    >
                </c:if>
                </div>
                <p></p>
                <% if (indx2 != 1) {%>
                </div>
                <%} else {%>
                <%}%>
                <% indx2++;%>
            </c:forEach>
        </c:if>
    </fieldset>

    <br>
    <input type="submit" class="button" value="Update changes" />
    <p></p>
</form>

<%@include file="bottom.jspf" %>