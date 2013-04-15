/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.models;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author llybeck
 */
public class Format {

    private int id;
    private MediaFormat mediaFormat;
    private String fileType;
    private Integer resoX;
    private Integer resoY;

    public Format(int id, MediaFormat mediaFormat) {
        this.id = id;
        this.mediaFormat = mediaFormat;
    }

    public Format(int id, MediaFormat mediaFormat, String fileType) {
        this.id = id;
        this.mediaFormat = mediaFormat;
        this.fileType = fileType;
    }

    public Format(int id, MediaFormat mediaFormat, String fileType, Integer resoX, Integer resoY) {
        this.id = id;
        this.mediaFormat = mediaFormat;
        this.fileType = fileType;
        this.resoX = resoX;
        this.resoY = resoY;
    }

    public Format(MediaFormat mediaFormat) {
        this.mediaFormat = mediaFormat;
    }

    public Format(MediaFormat mediaFormat, String fileType) {
        this.mediaFormat = mediaFormat;
        this.fileType = fileType;
    }

    public Format(MediaFormat mediaFormat, String fileType, Integer resoX, Integer resoY) {
        this.mediaFormat = mediaFormat;
        this.fileType = fileType;
        this.resoX = resoX;
        this.resoY = resoY;
    }

    public Integer getResoY() {
        return resoY;
    }

    public void setResoY(Integer resoY) {
        this.resoY = resoY;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MediaFormat getMediaFormat() {
        return mediaFormat;
    }

    public void setMediaFormat(MediaFormat mediaFormat) {
        this.mediaFormat = mediaFormat;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Integer getResoX() {
        return resoX;
    }

    public void setResoX(Integer resoX) {
        this.resoX = resoX;
    }

    @Override
    public String toString() {
        String s = "(" + mediaFormat;
        if (fileType != null) {
            s += ", " + fileType;
            if (resoX != null && resoY != null) {
                s += ", " + resoX + "x" + resoY;
            }
        }
        s += ")";
        return s;
    }

    public static List<String> getAllMediaFormats() {
        MediaFormat[] values = MediaFormat.values();
        List<String> list = new ArrayList<String>(values.length);
        for (int i = 0; i < values.length; i++) {
            list.add(values[i].toString());
        }
        return list;
    }

    public static MediaFormat getMediaFormat(String mediaFormat) {
        if (mediaFormat.equalsIgnoreCase("vhs")) {
            return MediaFormat.vhs;
        }
        if (mediaFormat.equalsIgnoreCase("dvd")) {
            return MediaFormat.dvd;
        }
        if (mediaFormat.equalsIgnoreCase("bd")) {
            return MediaFormat.bd;
        }
        if (mediaFormat.equalsIgnoreCase("dc")) {
            return MediaFormat.dc;
        }
        return null;
    }

    public boolean hasFileInfo() {
        return mediaFormat == MediaFormat.dc && fileType != null;
    }

    public boolean hasResoInfo() {
        return resoX != null && resoY != null && resoX != 0 && resoY != 0;
    }

    public static enum MediaFormat {

        dvd, bd, dc, vhs;
    }
}
