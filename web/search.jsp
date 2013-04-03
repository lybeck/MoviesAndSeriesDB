<%-- 
    Document   : searchpage
    Created on : Mar 26, 2013, 10:57:36 AM
    Author     : Easysimulation
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@include file="topNav.jspf" %>

                <fieldset class="searchFS">
                    <legend>Search from</legend>
                    <p id="pLeft">
                        Movies
                        <input type="checkbox" value="None" id="customCB1" name="check1"/>
                        <label class="checkbox" for="customCB1"></label>
                    </p>
                    <p id="pRight">
                        Series
                        <input type="checkbox" value="None" id="customCB2" name="check2"/>
                        <label class="checkbox" for="customCB2"></label>
                    </p>
                </fieldset>                
                
                <fieldset class="searchFS" style="line-height: 25px;">
                    <legend>Search</legend>
                    
                    <input type="text" value="Search #1" class="styled-textfield" name="txt1"
                           id="txt1" style="float: left; margin-left: 10%;"/>
                    <div class="styled-select" style="float: right; margin-right: 10%">
                        <select name="select1">
                            <option>[Choose searchword]</option>
                            <option>Name</option>
                            <option>Genre</option>
                            <option>Mediaformat</option>
                            <option>Year</option>
                            <option>Resolution</option>
                        </select>   
                    </div>
                    <br>
                    <br>
                    <input type="text" value="Search #2" class="styled-textfield" name="txt2"
                           id="txt1" style="float: left; margin-left: 10%;"/>
                    <div class="styled-select" style="float: right; margin-right: 10%">
                        <select name="select2">
                            <option>[Choose searchword]</option>
                            <option>Name</option>
                            <option>Genre</option>
                            <option>Mediaformat</option>
                            <option>Year</option>
                            <option>Resolution</option>
                        </select>   
                    </div>
                    <br>
                    <br>
                    <input type="text" value="Search #3" class="styled-textfield" name="txt3"
                           id="txt1" style="float: left; margin-left: 10%;"/>
                    <div class="styled-select" style="float: right; margin-right: 10%">
                        <select name="select3">
                            <option>[Choose searchword]</option>
                            <option>Name</option>
                            <option>Genre</option>
                            <option>Mediaformat</option>
                            <option>Year</option>
                            <option>Resolution</option>
                        </select>   
                    </div>
                </fieldset>   

<%@include file="bottom.jspf" %>
