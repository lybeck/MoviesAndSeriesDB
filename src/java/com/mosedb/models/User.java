/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.models;

/**
 *
 * @author Lasse
 */
public class User {

    private String username;
    private String firstName;
    private String lastName;
    private boolean admin;

    public User(String username, String firstName, String lastName, boolean admin) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.admin = admin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "Username:\t" + username + "\n"
                + "First name:\t" + firstName + "\n"
                + "Last name:\t" + lastName + "\n"
                + "Is admin:\t" + admin;
    }
}
