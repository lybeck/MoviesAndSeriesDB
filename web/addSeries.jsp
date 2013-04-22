<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<<%@include file="topNav.jspf" %>
<script src="JavaScript/addMovieJS.js"></script>

<h3>Add series to database</h3>
<form action="addSeries" method="post">
    <fieldset class="styledFS" style='text-align: left; width: 40%; float:right;'>
        <legend>Series info</legend>
        
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
            <div id='genreDropboxDiv1'></div>
            
            
    </fieldset>
    
    <fieldset class="styledFS" id="leftFields">
        <legend>Names</legend>
        <c:if test="${errorMessage != null}">
            <p id="errorMessage">${errorMessage}</p>
        </c:if>
        <c:if test="${successMessage != null}">
            <p id="successMessage">${successMessage}</p>
        </c:if>
        &emsp; Eng
        <br>
        <input type="text" class="styled-textfield" id="namefields"
               name="engName"/>
        <p></p>
        &emsp; Fin
        <br>
        <input type="text" class="styled-textfield" id="namefields"
               name="fiName"/>
        <p></p>
        &emsp; Swe
        <br>
        <input type="text" class="styled-textfield" id="namefields"
               name="sweName"/>
        <p></p>
        &emsp; Other
        <br>
        <input type="text" class="styled-textfield" id="namefields"
               name="otherName"/>
    </fieldset>
    <fieldset class="styledFS" id="leftFields">
            <legend>Media info</legend>
        Add / remove Media format:
        <input type="button" class="button" value="+" id="plusButton"
               onclick="addMediaFormatDropbox();">
        <input type="button" class="button" value="-" id="minusButton"
               onclick="removeMediaFormatDropbox();">

        <p></p>
        format #1
        <div class='styled-select' style='margin: 0 0 0 0'>
            <select id='formatSelect1' onchange='addAdditionalInfo(1);' name='mediaFormatDropbox1'>
                <c:if test='${formatList != null}'>
                    <c:forEach var='format' items='${formatList}'>
                        <option>${format}</option>
                    </c:forEach>
                </c:if>
            </select>
        </div>
        <div id="additionalInfoDiv1"></div>
        <p></p>
        <div id='mediaFormatDropboxDiv1'></div>
        </fieldset>

        <br>
        <input type="submit" class="button" value="Add movie" />
        <p></p>
    </div>

</form>

<%@include file="bottom.jspf" %>
