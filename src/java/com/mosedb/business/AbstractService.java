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

    /**
     * Reports connection error to the error log.
     *
     * @param ex Exeption, whose error message is reported.
     */
    protected void reportConnectionError(Exception ex) {
        System.err.println("Error while connecting to database!");
        System.err.println("Error:");
        System.err.println(ex);
    }

    /**
     * Reports error to the error log.
     * 
     * @param message Error message to be printed.
     * @param ex Exeption, whose error message is reported.
     */
    protected void reportError(String message, Exception ex) {
        System.err.println(message);
        System.err.println("Error:");
        System.err.println(ex);
    }
}
