package uy.edu.ucu.matchapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.Date;
import java.util.List;

@Parcel
public class LeagueTable {
    @Expose
    private String leagueCaption;
    @Expose
    @SerializedName("matchday")
    private int matchDay;
    @Expose
    private List<StandingTeam> standing;

    public LeagueTable() { /* Required empty bean constructor */ }

    public String getLeagueCaption() {
        return leagueCaption;
    }

    public void setLeagueCaption(String leagueCaption) {
        this.leagueCaption = leagueCaption;
    }

    public int getMatchDay() {
        return matchDay;
    }

    public void setMatchDay(int matchDay) {
        this.matchDay = matchDay;
    }

    public List<StandingTeam> getStanding() {
        return standing;
    }

    public void setStanding(List<StandingTeam> standing) {
        this.standing = standing;
    }
}
