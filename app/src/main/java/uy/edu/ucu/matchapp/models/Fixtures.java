package uy.edu.ucu.matchapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.ArrayList;

@Parcel
public class Fixtures {
    @Expose
    private String timeFrameStart;
    @Expose
    private String timeFrameEnd;
    @Expose
    private Integer count;
    @Expose
    @SerializedName("fixtures")
    private ArrayList<Fixture> fixtureList;

    public Fixtures() { /* Required empty bean constructor */ }

    public String getTimeFrameStart() {
        return timeFrameStart;
    }

    public void setTimeFrameStart(String timeFrameStart) {
        this.timeFrameStart = timeFrameStart;
    }

    public String getTimeFrameEnd() {
        return timeFrameEnd;
    }

    public void setTimeFrameEnd(String timeFrameEnd) {
        this.timeFrameEnd = timeFrameEnd;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public ArrayList<Fixture> getFixtureList() {
        return fixtureList;
    }

    public void setFixtureList(ArrayList<Fixture> fixtureList) {
        this.fixtureList = fixtureList;
    }
}
