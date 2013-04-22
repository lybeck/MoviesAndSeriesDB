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
 * @author Easysimulation
 */
public class AddUserServlet extends MosedbServlet {

    private static final String USERNAME_FIELD = "username";
    private static final String PASSWORD_FIELD = "password";
    private static final String FIRST_NAME_FIELD = "firstName";
    private static final String LAST_NAME_FIELD = "lastName";
    private static final String ADMIN_BOX = "adminBox";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        redirectHome(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        if (isUserLoggedIn(request)) {
            User user = AttributeManager.getUserSessionKey(request.getSession(true));
            if (user.isAdmin()) {
                User userToBeAdded = getUserFromFields(request);
                if (userToBeAdded != null) {
                    String password = request.getParameter(PASSWORD_FIELD).trim();
                    if (!password.isEmpty()) {
                        UserService userService = new UserService();
                        boolean success = userService.addUser(userToBeAdded, password);
                        if (success) {
                            AttributeManager.setSuccessMessage(request, "User added successfully!");
                        } else {
                            AttributeManager.setErrorMessage(request, "Username already exists!");
                        }
                    }
                }
                UserService userservice = new UserService();
                AttributeManager.setUserList(request, userservice.getAllUsers());
                restorePage("adminTools.jsp", request, response);
            } else {
                redirectHome(request, response);
            }
        } else {
            redirectHome(request, response);
        }
    }

    private User getUserFromFields(HttpServletRequest request) {
        String username = request.getParameter(USERNAME_FIELD).trim();
        String firstName = request.getParameter(FIRST_NAME_FIELD).trim();
        String lastName = request.getParameter(LAST_NAME_FIELD).trim();

        String adminCheck = request.getParameter(ADMIN_BOX);
        boolean isAdminChecked = (adminCheck != null);

        if (username.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
            AttributeManager.setErrorMessage(request, "All fields must be filled!");
            return null;
        }
        return new User(username, firstName, lastName, isAdminChecked);
    }
}
