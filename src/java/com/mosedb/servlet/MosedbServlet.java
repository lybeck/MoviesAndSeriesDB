/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.servlet;

import com.mosedb.tools.AttributeManager;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Lasse
 */
public class MosedbServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        redirectHome(request, response);
    }

    /**
     * Redirects the user to the login page if not logged in, otherwise to the
     * search page.
     *
     * @param request
     * @param response
     * @throws IOException
     */
    protected void redirectHome(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (isUserLoggedIn(request)) {
            redirectToPage("search", request, response);
        } else {
            redirectToLoginPage(response);
        }
    }

    /**
     * Restores the specified web page.
     *
     * @param page The page name.
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void restorePage(String page, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }

    /**
     * Redirects user to the specified page, or to the login page if not logged
     * in.
     *
     * @param page The page name.
     * @param request
     * @param response
     * @throws IOException
     */
    protected void redirectToPage(String page, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (isUserLoggedIn(request)) {
            response.sendRedirect(page);
        } else {
            redirectToLoginPage(response);
        }
    }

    /**
     * Checks if a user is logged in.
     *
     * @param request
     * @return {@code true} if a user is logged in, otherwise {@code false}.
     */
    protected boolean isUserLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        return AttributeManager.getUserInSession(session) != null;
    }

    /**
     * Redirects the user to the login page.
     *
     * @param response
     * @throws IOException
     */
    private void redirectToLoginPage(HttpServletResponse response) throws IOException {
        response.sendRedirect("login.jsp");
    }
}
