function addSearchLine(){
    var inputs = document.getElementsByTagName('input');
    var textInputs = 0;
    for(var i=0; i<inputs.length; i++){
        if(inputs[i].type == "text"){
            textInputs = textInputs+1;
        }
    }
    if(textInputs == 4){
        return;
    }
    
    var onclickTxt = " onclick=\"if(this.value == 'Search #" + (textInputs+1) +
                    "'){this.value=''};\"";
    var onblurTxt = " onblur=\"if(this.value == ''){this.value = 'Search #" + 
                    (textInputs+1) + "'};\"";
            
    var code = "<input type=\"text\" value=\"Search #" + (textInputs+1) + "\"" +
               onclickTxt + onblurTxt + " class=\"styled-textfield\" name=\"txt" + 
               (textInputs+1) + "\"" + " id=\"txt" + (textInputs+1) +
               "\" style=\"float: left; margin-left: 13%;\"/>" +
               "<div class=\"styled-select\" style=\"float: right; margin-right: 7%\">" + 
               " <select name=\"select" + (textInputs+1) + "\">" +  
               " <option>Name</option>" + 
               " <option>Genre</option>" +
               " <option>Media format</option>" +
               " <option>Year</option> </select> </div> <br><br>" + 
               " <div id=\"txtDiv" + (textInputs+1) + "\"></div>";

    document.getElementById("txtDiv" + textInputs).innerHTML=code;
}

function removeSearchLine(){
    var inputs = document.getElementsByTagName('input');
    var textInputs = 0;
    for(var i=0; i<inputs.length; i++){
        if(inputs[i].type == "text"){
            textInputs = textInputs+1;
        }
    }
    if(textInputs == 1){
        return;
    }
    
    document.getElementById("txtDiv" + (textInputs-1)).innerHTML='';
}
