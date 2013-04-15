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

function addMediaFormatDropbox() {
    var selects = document.getElementsByTagName('select');
    var formatBoxes = 0;
    for (var i = 0; i < selects.length; i++) {
        if (selects[i].name.substring(0, 18) == "mediaFormatDropbox") {
            formatBoxes = formatBoxes + 1;
        }
    }

    var boxDiv = "<div class='styled-select' style='margin: 0 0 0 0'>";
    var selectDiv = "<select name='mediaFormatDropbox" + (formatBoxes + 1) + "' id='formatSelect" + 
            (formatBoxes + 1) + "' onclick='addAdditionalInfo(" + (formatBoxes + 1) + ");'>";
    var loops = document.getElementById("formatSelect1").innerHTML;
    var nextDiv = "</select></div>" + "<div id='additionalInfoDiv" + (formatBoxes+1) + 
            "'></div><p></p><div id='mediaFormatDropboxDiv" + (formatBoxes + 1) +
            "'></div> ";
    var code = "format #" + (formatBoxes + 1) + boxDiv + selectDiv + loops + nextDiv;
    document.getElementById("mediaFormatDropboxDiv" + formatBoxes).innerHTML = code;
}

function removeMediaFormatDropbox() {
    var selects = document.getElementsByTagName('select');
    var formatBoxes = 0;
    for (var i = 0; i < selects.length; i++) {
        if (selects[i].name.substring(0, 18) == "mediaFormatDropbox") {
            formatBoxes = formatBoxes + 1;
        }
    }
    if (formatBoxes == 1) {
        return;
    }

    document.getElementById('mediaFormatDropboxDiv' + (formatBoxes - 1)).innerHTML = '';
}

function addAdditionalInfo(boxNumber) {
    try {
        var Bnum = parseInt(boxNumber);
        if (document.getElementById('formatSelect' + Bnum).value == 'dc') {

            var fileType = "<br>File-type : <input type='text' class='styled-textfield'" +
                    "id='formatFields' name='fileType" + Bnum + "'>";
            var resox = "&emsp;Width : <input type='text' class='styled-textfield'" +
                    "id='formatFields' name='resox" + Bnum + "'>";
            var resoy = "&emsp;Height : <input type='text' class='styled-textfield'" +
                    "id='formatFields' name='resoy" + Bnum + "'>";
            var code = fileType + resox + resoy;

            document.getElementById('additionalInfoDiv' + Bnum).innerHTML = code;

        } else {
            document.getElementById('additionalInfoDiv' + Bnum).innerHTML = '';
        }

    } catch (err) {
        alert("error parsing integer-parameter:\n" + err.message);
    }
}




