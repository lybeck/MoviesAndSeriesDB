<%-- 
    Document   : searchpage
    Created on : Mar 26, 2013, 10:57:36 AM
    Author     : Easysimulation
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@include file="topNav.jspf" %>
                
                <h3>Search content from database</h3>

                <fieldset class="searchFS">
                    <legend>Search from</legend>
                    <p class="pLeft">
                        Movies
                        <input type="checkbox" value="None" id="customCB1" name="check1" checked/>
                        <label class="customCheck" for="customCB1"></label>
                    </p>
                    <p class="pRight">
                        Series
                        <input type="checkbox" value="None" id="customCB2" name="check1" checked/>
                        <label class="customCheck" for="customCB2"></label>
                    </p>
                </fieldset>                
                

                <fieldset class="searchFS" style="line-height: 25px;">
                    <legend>Search</legend>
                    
                    <input type="text" value="Search #1" 
                           onclick="if(this.value == 'Search #1'){this.value=''};"
                           onblur="if(this.value == ''){this.value = 'Search #1'};"
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
                    <input type="text" value="Search #2" 
                           onclick="if(this.value == 'Search #2'){this.value=''};"
                           onblur="if(this.value == ''){this.value = 'Search #2'};"
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
                    <input type="text" value="Search #3" 
                           onclick="if(this.value == 'Search #3'){this.value=''};"
                           onblur="if(this.value == ''){this.value = 'Search #3'};"
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
                    <br>
                    <br>
                    <p class="pLeft" style="width: 22%; text-align: right;">
                        seen
                        <input type="radio" value="None" id="seenRadio1" name="seenRadio"/>
                        <label class="customCheck" for="seenRadio1"></label>
                    </p>    
                    
                    not seen
                    <input type="radio" value="None" id="seenRadio2" name="seenRadio"/>
                    <label class="customCheck" for="seenRadio2"></label>
                    
                    <p class="pRight" style="width: 29%; text-align: left;">
                        both
                        <input type="radio" value="None" id="seenRadio3" name="seenRadio" checked/>
                        <label class="customCheck" for="seenRadio3"></label>
                    </p>   
                    
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
