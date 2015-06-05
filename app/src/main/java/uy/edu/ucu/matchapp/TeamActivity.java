package uy.edu.ucu.matchapp;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.larvalabs.svgandroid.SVG;
import com.larvalabs.svgandroid.SVGBuilder;
import com.larvalabs.svgandroid.SVGParser;

import org.parceler.Parcels;

import java.net.HttpURLConnection;
import java.net.URL;

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
    private ImageView team_image;

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
        team_image = (ImageView) findViewById(R.id.team_image);

        marketvalue.setText(mTeam.getSquadMarketValue());
        teamName.setText(mTeam.getName());
        teamCurrentPosition.setText(String.valueOf(mStandingTeam.getPosition()));
        team_pj.setText(String.valueOf(mStandingTeam.getPlayedGames()));
        team_gf.setText(String.valueOf(mStandingTeam.getGoals()));
        team_ga.setText(String.valueOf(mStandingTeam.getGoalsAgainst()));
        team_dif.setText(String.valueOf(mStandingTeam.getGoalDifference()));
        team_pts.setText(String.valueOf(mStandingTeam.getPoints()));

        if(mTeam.getImageUrl() != null) {
            new AsyncTask<String, Void, Drawable>() {

                @Override
                protected Drawable doInBackground(String... params) {

                    String url = params[0];
                    HttpURLConnection connection = null;
                    Drawable drawable = null;
                    try {
                        connection = (HttpURLConnection) new URL(url).openConnection();
                        SVG svgLogo = new SVGBuilder().readFromInputStream(connection.getInputStream()).build();
                        drawable = svgLogo.getDrawable();

                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (connection != null) connection.disconnect();
                    }

                    // si no tengo drawable disponible cargo un placeholder (por ej. el icono de la app)
                    if (drawable == null) {
                        drawable = getResources().getDrawable(R.mipmap.ic_launcher);
                    }
                    return drawable;
                }

                @Override
                public void onPostExecute(Drawable logoDrawable) {

                    // setLayerType fue introducido en la Version HONEYCOMB (API 11)
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
                        team_image.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                        team_image.setImageDrawable(logoDrawable);
                    } else {
                        // si no pudeo setear el layerType (Version < HONEYCOMB), uso el placeholder u oculto el imageview
                        team_image.setVisibility(View.GONE);
                    }

                }
            }.execute(mTeam.getImageUrl());
        }


        new RestClient(getApplicationContext()).getFootballDataService().getTeamPlayers(teamID, new Callback<Players>() {
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
