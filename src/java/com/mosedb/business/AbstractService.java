/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.business;

/**
 *
 * @author llybeck
 */
public abstract class AbstractService {

    protected void reportError(String message, Exception ex) {
        System.err.println(message);
        System.err.println("Error:");
        System.err.println(ex);
    }
}
