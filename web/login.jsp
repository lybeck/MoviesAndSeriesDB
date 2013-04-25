<%-- 
    Document   : login
    Created on : Mar 24, 2013, 12:49:46 PM
    Author     : Roope
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="style/loginStyle.css" />
        <title>MoSeDB|Login</title>
    </head>
    <body>

        <img id='loginPic' src="style/mosedb_gradAndnameGrad.png" title="moseDB" alt='moseDB logo' />

        <form action="login" method="POST">

            <div id="center">

                USERNAME 
                <br>
                <input class="loginBoxes" value="" type="text" name="username"/> 
                <br>
                <br>
                <br>
                PASSWORD
                <br>
                <input class="loginBoxes" value="" type="password" name="password"/> 

            </div>

            <input type=submit class="loginButton" value="Login"/>

        </form>

        <c:choose>
            <c:when test="${errorMessage != null}">
                <p id="loginerror">${errorMessage}</p>
            </c:when>
            <c:otherwise>
                <p style="visibility: hidden">This cannot be seen.</p>
            </c:otherwise>
        </c:choose>

        <footer>Copyright © Lybeck and Sirviö 2013</footer>

    </body>
</html>
