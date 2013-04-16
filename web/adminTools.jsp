<%-- 
    Document   : adminTools
    Created on : Apr 15, 2013, 8:30:46 PM
    Author     : Easysimulation
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<%@include file="topNav.jspf" %>
<%--  <script src="JavaScript/addMovieJS.js"></script> --%>

<h3>Manage users</h3>
<form action="addUser" method="post">
    <fieldset class="styledFS" id="usernameFS">
        <legend>Add user</legend>
        username : <input type="text" class="styled-textfield" id='usernameFields' name='username'>
        <p></p>
        password : <input type="password" class="styled-textfield" id='usernameFields' name='password'>
        <p></p>
        first name : <input type="text" class="styled-textfield" id='usernameFields' name='firstName'>
        <p></p>
        last name : <input type="text" class="styled-textfield" id='usernameFields' name='lastName'>
        <p></p> 
        admin  
        <input type="checkbox" name='adminBox' id="adminBox">
        <label class="customCheck" for="adminBox" style="margin: 19px 0 0 3px;"></label> &emsp;&emsp;&emsp;      
        <input type="submit" class="button" value="Add user">
    </fieldset>
</form>

<p></p>
   
<form action="deleteUser" method="post">
    <table class="customTable">
        <tr>
            <th>username</th>
            <th>first name</th>
            <th>last name</th>
            <th>admin</th>
            <th>select</th>
        </tr>
        <c:choose>
            <c:when test="${empty userList || userList == null}">
                </table>                           
                <p>No users!</p>
            </c:when>
            <c:otherwise>
                <% int indx = 1;%>
                <c:forEach var="user" items="${userList}">
                    <% if (indx % 2 == 1) {%>
                    <tr>
                        <%} else {%>
                    <tr class="red">
                        <%}%>
                        <td>${user.username}</td>
                        <td>${user.firstName}</td>
                        <td>${user.lastName}</td>
                        <td>
                            <c:choose>
                                <c:when test="${user.admin}" >
                                    yes
                                </c:when>
                                <c:otherwise>
                                    no 
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td class="padded">
                            <c:if test="${user.username != userSessionKey.username}">
                                <input type="checkbox" name='deleteSelect' value="${user.username}" id="${user.username}">
                                <label class="customCheck" for="${user.username}"></label>
                            </c:if>
                        </td>
                    </tr>
                    <% indx = indx + 1;%>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </table>
    <input type="submit" class="button" value="delete selected users">
</form>
    
<%@include file="bottom.jspf" %>
