package uy.edu.ucu.matchapp.network;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import uy.edu.ucu.matchapp.models.FixtureDetail;
import uy.edu.ucu.matchapp.models.Fixtures;
import uy.edu.ucu.matchapp.models.LeagueTable;
import uy.edu.ucu.matchapp.models.Players;
import uy.edu.ucu.matchapp.models.SoccerSeason;
import uy.edu.ucu.matchapp.models.Team;

public interface FootballDataService {
    @GET("/fixtures")
    void getFixtures(@Query("timeFrameStart") String timeFrameStart, @Query("timeFrameEnd") String timeFrameEnd, Callback<Fixtures> cb);

    @GET("/fixtures/{id}")
    void getFixtureDetail(@Path("id") int fixtureId, Callback<FixtureDetail> cb);

    @GET("/soccerseasons")
    void getSoccerSeasons(Callback<ArrayList<SoccerSeason>> cb);

    @GET("/soccerseasons/{id}")
    void getSoccerSeason(@Path("id") int soccerSeasonId, Callback<SoccerSeason> cb);

    @GET("/soccerseasons/{id}/leagueTable")
    void getSoccerSeasonLeagueTable(@Path("id") int soccerSeasonId, Callback<LeagueTable> cb);

    @GET("/teams/{id}")
    void getTeam(@Path("id") int teamId, Callback<Team> cb);

    @GET("/teams/{id}/players")
    void getTeamPlayers(@Path("id") int teamId, Callback<Players> cb);
}
