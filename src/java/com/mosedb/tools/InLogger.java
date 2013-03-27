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
public class InLogger {

    public static User doKirjauduSisaan(HttpSession session, String username, String password) throws Exception {
        UserDao dao = new UserDao();
        User user = dao.getUser(username, password);
        session.setAttribute(kayttajaSessioAvain(), user.getUsername());
        return user;
    }

    private static String kayttajaSessioAvain() {
        return "user_id";
    }

//    public static User getKirjautunutKayttaja(HttpSession session) throws Exception {
//        long kayttajaId = ((Long) session.getAttribute(kayttajaSessioAvain())).longValue();
//        KayttajaVarasto varasto = new KayttajaVarasto();
//        Kayttaja kayttaja = varasto.haeKayttaja(kayttajaId);
//        return kayttaja;
//    }
}
