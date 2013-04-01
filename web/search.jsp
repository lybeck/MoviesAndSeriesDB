<%-- 
    Document   : searchpage
    Created on : Mar 26, 2013, 10:57:36 AM
    Author     : Easysimulation
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@include file="topNav.jspf" %>

                <fieldset id="searchFS">
                    <legend>Search from</legend>
                    <p id="pLeft">
                        Movies
                        <input type="checkbox" value="None" id="customCB1" name="check"/>
                        <label class="checkbox" for="customCB1"></label>
                    </p>
                    <p id="pRight">
                        Series
                        <input type="checkbox" value="None" id="customCB2" name="check"/>
                        <label class="checkbox" for="customCB2"></label>
                    </p>
                </fieldset>

<%@include file="bottom.jspf" %>
