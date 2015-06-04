package uy.edu.ucu.matchapp.models;

import com.google.gson.annotations.Expose;

import org.parceler.Parcel;

@Parcel
public class Player {
    @Expose
    private String name;
    @Expose
    private String position;
    @Expose
    private int jerseyNumber;
    @Expose
    private String nationality;

    public Player() { /* Required empty bean constructor */ }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getJerseyNumber() {
        return jerseyNumber;
    }

    public void setJerseyNumber(int jerseyNumber) {
        this.jerseyNumber = jerseyNumber;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}
