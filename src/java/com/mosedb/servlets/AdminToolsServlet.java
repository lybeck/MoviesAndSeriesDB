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
public class AdminToolsServlet extends MosedbServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        if (isUserLoggedIn(request)) {
            User user = AttributeManager.getUserSessionKey(request.getSession(true));
            if (user.isAdmin()) {
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
}
