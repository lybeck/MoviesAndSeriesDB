package com.mosedb.servlets;

import com.mosedb.business.UserService;
import com.mosedb.models.User;
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
        if (isUserLoggedIn(request)) {
            User user = AttributeManager.getUserSessionKey(request.getSession(true));
            if (user.isAdmin()) {
                User userToBeAdded = getUserFromFields(request);
                if(userToBeAdded!=null){
                    String password = request.getParameter(PASSWORD_FIELD).trim();
                    if(!password.isEmpty()){
                        UserService userService=new UserService();
                        userService.addUser(userToBeAdded, password);
                    }
                }
                redirectToPage("adminTools", request, response);
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
            return null;
        }
        return new User(username, firstName, lastName, isAdminChecked);
    }
}
