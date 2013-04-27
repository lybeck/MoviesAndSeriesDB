function passwordsMatch() {
    if (document.getElementById('password').value.length === 0) {
        document.getElementById('password_error_wrapper').innerHTML = errorAction("Input Your password!");
        return;
    }
    if (document.getElementById('new_password').value !== document.getElementById('confirm_new_password').value) {
        document.getElementById('password_error_wrapper').innerHTML = errorAction("New passwords do not match!");
        return;
    }
    if (document.getElementById('new_password').value === document.getElementById('password').value) {
        document.getElementById('password_error_wrapper').innerHTML = errorAction("New password has to differ from original password!");
        return;
    }
    if (document.getElementById('new_password').value === document.getElementById('confirm_new_password').value) {
        document.getElementById('password_error_wrapper').innerHTML = '';
        document.getElementById('submit_button').disabled = false;
    }
}

function errorAction(message) {
    var code = "<p id='errorMessage'>" + message + "</p>";
    document.getElementById('submit_button').disabled = true;
    return code;
}


