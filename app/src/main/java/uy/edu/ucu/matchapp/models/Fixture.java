package uy.edu.ucu.matchapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.Date;
import java.util.HashMap;

@Parcel
public class Fixture {
    @Expose
    @SerializedName("_links")
    private HashMap<String, HashMap<String, String>> links;
    @Expose
    private Date date;
    @Expose
    private String status;
    @Expose
    @SerializedName("matchday")
    private Integer matchDay;
    @Expose
    private String homeTeamName;
    @Expose
    private String awayTeamName;
    private SoccerSeason soccerSeason;
    private Team homeTeam;
    private Team awayTeam;

    public Fixture() { /* Required empty bean constructor */ }

    public HashMap<String, HashMap<String, String>> getLinks() {
        return links;
    }

    public void setLinks(HashMap<String, HashMap<String, String>> links) {
        this.links = links;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getMatchDay() {
        return matchDay;
    }

    public void setMatchDay(Integer matchDay) {
        this.matchDay = matchDay;
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public void setHomeTeamName(String homeTeamName) {
        this.homeTeamName = homeTeamName;
    }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public void setAwayTeamName(String awayTeamName) {
        this.awayTeamName = awayTeamName;
    }

    public SoccerSeason getSoccerSeason() {
        return soccerSeason;
    }

    public void setSoccerSeason(SoccerSeason soccerSeason) {
        this.soccerSeason = soccerSeason;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }
}
