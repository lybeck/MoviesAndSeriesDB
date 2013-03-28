/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.servlets;

import com.mosedb.tools.LoginManager;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author llybeck
 */
public class MosedbServlet extends HttpServlet {

    protected void restorePage(String page, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }

    protected void redirectToPage(String page, HttpServletResponse response) throws IOException {
        response.sendRedirect(page);
    }

    protected void setErrorMessage(String message, HttpServletRequest request) {
        request.setAttribute("errorMessage", message);
    }

    protected boolean isUserLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        if (LoginManager.getLoggedUser(session) != null) {
            return true;
        } else {
            return false;
        }
    }
}
