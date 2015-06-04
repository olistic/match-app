package uy.edu.ucu.matchapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.parceler.Parcels;
import org.w3c.dom.Text;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import uy.edu.ucu.matchapp.models.LeagueTable;
import uy.edu.ucu.matchapp.models.Player;
import uy.edu.ucu.matchapp.models.Players;
import uy.edu.ucu.matchapp.models.StandingTeam;
import uy.edu.ucu.matchapp.models.Team;
import uy.edu.ucu.matchapp.network.RestClient;
import uy.edu.ucu.matchapp.views.adapters.PlayerListItemView;


public class TeamActivity extends Activity {

    private Team mTeam;
    private int teamID;
    private LeagueTable mLeagueTable;
    private StandingTeam mStandingTeam;
    private TextView marketvalue;
    private TextView teamName;
    private TextView teamCurrentPosition;
    private TextView team_pj;
    private TextView team_gf;
    private TextView team_ga;
    private TextView team_dif;
    private TextView team_pts;
    private LinearLayout playersLinearLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        // Get mTeam from intent
        mTeam = Parcels.unwrap(this.getIntent().getParcelableExtra("TEAM"));
        mLeagueTable = Parcels.unwrap(this.getIntent().getParcelableExtra("LEAGUETABLE"));
        teamID = Parcels.unwrap(this.getIntent().getParcelableExtra("TEAMID"));

        // Set Action Bar title to mTeam's name
        if (mTeam.getName() != null) {
            getActionBar().setTitle(mTeam.getName());
        }

        for(StandingTeam st : mLeagueTable.getStanding()){
            if(st.getTeamName().equals(mTeam.getName())){
                mStandingTeam = st;
                break;
            }
        }

        marketvalue = (TextView)findViewById(R.id.team_market_value);
        teamName = (TextView) findViewById(R.id.team_name_grid);
        teamCurrentPosition = (TextView) findViewById(R.id.team_current_position);
        team_pj = (TextView) findViewById(R.id.team_pj);
        team_gf = (TextView) findViewById(R.id.team_gf);
        team_ga = (TextView) findViewById(R.id.team_ga);
        team_dif = (TextView) findViewById(R.id.team_dif);
        team_pts = (TextView) findViewById(R.id.team_pts);
        playersLinearLayout = (LinearLayout) findViewById(R.id.teamPlayersLinearLayout);


        marketvalue.setText(mTeam.getSquadMarketValue());
        teamName.setText(mTeam.getName());
        teamCurrentPosition.setText(String.valueOf(mStandingTeam.getPosition()));
        team_pj.setText(String.valueOf(mStandingTeam.getPlayedGames()));
        team_gf.setText(String.valueOf(mStandingTeam.getGoals()));
        team_ga.setText(String.valueOf(mStandingTeam.getGoalsAgainst()));
        team_dif.setText(String.valueOf(mStandingTeam.getGoalDifference()));
        team_pts.setText(String.valueOf(mStandingTeam.getPoints()));


        new RestClient(getApplicationContext()).getmFootballDataService().getTeamPlayers(teamID, new Callback<Players>() {
            @Override
            public void success(Players players, Response response) {
                for(Player p : players.getPlayerList()){
                    PlayerListItemView item = new PlayerListItemView(getApplicationContext(),String.valueOf(p.getJerseyNumber()), p.getName(), p.getPosition(), p.getNationality());
                    playersLinearLayout.addView(item);
                }

            }

            @Override
            public void failure(RetrofitError error) {
                // TODO: Handle error
            }
        });

    }
}
