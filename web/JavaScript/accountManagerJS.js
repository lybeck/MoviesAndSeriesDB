function passwordsMatch() {
    var passwordField = document.getElementById('password');
    var newPasswordField = document.getElementById('new_password');
    var confirmNewPasswordField = document.getElementById('confirm_new_password');
    var errorWrapper = document.getElementById('password_error_wrapper');

    if (passwordField.value.length === 0) {
        errorWrapper.innerHTML = errorAction("Input Your password!");
        return;
    }
    if (newPasswordField.value !== confirmNewPasswordField.value) {
        errorWrapper.innerHTML = errorAction("New passwords do not match!");
        return;
    }
    if (newPasswordField.value === passwordField.value) {
        errorWrapper.innerHTML = errorAction("New password has to differ from original password!");
        return;
    }
    if (newPasswordField.value === confirmNewPasswordField.value) {
        errorWrapper.innerHTML = '';
        document.getElementById('submit_button').disabled = false;
    }
}

function errorAction(message) {
    var code = "<p id='errorMessage'>" + message + "</p>";
    document.getElementById('submit_button').disabled = true;
    return code;
}


