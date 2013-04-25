/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.business;

/**
 *
 * @author Lasse
 */
public abstract class AbstractService {
    
    protected void reportConnectionError(Exception ex) {
        System.err.println("Error while connecting to database!");
        System.err.println("Error:");
        System.err.println(ex);
    }

    protected void reportError(String message, Exception ex) {
        System.err.println(message);
        System.err.println("Error:");
        System.err.println(ex);
    }
}
