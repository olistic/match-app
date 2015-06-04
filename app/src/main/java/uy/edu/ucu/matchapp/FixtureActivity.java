package uy.edu.ucu.matchapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.parceler.Parcels;
import org.w3c.dom.Text;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import uy.edu.ucu.matchapp.models.Fixture;
import uy.edu.ucu.matchapp.models.FixtureDetail;
import uy.edu.ucu.matchapp.models.LeagueTable;
import uy.edu.ucu.matchapp.models.Team;
import uy.edu.ucu.matchapp.network.RestClient;
import uy.edu.ucu.matchapp.views.adapters.HeadTHeadListItemView;


public class FixtureActivity extends Activity {

    private FixtureDetail mFixtureDetail;
    private LeagueTable mLeagueTable;
    private TextView mMatchDayTextView;
    private TextView mStatusTextView;
    private TextView mDateTextView;
    private TextView mHomeTeamTextView;
    private TextView mAwayTeamTextView;
    private TextView mTeam1Wins;
    private TextView mTeam2Wins;
    private TextView mDraws;

    private LinearLayout headToheadLL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixture);

        // Look up view for data population
        mMatchDayTextView = (TextView) findViewById(R.id.fixtureMatchday);
        mStatusTextView = (TextView) findViewById(R.id.fixtureStatus);
        mDateTextView = (TextView) findViewById(R.id.fixtureDate);
        mHomeTeamTextView = (TextView) findViewById(R.id.fixtureHomeTeam);
        mAwayTeamTextView = (TextView) findViewById(R.id.fixtureAwayTeam);
        mTeam1Wins = (TextView) findViewById(R.id.lblTeam1Wins);
        mTeam2Wins = (TextView) findViewById(R.id.lblTeam2Wins);
        mDraws = (TextView) findViewById(R.id.lblDraws);

        headToheadLL = (LinearLayout)findViewById(R.id.headToHeadLinearLayout);

        // Get fixture from intent
        Fixture fixture = Parcels.unwrap(this.getIntent().getParcelableExtra("FIXTURE"));

        // Set action bar title to soccer season caption
        if (fixture.getSoccerSeason() != null) {
            setTitle(fixture.getSoccerSeason().getCaption());
        }

        // Fetch fixture detail
        String fixtureUrl = fixture.getLinks().get("self").get("href");
        int fixtureId = Integer.parseInt(fixtureUrl.substring(fixtureUrl.lastIndexOf('/') + 1));
        new RestClient(this).getmFootballDataService().getFixtureDetail(fixtureId, new Callback<FixtureDetail>() {
            @Override
            public void success(FixtureDetail fixtureDetail, Response response) {
                mFixtureDetail = fixtureDetail;

                String soccerSeasonUrl = mFixtureDetail.getFixture().getLinks().get("soccerseason").get("href");
                int soccerSeasonId = Integer.parseInt(soccerSeasonUrl.substring(soccerSeasonUrl.lastIndexOf('/') + 1));
                new RestClient(getApplicationContext()).getmFootballDataService().getSoccerSeasonLeagueTable(soccerSeasonId, new Callback<LeagueTable>() {
                    @Override
                    public void success(LeagueTable leagueTable, Response response) {
                        mLeagueTable = leagueTable;
                    }
                    @Override
                    public void failure(RetrofitError error) {
                        // TODO: Handle error
                    }
                });

                // Populate the data into the template view using the data object
                mMatchDayTextView.setText(String.format("Match day %d", mFixtureDetail.getFixture().getMatchDay()));
                mStatusTextView.setText(mFixtureDetail.getFixture().getStatus());
                mDateTextView.setText(mFixtureDetail.getFixture().getDate().toString());
                mHomeTeamTextView.setText(mFixtureDetail.getFixture().getHomeTeamName());
                mAwayTeamTextView.setText(mFixtureDetail.getFixture().getAwayTeamName());

                String homeTeamUrl = mFixtureDetail.getFixture().getLinks().get("homeTeam").get("href");
                final int homeTeamId = Integer.parseInt(homeTeamUrl.substring(homeTeamUrl.lastIndexOf('/') + 1));
                new RestClient(getApplicationContext()).getmFootballDataService().getTeam(homeTeamId, new Callback<Team>() {
                    @Override
                    public void success(Team team, Response response) {
                        mFixtureDetail.getFixture().setHomeTeam(team);
                        mHomeTeamTextView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getApplicationContext(), TeamActivity.class);
                                intent.putExtra("TEAM", Parcels.wrap(mFixtureDetail.getFixture().getHomeTeam()));
                                intent.putExtra("LEAGUETABLE", Parcels.wrap(mLeagueTable));
                                intent.putExtra("TEAMID", Parcels.wrap(homeTeamId));
                                startActivity(intent);
                            }
                        });

                    }
                    @Override
                    public void failure(RetrofitError error) {
                        // TODO: Handle error
                    }
                });
                String awayTeamUrl = mFixtureDetail.getFixture().getLinks().get("awayTeam").get("href");
                final int awayTeamId = Integer.parseInt(awayTeamUrl.substring(awayTeamUrl.lastIndexOf('/') + 1));
                new RestClient(getApplicationContext()).getmFootballDataService().getTeam(awayTeamId, new Callback<Team>() {
                    @Override
                    public void success(Team team, Response response) {
                        mFixtureDetail.getFixture().setAwayTeam(team);
                        mAwayTeamTextView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getApplicationContext(), TeamActivity.class);
                                intent.putExtra("TEAM", Parcels.wrap(mFixtureDetail.getFixture().getAwayTeam()));
                                intent.putExtra("LEAGUETABLE", Parcels.wrap(mLeagueTable));
                                intent.putExtra("TEAMID", Parcels.wrap(awayTeamId));
                                startActivity(intent);
                            }
                        });
                    }
                    @Override
                    public void failure(RetrofitError error) {
                        // TODO: Handle error
                    }
                });




                mTeam1Wins.setText(mFixtureDetail.getFixture().getHomeTeamName() + mFixtureDetail.getHeadToHead().getHomeTeamWins());
                mTeam2Wins.setText(mFixtureDetail.getFixture().getAwayTeamName() + mFixtureDetail.getHeadToHead().getAwayTeamWins());
                mDraws.setText("Draws " + mFixtureDetail.getHeadToHead().getDraws());

                for(Fixture fixt : mFixtureDetail.getHeadToHead().getFixtureList()){
                    String homeTeamInfo = fixt.getHomeTeamName()+ "  " + fixt.getResult().get("goalsHomeTeam");
                    String awayTeamInfo = fixt.getAwayTeamName()+ "  " + fixt.getResult().get("goalsAwayTeam");
                    HeadTHeadListItemView item = new HeadTHeadListItemView(getApplicationContext(),fixt.getDate(),homeTeamInfo, awayTeamInfo);
                    headToheadLL.addView(item);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                // TODO: Handle error
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fixture, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_share:
                // TODO: Share fixture in social networks
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
