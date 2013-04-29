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
    var sFieldsDiv = "<div id=seasonfieldsDiv>";
    var fieldset = "<fieldset class='styledFS smallCenetered' name='seasonfields'>" +
            "<legend>New season</legend>";

    var seenCheckbox = "<div id='seasonFieldSeenDiv'>" +
            "Seen: <input type='checkbox' value='new_season_seen_checkbox' name='new_season_seen_checkbox' " +
            "id='new_season_seen_checkbox'><label class='customCheck' " +
            "for='new_season_seen_checkbox' style='float: right;'></label></div>";

    var selectClose = "</select></div>";
    var seasonTxt = "Season #";
    var boxDiv = "<div class='styled-select' style='margin: 0 0 0 0; width:35%;'>";
    var seasonSelect = "<select name='new_season_select' id='new_season_select' " +
            "onchange='checkNewSeasonDropboxes();'>";
    var seasonLoops = document.getElementById("seasonSelect0").innerHTML;

    var episodeTxt = "</select></div><p></p> Number of episodes:";
    var episodeSelect = "<select name='new_season_episode_select' id='new_season_episode_select' " +
            "onchange='checkNewSeasonDropboxes()'>";
    var episodeLoops = document.getElementById("episodeSelect0").innerHTML;

    var yearTxt = "</select></div><p></p> Year:";
    var yearSelect = "<select name='new_season_year_select'>";
    var yearLoops = document.getElementById("yearSelect0").innerHTML;

    var rightDiv = selectClose + "<div style='margin: 0 0 0 0; width:100%; text-align: right;'>";
    var deleteButton = "<button onclick='removeSeasonFS();'" +
            "class='button small'>Hide</button></div>";

    var close = "</fieldset></div><p></p>";

    var code = sFieldsDiv + fieldset + seenCheckbox + seasonTxt +
            boxDiv + seasonSelect + seasonLoops +
            episodeTxt + boxDiv + episodeSelect + episodeLoops +
            yearTxt + boxDiv + yearSelect + yearLoops +
            rightDiv + deleteButton + close;

    document.getElementById("seasonfieldsHolder").innerHTML = code;
    document.getElementById("plusButtonHolder").innerHTML = '';
    checkNewSeasonDropboxes();

}

function deleteSeasonFS() {
    var code = document.getElementById('hiddenDeleteSeasonFS').innerHTML;
    document.getElementById('deleteSeasonfieldsHolder').innerHTML = code;
    document.getElementById("deleteSeasonButtonHolder").innerHTML = '';
}

function removeSeasonFS() {
    document.getElementById("seasonfieldsHolder").innerHTML = '';

    var plusBCode = "<input type='button' class='button small' value='Add season' id='addSeasonButton'" +
            "onclick='addSeasonFS();'>" +
            "<p></p>";

    document.getElementById("plusButtonHolder").innerHTML = plusBCode;
    checkNewSeasonDropboxes();
}

function removeDeleteSeasonFS() {
    document.getElementById("deleteSeasonfieldsHolder").innerHTML = '';

    var plusBCode = "<input type='button' class='button small' value='Delete season' id='deleteSeasonButton'" +
            "onclick='deleteSeasonFS();'>" +
            "<p></p>";

    document.getElementById("deleteSeasonButtonHolder").innerHTML = plusBCode;
}

function checkNewSeasonDropboxes() {
    try {
        var episodeDropboxValue = document.getElementById('new_season_episode_select').value;
        var seasonDropboxValue = document.getElementById('new_season_select').value;
    } catch (error) {
        document.getElementById("update_series").disabled = false;
    }

    if (seasonDropboxValue.length === 0 || seasonDropboxValue.length > 5 ||
            episodeDropboxValue.length === 0) {
        document.getElementById("update_series").disabled = true;
    } else {
        document.getElementById("update_series").disabled = false;
    }
}