package uy.edu.ucu.matchapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.ArrayList;

@Parcel
public class HeadToHead {


    @Expose
    private Integer homeTeamWins;
    @Expose
    private Integer awayTeamWins;
    @Expose
    private Integer draws;
    @Expose
    @SerializedName("fixtures")
    private ArrayList<Fixture> fixtureList;

    public HeadToHead() { /* Required empty bean constructor */ }




    public Integer getHomeTeamWins() {
        return homeTeamWins;
    }

    public void setHomeTeamWins(Integer homeTeamWins) {
        this.homeTeamWins = homeTeamWins;
    }

    public Integer getAwayTeamWins() {
        return awayTeamWins;
    }

    public void setAwayTeamWins(Integer awayTeamWins) {
        this.awayTeamWins = awayTeamWins;
    }

    public Integer getDraws() {
        return draws;
    }

    public void setDraws(Integer draws) {
        this.draws = draws;
    }

    public ArrayList<Fixture> getFixtureList() {
        return fixtureList;
    }

    public void setFixtureList(ArrayList<Fixture> fixtureList) {
        this.fixtureList = fixtureList;
    }
}
