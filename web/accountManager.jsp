<%-- 
    Document   : accountManager
    Created on : Apr 27, 2013, 7:18:00 PM
    Author     : Roope
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<%@include file="topNav.jspf" %>
<script src="JavaScript/accountManagerJS.js"></script>

<h3>Manage Your account</h3>
<form action="accountManager" method="post">
    <fieldset class="styledFS" id="usernameFS">
        <legend>Update account-information</legend>
        <c:if test="${errorMessage != null}" >
            <p id="errorMessage">${errorMessage}</p>
        </c:if>
        <c:if test="${successMessage != null}" >
            <p id="successMessage">${successMessage}</p>
        </c:if> 
        Username  <br><input type="text" class="styled-textfield usernameFields" name='username' id='username'
                             value="${userInSession.username}">
        <p></p>
        Password  <br><input type="password" class="styled-textfield usernameFields" name='password'
                                onload="passwordsMatch();" oninput="passwordsMatch();" id='password'>
        <p></p>
        New password  <br><input type="password" class="styled-textfield usernameFields" name='new_password'
                                oninput="passwordsMatch();" id='new_password'>
        <p></p>
        Confirm new password  <br><input type="password" class="styled-textfield usernameFields" name='confirm_new_password' 
                                oninput="passwordsMatch();" id='confirm_new_password'>
        <p></p>
        First name <br><input type="text" class="styled-textfield usernameFields" name='first_name' id='first_name'
                              value="${userInSession.firstName}">
        <p></p>
        Last name <br><input type="text" class="styled-textfield usernameFields" name='last_name' id='last_name'
                             value="${userInSession.lastName}">
        <p></p> 
    </fieldset>

    <div id='password_error_wrapper'></div>

    <input type="submit" class="button" value="Update account-information" id='submit_button'>
</form>

<script>
    window.onload = passwordsMatch;
</script>

<%@include file="bottom.jspf" %>
