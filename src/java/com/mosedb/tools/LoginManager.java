package com.mosedb.tools;

import com.mosedb.business.UserService;
import com.mosedb.models.User;
import javax.servlet.http.HttpSession;

/**
 * LoginManager class manages the login- and logout functionality.
 *
 * @author Lasse
 */
public class LoginManager {

    /**
     * The login functionality. Uses {@code UserService()} to get the user
     * with given parameters, and {@code AttributeManager() } to set the user to the 
     * current session.
     *
     * @param session The current session.
     * @param username Users username.
     * @param password Users password.
     * @return The matching user according to the username and password,
     * {@code null} if no user was found with given parameters.;
     */
    public static User doLogin(HttpSession session, String username, String password){
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
    
    /**
     * Handles the logout functionality; uses {@code AttributeManager()} to remove
     * all object from current session.
     * @param session 
     */
    public static void doLogout(HttpSession session) {
        AttributeManager.removeAll(session);
    }
}
