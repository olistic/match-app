package uy.edu.ucu.matchapp.models;

import com.google.gson.annotations.Expose;

import org.parceler.Parcel;

import java.util.Date;
import java.util.List;

@Parcel
public class StandingTeam {
    @Expose
    private int position;
    @Expose
    private String teamName;
    @Expose
    private int playedGames;
    @Expose
    private int points;
    @Expose
    private int goals;
    @Expose
    private int goalsAgainst;
    @Expose
    private int goalDifference;

    public StandingTeam() { /* Required empty bean constructor */ }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getPlayedGames() {
        return playedGames;
    }

    public void setPlayedGames(int playedGames) {
        this.playedGames = playedGames;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public int getGoalsAgainst() {
        return goalsAgainst;
    }

    public void setGoalsAgainst(int goalsAgainst) {
        this.goalsAgainst = goalsAgainst;
    }

    public int getGoalDifference() {
        return goalDifference;
    }

    public void setGoalDifference(int goalDifference) {
        this.goalDifference = goalDifference;
    }
}
