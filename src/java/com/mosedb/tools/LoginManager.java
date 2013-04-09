/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.tools;

import com.mosedb.business.UserService;
import com.mosedb.models.User;
import javax.servlet.http.HttpSession;

/**
 *
 * @author llybeck
 */
public class LoginManager {

    public static User doLogin(HttpSession session, String username, String password) throws Exception {
        UserService userService = new UserService();
        User user = userService.getUser(username, password);
        session.setAttribute(userSessionKey(), user);

        if (user == null || !user.isAdmin()) {  
            session.setAttribute(adminSessionKey(), false);
        } else {
            session.setAttribute(adminSessionKey(), true);
        }
        return user;
    }

    private static String userSessionKey() {
        return "user_id";
    }

    private static String adminSessionKey() {
        return "admin_key";
    }

    public static User getLoggedUser(HttpSession session) {
        return (User) session.getAttribute(userSessionKey());
    }

    public static boolean loggedUserIsAdmin(HttpSession session) {
        return (Boolean) session.getAttribute(adminSessionKey());
    }

    public static void doLogout(HttpSession session) {
        session.removeAttribute(userSessionKey());
        session.removeAttribute(adminSessionKey());
    }
}
