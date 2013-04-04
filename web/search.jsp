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
                    
                    <input type="text" value="Search #1" onclick="this.value='';"
                           onblur="this.value=!this.value?'Search #1':this.value;"
                           class="styled-textfield" name="txt1"
                           id="txt1" style="float: left; margin-left: 13%;"/>
                    <div class="styled-select" style="float: right; margin-right: 7%">
                        <select name="select1">
                            <option>[Choose searchword]</option>
                            <option>Name</option>
                            <option>Genre</option>
                            <option>Media format</option>
                            <option>Year</option>
                        </select>   
                    </div>
                    <br>
                    <br>
                    <input type="text" value="Search #2" onclick="this.value='';"
                           onblur="this.value=!this.value?'Search #2':this.value;"
                           class="styled-textfield" name="txt2"
                           id="txt1" style="float: left; margin-left: 13%;"/>
                    <div class="styled-select" style="float: right; margin-right: 7%">
                        <select name="select2">
                            <option>[Choose searchword]</option>
                            <option>Name</option>
                            <option>Genre</option>
                            <option>Media format</option>
                            <option>Year</option>
                        </select>   
                    </div>
                    <br>
                    <br>
                    <input type="text" value="Search #3" onclick="this.value='';"
                           onblur="this.value=!this.value?'Search #3':this.value;"
                           class="styled-textfield" name="txt3"
                           id="txt1" style="float: left; margin-left: 13%;"/>
                    <div class="styled-select" style="float: right; margin-right: 7%">
                        <select name="select3">
                            <option>[Choose searchword]</option>
                            <option>Name</option>
                            <option>Genre</option>
                            <option>Media format</option>
                            <option>Year</option>
                        </select>   
                    </div>
                </fieldset> 


                <input type="submit" class="button" value="Search">
                
                <table id="searchResults">
                    <tr>
                        <th>Name</th>
                        <th>Movie/Series</th>
                        <th>Year</th>
                        <th>Media format</th>
                        <th>Seen</th>
                    </tr>
                    <tr >
                        <td>#1 example</td>
                        <td>Movie</td>
                        <td>1969</td>
                        <td>VHS</td>
                        <td>yes</td>
                    </tr>
                    <tr class="red">
                        <td>#2 example</td>
                        <td>Series</td>
                        <td>2000</td>
                        <td>DVD</td>
                        <td>yes</td>
                    </tr>
                    <tr >
                        <td>#3 example</td>
                        <td>Movie</td>
                        <td>1999</td>
                        <td>DC</td>
                        <td>no</td>
                    </tr>
                </table>

<%@include file="bottom.jspf" %>
