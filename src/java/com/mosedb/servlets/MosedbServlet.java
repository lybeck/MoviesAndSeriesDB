/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.servlets;

import com.mosedb.tools.AttributeManager;
import com.mosedb.tools.LoginManager;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        redirectHome(request, response);
    }

    protected void redirectHome(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (isUserLoggedIn(request)) {
            redirectToPage("search", request, response);
        } else {
            redirectToLoginPage(response);
        }
    }

    protected void restorePage(String page, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }

    protected void redirectToPage(String page, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (isUserLoggedIn(request)) {
            response.sendRedirect(page);
        } else {
            redirectToLoginPage(response);
        }
    }

    protected void setErrorMessage(String message, HttpServletRequest request) throws IOException {
        request.setAttribute("errorMessage", message);
    }

    protected boolean isUserLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        return AttributeManager.getUserSessionKey(session) != null;
    }

    private void redirectToLoginPage(HttpServletResponse response) throws IOException {
        response.sendRedirect("login.jsp");
    }
}
