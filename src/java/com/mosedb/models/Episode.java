/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mosedb.models;

/**
 *
 * @author llybeck
 */
public class Episode implements Comparable<Episode> {

    private int seriesId;
    private int seasonNumber;
    private int episodeNumber;
    private String episodeName;
    private Integer episodeYear;
    private boolean seen;

    public Episode(int seriesId, int seasonNumber, int episodeNumber) {
        this.seriesId = seriesId;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
    }

    public Episode(int seriesId, int seasonNumber, int episodeNumber, String episodeName, boolean seen) {
        this.seriesId = seriesId;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
        this.episodeName = episodeName;
        this.seen = seen;
    }

    public Episode(int seriesId, int seasonNumber, int episodeNumber, String episodeName, Integer episodeYear, boolean seen) {
        this.seriesId = seriesId;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
        this.episodeName = episodeName;
        this.episodeYear = episodeYear;
        this.seen = seen;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public int getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(int seriesId) {
        this.seriesId = seriesId;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public String getEpisodeName() {
        return episodeName;
    }

    public void setEpisodeName(String episodeName) {
        this.episodeName = episodeName;
    }

    public Integer getEpisodeYear() {
        return episodeYear;
    }

    public void setEpisodeYear(Integer episodeYear) {
        this.episodeYear = episodeYear;
    }

    @Override
    public int compareTo(Episode o) {
        if (seriesId < o.seriesId) {
            return -1;
        }
        if (seriesId > o.seriesId) {
            return 1;
        }
        if (seasonNumber < o.seasonNumber) {
            return -1;
        }
        if (seasonNumber > o.seasonNumber) {
            return 1;
        }
        if (episodeNumber < o.episodeNumber) {
            return -1;
        }
        if (episodeNumber > o.episodeNumber) {
            return 1;
        }
        return 0;
    }
    
    @Override
    public String toString() {
        return "Episode:\n"
                + "Series:\t\t" + seriesId + "\n"
                + "Season:\t\t" + seasonNumber + "\n"
                + "Episode:\t" + episodeNumber + "\n"
                + "Name:\t\t" + episodeName + "\n"
                + "Year:\t\t" + episodeYear + "\n"
                + "Seen:\t\t" + seen + "\n";
    }
}
