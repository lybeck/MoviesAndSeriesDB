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
 * @author Lasse
 */
public class LoginManager {

    public static User doLogin(HttpSession session, String username, String password) throws Exception {
        UserService userService = new UserService();
        User user = userService.getUser(username, password);
        AttributeManager.setUserInSession(session, user);

        if (user == null || !user.isAdmin()) {  
            AttributeManager.setAdminSessionKey(session, false);
        } else {
            AttributeManager.setAdminSessionKey(session, true);
        }
        return user;
    }

    public static void doLogout(HttpSession session) {
        AttributeManager.removeAll(session);
    }
}
