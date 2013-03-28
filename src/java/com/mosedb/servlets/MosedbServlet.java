/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
}
