<%-- 
    Document   : searchpage
    Created on : Mar 26, 2013, 10:57:36 AM
    Author     : Easysimulation
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<%@include file="topNav.jspf" %>
<script src="JavaScript/searchJS.js"></script>

<h3>Search content from database</h3>
<form action="searchAction" method="post">
    <fieldset class="styledFS">
        <legend>Search from</legend>
        <p class="pLeft">
            Movies
            <input type="radio" value="movie" id="customCB1" name="check1" checked/>
            <label class="customCheck" for="customCB1"></label>
        </p>
        <p class="pRight">
            Series
            <input type="radio" value="series" id="customCB2" name="check1"/>
            <label class="customCheck" for="customCB2"></label>
        </p>
    </fieldset>                


    <fieldset class="styledFS" style="line-height: 25px;">
        <legend>Search</legend>
        <input type="button" class="button" value="+" 
               style="padding: 0px 3px; margin: 0;"
               onclick="addSearchLine();">
        &emsp;&emsp;&emsp;
        <input type="button" class="button" value="-" 
               style="padding: 0px 5px; margin: 0;"
               onclick="removeSearchLine();">
        <br>

        <input type="text" value="Search #1" 
               onclick="if (this.value == 'Search #1') {
                           this.value = ''
                       }
                       ;"
               onblur="if (this.value == '') {
                           this.value = 'Search #1'
                       }
                       ;"
               class="styled-textfield" name="txt1"
               id="txt1" style="float: left; margin-left: 13%;"/>
        <div class="styled-select" style="float: right; margin-right: 7%">
            <select name="select1">
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
            <input type="radio" value="seen" id="seenRadio1" name="seenRadio"/>
            <label class="customCheck" for="seenRadio1"></label>
        </p>    

        not seen
        <input type="radio" value="notSeen" id="seenRadio2" name="seenRadio"/>
        <label class="customCheck" for="seenRadio2"></label>

        <p class="pRight" style="width: 35%; text-align: left;">
            both
            <input type="radio" value="both" id="seenRadio3" name="seenRadio" checked/>
            <label class="customCheck" for="seenRadio3"></label>
        </p>   
    </fieldset> 


    <input type="submit" class="button" value="Search">
</form>

<table id="searchResults">
    <tr>
        <c:if test="${adminSessionKey != null && adminSessionKey}">
            <th>Owner</th>
            </c:if>
        <th>Eng</th>
        <th>Fin</th>
        <th>Swe</th>
        <th>Other</th>
        <th>Seen</th>
    </tr>
    <c:choose>
        <c:when test="${empty movieList || movieList == null}">
        </table>                           
        <p>No hits!</p>
    </c:when>
    <c:otherwise>
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
                    </c:choose></td>
            </tr>
            <% indx = indx + 1;%>
        </c:forEach>
    </table>       
</c:otherwise>
</c:choose>


<%@include file="bottom.jspf" %>
