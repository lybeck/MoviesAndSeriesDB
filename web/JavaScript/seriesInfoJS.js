function addGenreDropbox() {
    var selects = document.getElementsByTagName('select');
    var genreBoxes = 0;
    for (var i = 0; i < selects.length; i++) {
        if (selects[i].name.substring(0, 12) == "genreDropbox") {
            genreBoxes = genreBoxes + 1;
        }
    }

    var boxDiv = "<div class='styled-select' style='margin: 0 0 0 0'>";
    var selectDiv = "<select name='genreDropbox" + (genreBoxes + 1) + "' id='genreSelect" + (genreBoxes + 1) + "'>";
    var loops = document.getElementById("genreSelect0").innerHTML;
    var nextDiv = "</select></div><p></p><div id='genreDropboxDiv" + (genreBoxes + 2) +
            "'></div> ";
    var code = "Genre #" + (genreBoxes + 1) + boxDiv + selectDiv + loops + nextDiv;

    document.getElementById("genreDropboxDiv" + (genreBoxes + 1)).innerHTML = code;
}

function removeGenreDropbox() {
    var selects = document.getElementsByTagName('select');
    var genreBoxes = 0;
    for (var i = 0; i < selects.length; i++) {
        if (selects[i].name.substring(0, 12) == "genreDropbox") {
            genreBoxes = genreBoxes + 1;
        }
    }
    if (genreBoxes == 0) {
        return;
    }

    document.getElementById("genreDropboxDiv" + genreBoxes).innerHTML = '';
}

function addSeasonFS() {
    var sFDivs = document.getElementsByTagName('div');
    var seasonfields = 0;
    for (var i = 0; i < sFDivs.length; i++) {
        if (sFDivs[i].id.substring(0, 15) == "seasonfieldsDiv") {
            seasonfields++;
        }
    }
    seasonfields++;
    
    var sFieldsDiv = "<div id=seasonfieldsDiv" + seasonfields + ">";
    var fieldset = "<fieldset class='styledFS' name='seasonfields" + seasonfields +
            "' style='width: 60%; margin: 0 auto; text-align:left'><legend>New season</legend>";
    var selectClose = "</select></div>";
    var seasonTxt = "Season #";
    var boxDiv = "<div class='styled-select' style='margin: 0 0 0 0; width:35%;'>";
    var seasonSelect = "<select id='seasonSelect" + seasonfields + "'>";
    var seasonLoops = document.getElementById("seasonSelect0").innerHTML;

    var episodeTxt = "</select></div><p></p> Number of episodes:";
    var episodeSelect = "<select name='episodeSelect" + seasonfields + "'>";
    var episodeLoops = document.getElementById("episodeSelect0").innerHTML;

    var yearTxt = "</select></div><p></p> Year:";
    var yearSelect = "<select name='yearSelect" + seasonfields + "'>";
    var yearLoops = document.getElementById("yearSelect0").innerHTML;

    var rightDiv = selectClose + "<div style='margin: 0 0 0 0; width:100%; text-align: right;'>";
    var deleteButton = "<button onclick='removeSeasonFS(" + seasonfields + ");'" +
            "class='button small'>Delete</button></div>";

    var close = "</fieldset></div><p></p>";

    var code = sFieldsDiv + fieldset + seasonTxt + boxDiv + seasonSelect + seasonLoops +
            episodeTxt + boxDiv + episodeSelect + episodeLoops +
            yearTxt + boxDiv + yearSelect + yearLoops +
            rightDiv + deleteButton + close;

    var currentDivs = document.getElementById("seasonfieldsHolder").innerHTML;
    document.getElementById("seasonfieldsHolder").innerHTML = currentDivs+code;
    document.getElementById("number_of_season_divs").value=seasonfields;

}

function removeSeasonFS(divNumber) {
    var divNum = parseInt(divNumber);
    document.getElementById("seasonfieldsDiv" + divNum).innerHTML = '';
}