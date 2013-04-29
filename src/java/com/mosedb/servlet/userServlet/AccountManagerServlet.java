package com.mosedb.servlet.userServlet;

import com.mosedb.business.UserService;
import com.mosedb.models.User;
import com.mosedb.servlet.MosedbServlet;
import com.mosedb.tools.AttributeManager;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Roope
 */
public class AccountManagerServlet extends MosedbServlet {

    private static final String PASSWORD_FIELD = "password";
    private static final String NEW_PASSWORD_FIELD = "new_password";
    private static final String FIRST_NAME_FIELD = "first_name";
    private static final String LAST_NAME_FIELD = "last_name";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        if (isUserLoggedIn(request)) {
            restorePage("accountManager.jsp", request, response);
        } else {
            redirectHome(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        if (isUserLoggedIn(request)) {
            String password = request.getParameter(PASSWORD_FIELD);
            String newPassword = request.getParameter(NEW_PASSWORD_FIELD);

            UserService userService = new UserService();
            User user = userService.getUser(AttributeManager.getUserInSession(request.getSession(true)).getUsername(), password);
            if (user == null) {
                AttributeManager.setErrorMessage(request, "Invalid Password");
                restorePage("accountManager.jsp", request, response);
                return;
            }
            User updatedUser = getUserFromFields(request, user);
            if (updatedUser == null) {
                restorePage("accountManager.jsp", request, response);
                return;
            }
            boolean success;
            if (newPassword != null && newPassword.length() != 0) {
                success = userService.updateUser(user.getUsername(), updatedUser, newPassword);
            } else {
                success = userService.updateUser(user.getUsername(), updatedUser, password);
            }
            if(success){
                AttributeManager.setSuccessMessage(request, "Succesfully updated account-information!");
                AttributeManager.setUserInSession(request.getSession(true), updatedUser);
            } else {
                AttributeManager.setErrorMessage(request, "Failed to update account-information!");
            }
            restorePage("accountManager.jsp", request, response);
        } else {
            redirectHome(request, response);
        }
    }

    private User getUserFromFields(HttpServletRequest request, User user) {
        String newFirstName = request.getParameter(FIRST_NAME_FIELD).trim();
        String newLastName = request.getParameter(LAST_NAME_FIELD).trim();


        if (newFirstName.isEmpty() || newLastName.isEmpty()) {
            AttributeManager.setErrorMessage(request, "All fields must be filled!");
            return null;
        }
        return new User(user.getUsername(), newFirstName, newLastName, user.isAdmin());
    }
}
