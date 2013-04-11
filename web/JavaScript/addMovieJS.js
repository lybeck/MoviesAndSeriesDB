function addGenreDropbox() {
    var selects = document.getElementsByTagName('select');
    var genreBoxes = 0;
    for (var i = 0; i < selects.length; i++) {
        if (selects[i].name.substring(0, 12) == "genreDropbox") {
            genreBoxes = genreBoxes + 1;
        }
    }
    
    var boxDiv = "<div class='styled-select' style='margin: 0 0 0 0'>";
    var selectDiv = "<select name='genreDropbox" + (genreBoxes+1) + "' id='genreSelect" + (genreBoxes+1) + "'>";
    var loops = document.getElementById("genreSelect0").innerHTML;
    var nextDiv = "</select></div><p></p><div id='genreDropboxDiv" + (genreBoxes + 2) + 
            "'></div> ";
    var code = "Genre #" + (genreBoxes+1) + boxDiv + selectDiv + loops  + nextDiv;
    
    document.getElementById("genreDropboxDiv" + (genreBoxes + 1)).innerHTML = code;
}

function removeGenreDropbox(){
    var selects = document.getElementsByTagName('select');
    var genreBoxes = 0;
    for (var i = 0; i < selects.length; i++) {
        if (selects[i].name.substring(0, 12) == "genreDropbox") {
            genreBoxes = genreBoxes + 1;
        }
    }
    if(genreBoxes == 0){
        return;
    }
    
    document.getElementById("genreDropboxDiv" + genreBoxes).innerHTML='';
}

