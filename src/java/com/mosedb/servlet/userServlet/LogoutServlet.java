/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.servlet.userServlet;

import com.mosedb.servlet.MosedbServlet;
import com.mosedb.tools.LoginManager;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Handles the logout functionality and redirects to login page.
 *
 * @author Lasse
 */
public class LogoutServlet extends MosedbServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(true);
        LoginManager.doLogout(session);
        redirectHome(request, response);
    }
}
