package uy.edu.ucu.matchapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Parcel
public class SoccerSeason {
    @Expose
    @SerializedName("_links")
    private HashMap<String, HashMap<String, String>> links;
    @Expose
    private String caption;
    @Expose
    private String league;
    @Expose
    private String year;
    @Expose
    private int numberOfTeams;
    @Expose
    private int numberOfGames;
    @Expose
    private Date lastUpdated;
    private List<Fixture> fixtures;

    public SoccerSeason() { /* Required empty bean constructor */ }

    public HashMap<String, HashMap<String, String>> getLinks() {
        return links;
    }

    public void setLinks(HashMap<String, HashMap<String, String>> links) {
        this.links = links;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getNumberOfTeams() {
        return numberOfTeams;
    }

    public void setNumberOfTeams(int numberOfTeams) {
        this.numberOfTeams = numberOfTeams;
    }

    public int getNumberOfGames() {
        return numberOfGames;
    }

    public void setNumberOfGames(int numberOfGames) {
        this.numberOfGames = numberOfGames;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public List<Fixture> getFixtures() {
        return fixtures;
    }

    public void setFixtures(List<Fixture> fixtures) {
        this.fixtures = fixtures;
    }

    @Override
    public String toString() {
        return getCaption();
    }
}
