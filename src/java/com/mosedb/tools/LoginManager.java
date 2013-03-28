/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.tools;

import com.mosedb.dao.UserDao;
import com.mosedb.models.User;
import javax.servlet.http.HttpSession;

/**
 *
 * @author llybeck
 */
public class LoginManager {

    public static User doLogin(HttpSession session, String username, String password) throws Exception {
        UserDao dao = new UserDao();
        User user = dao.getUser(username, password);
        session.setAttribute(userSessionKey(), user);
        return user;
    }

    private static String userSessionKey() {
        return "user_id";
    }

    public static User getLoggedUser(HttpSession session) {
        return (User) session.getAttribute(userSessionKey());
    }

    public static void doLogout(HttpSession session) {
        session.removeAttribute(userSessionKey());
    }
}