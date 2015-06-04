package uy.edu.ucu.matchapp.models;

import com.google.gson.annotations.Expose;

import org.parceler.Parcel;

@Parcel
public class FixtureDetail {
    @Expose
    private Fixture fixture;
    @Expose
    private HeadToHead headToHead;

    public FixtureDetail() { /* Required empty bean constructor */ }

    public Fixture getFixture() {
        return fixture;
    }

    public void setFixture(Fixture fixture) {
        this.fixture = fixture;
    }

    public HeadToHead getHeadToHead() {
        return headToHead;
    }

    public void setHeadToHead(HeadToHead headToHead) {
        this.headToHead = headToHead;
    }
}
