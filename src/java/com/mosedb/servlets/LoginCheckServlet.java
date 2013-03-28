/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author llybeck
 */
public class LoginCheckServlet extends MosedbServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (isUserLoggedIn(request)) {
            redirectToPage("search.jsp", response);
        } else {
            redirectToPage("login.jsp", response);
        }
    }
}
