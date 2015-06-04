package uy.edu.ucu.matchapp.models;

import com.google.gson.annotations.Expose;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class Players {
    @Expose
    private List<Player> playerList;

    public Players() { /* Required empty bean constructor */ }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }
}
