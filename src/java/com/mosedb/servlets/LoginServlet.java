/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.servlets;

import com.mosedb.models.User;
import com.mosedb.tools.InLogger;
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        try {
            User user = InLogger.doLogin(session, username, password);
            if (user != null) {
                redirectToPage("search.jsp", response);
            } else {
                setErrorMessage("Invalid username or password!", request);
                restorePage("login.jsp", request, response);
            }
        } catch (Exception e) {
            System.out.println("Unknown error while trying to log in!");
        }
    }
}
