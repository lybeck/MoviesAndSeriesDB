<%-- 
    Document   : searchpage
    Created on : Mar 26, 2013, 10:57:36 AM
    Author     : Easysimulation
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<%@include file="topNav.jspf" %>

<h3>Search content from database</h3>
<form action="searchAction" method="post">
    <fieldset class="styledFS">
        <legend>Search from</legend>
        <p class="pLeft">
            Movies
            <input type="radio" value="movie" id="customCB1" name="movie_series_radio" checked/>
            <label class="customCheck" for="customCB1"></label>
        </p>
        <p class="pRight">
            Series
            <input type="radio" value="series" id="customCB2" name="movie_series_radio"/>
            <label class="customCheck" for="customCB2"></label>
        </p>
    </fieldset>                


    <fieldset class="styledFS" style="line-height: 25px;">
        
        <legend>Search</legend>
        <p></p>
        <input type="text" value="Search" 
               onclick="if (this.value == 'Search') {
                           this.value = ''
                       }
                       ;"
               onblur="if (this.value == '') {
                           this.value = 'Search'
                       }
                       ;"
               class="styled-textfield" name="search_field"
               id="txt1" style="float: left; margin-left: 13%;"/>
        <div class="styled-select" style="float: right; margin-right: 7%">
            <select name="drop_box">
                <option>Name</option>
                <option>Genre</option>
                <option>Media format</option>
            </select>   
        </div>
        <br>
        <br>
        <div id="txtDiv1"></div>
        <p class="pLeft" style="width: 27%; text-align: right;">
            seen
            <input type="radio" value="seen" id="seenRadio1" name="seen_radio"/>
            <label class="customCheck" for="seenRadio1"></label>
        </p>    

        not seen
        <input type="radio" value="not_seen" id="seenRadio2" name="seen_radio"/>
        <label class="customCheck" for="seenRadio2"></label>

        <p class="pRight" style="width: 35%; text-align: left;">
            both
            <input type="radio" value="both" id="seenRadio3" name="seen_radio" checked/>
            <label class="customCheck" for="seenRadio3"></label>
        </p>   
    </fieldset> 


    <input type="submit" class="button" value="Search">
</form>

<form action="movieInfo" method="post">
    <c:if test="${movieList != null}">
        <c:choose>
            <c:when test="${empty movieList}">                          
            <p>No hits!</p>
        </c:when>
        <c:otherwise>
            <table class="customTable">
                <tr>
                    <c:if test="${adminSessionKey != null && adminSessionKey}">
                        <th>Owner</th>
                        </c:if>
                    <th>Eng</th>
                    <th>Fin</th>
                    <th>Swe</th>
                    <th>Other</th>
                    <th>Seen</th>
                    <th>View</th>
                </tr>
            <% int indx = 1;%>
            <c:forEach var="movie" items="${movieList}">
                <% if (indx % 2 == 1) {%>
                <tr>
                    <%} else {%>
                <tr class="red">
                    <%}%>
                    <c:if test="${adminSessionKey != null && adminSessionKey}">
                        <td>
                            ${movie.owner}
                        </td>
                    </c:if>
                    <td>
                        <c:if test="${movie.nameEng != null}" >
                            ${movie.nameEng} 
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${movie.nameFi != null}" >
                            ${movie.nameFi} 
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${movie.nameSwe != null}" >
                            ${movie.nameSwe} 
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${movie.nameOther != null}" >
                            ${movie.nameOther} 
                        </c:if>
                    </td>
                    <td><c:choose>
                            <c:when test="${movie.seen}" >
                                yes
                            </c:when>
                            <c:otherwise>
                                no 
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <button type="submit" class="button small" value="${movie.id}" 
                               name='Edit'>View</button>
                    </td>
                </tr>
                <% indx = indx + 1;%>
            </c:forEach>
        </c:otherwise>
        </c:choose>
        </table>       
    </c:if>
    <c:if test="${seriesList != null}">
        <c:choose>
            <c:when test="${empty seriesList}">                          
            <p>No hits!</p>
        </c:when>
        <c:otherwise>
            <table class="customTable">
                <tr>
                    <c:if test="${adminSessionKey != null && adminSessionKey}">
                        <th>Owner</th>
                        </c:if>
                    <th>Eng</th>
                    <th>Fin</th>
                    <th>Swe</th>
                    <th>Other</th>
                    <th>View</th>
                </tr>
            <% int indx2 = 1;%>
            <c:forEach var="series" items="${seriesList}">
                <% if (indx2 % 2 == 1) {%>
                <tr>
                    <%} else {%>
                <tr class="red">
                    <%}%>
                    <c:if test="${adminSessionKey != null && adminSessionKey}">
                        <td>
                            ${series.owner}
                        </td>
                    </c:if>
                    <td>
                        <c:if test="${series.nameEng != null}" >
                            ${series.nameEng} 
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${series.nameFi != null}" >
                            ${series.nameFi} 
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${series.nameSwe != null}" >
                            ${series.nameSwe} 
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${series.nameOther != null}" >
                            ${series.nameOther} 
                        </c:if>
                    </td>
                    <td>
                        <button type="submit" class="button small" value="${series.id}" 
                               name='Edit'>View</button>
                    </td>
                </tr>
                <% indx2 = indx2 + 1;%>
            </c:forEach>
        </c:otherwise>
        </c:choose>
        </table>       
    </c:if>
</form>

<%@include file="bottom.jspf" %>
