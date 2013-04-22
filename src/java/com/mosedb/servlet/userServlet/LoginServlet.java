/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.servlet.userServlet;

import com.mosedb.models.User;
import com.mosedb.servlet.MosedbServlet;
import com.mosedb.tools.AttributeManager;
import com.mosedb.tools.LoginManager;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author llybeck
 */
public class LoginServlet extends MosedbServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isUserLoggedIn(request)) {
            redirectHome(request, response);
        } else {
            redirectToPage("login", request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(true);
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        try {
            User user = LoginManager.doLogin(session, username, password);
            if (user != null) {
                redirectHome(request, response);
            } else {
                AttributeManager.setErrorMessage(request, "Invalid username or password!");
                restorePage("login.jsp", request, response);
            }
        } catch (Exception e) {
            System.out.println("Unknown error while trying to log in!");
        }
    }
}
