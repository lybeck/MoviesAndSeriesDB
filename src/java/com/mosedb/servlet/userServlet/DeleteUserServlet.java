package com.mosedb.servlet.userServlet;

import com.mosedb.business.UserService;
import com.mosedb.models.User;
import com.mosedb.servlet.MosedbServlet;
import com.mosedb.tools.AttributeManager;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Easysimulation
 */
public class DeleteUserServlet extends MosedbServlet {
    
    private static final String SELECT_GROUP="deleteSelect";

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
                String[] checks = request.getParameterValues(SELECT_GROUP);
                if (checks != null) {
                    UserService userService = new UserService();
                    for (String username : checks) {
                        userService.deleteUser(username);
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
    }
