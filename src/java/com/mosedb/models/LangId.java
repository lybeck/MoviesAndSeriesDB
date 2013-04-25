/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.models;

/**
 *
 * @author Lasse
 */
public enum LangId {

    eng, fi, swe, other;
    
    public static LangId getLangId(String langId) {
        LangId enumLangId = LangId.other;
        if (langId.equalsIgnoreCase("eng")) {
            enumLangId = LangId.eng;
        } else if (langId.equalsIgnoreCase("fi")) {
            enumLangId = LangId.fi;
        } else if (langId.equalsIgnoreCase("swe")) {
            enumLangId = LangId.swe;
        }
        return enumLangId;
    }
}
