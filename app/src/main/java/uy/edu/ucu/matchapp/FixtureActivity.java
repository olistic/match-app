package uy.edu.ucu.matchapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.parceler.Parcels;

import java.text.SimpleDateFormat;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import uy.edu.ucu.matchapp.models.Fixture;
import uy.edu.ucu.matchapp.models.FixtureDetail;
import uy.edu.ucu.matchapp.models.LeagueTable;
import uy.edu.ucu.matchapp.models.Team;
import uy.edu.ucu.matchapp.network.RestClient;
import uy.edu.ucu.matchapp.views.adapters.HeadToHeadFixturesAdapter;

public class FixtureActivity extends Activity {

    private FixtureDetail mFixtureDetail;
    private LeagueTable mLeagueTable;
    private TextView mMatchDayTextView;
    private TextView mStatusTextView;
    private TextView mDateTextView;
    private TextView mTimeTextView;
    private TextView mHomeTeamTextView;
    private TextView mAwayTeamTextView;
    private TextView mHomeTeamWinsTextView;
    private TextView mAwayTeamWinsTextView;
    private TextView mDrawsTextView;
    private ListView mHeadToHeadListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixture);

        // Look up view for data population
        mMatchDayTextView = (TextView) findViewById(R.id.fixture_match_day);
        mStatusTextView = (TextView) findViewById(R.id.fixture_status);
        mDateTextView = (TextView) findViewById(R.id.fixture_date);
        mTimeTextView = (TextView) findViewById(R.id.fixture_time);
        mHomeTeamTextView = (TextView) findViewById(R.id.fixture_home_team);
        mAwayTeamTextView = (TextView) findViewById(R.id.fixture_away_team);
        mHomeTeamWinsTextView = (TextView) findViewById(R.id.home_team_wins);
        mAwayTeamWinsTextView = (TextView) findViewById(R.id.away_team_wins);
        mDrawsTextView = (TextView) findViewById(R.id.draws);
        mHeadToHeadListView = (ListView) findViewById(R.id.head_to_head_fixtures);

        // Get fixture from intent
        Fixture fixture = Parcels.unwrap(this.getIntent().getParcelableExtra("FIXTURE"));

        // Set action bar title to soccer season caption
        if (fixture.getSoccerSeason() != null) {
            setTitle(fixture.getSoccerSeason().getCaption());
        }

        // Fetch fixture detail
        String fixtureUrl = fixture.getLinks().get("self").get("href");
        int fixtureId = Integer.parseInt(fixtureUrl.substring(fixtureUrl.lastIndexOf('/') + 1));
        new RestClient(this).getFootballDataService().getFixtureDetail(fixtureId, new Callback<FixtureDetail>() {
            @Override
            public void success(FixtureDetail fixtureDetail, Response response) {
                mFixtureDetail = fixtureDetail;

                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat tf = new SimpleDateFormat("hh:mm a");

                // Populate the data into the template view using the data object
                mMatchDayTextView.setText(String.format("Match day: %d", mFixtureDetail.getFixture().getMatchDay()));
                mStatusTextView.setText(mFixtureDetail.getFixture().getStatus());
                mDateTextView.setText(df.format(mFixtureDetail.getFixture().getDate()));
                mTimeTextView.setText(tf.format(mFixtureDetail.getFixture().getDate()));
                mHomeTeamTextView.setText(mFixtureDetail.getFixture().getHomeTeamName());
                mAwayTeamTextView.setText(mFixtureDetail.getFixture().getAwayTeamName());

                mHomeTeamWinsTextView.setText(String.format("%s: %d",
                        mFixtureDetail.getFixture().getHomeTeamName(),
                        mFixtureDetail.getHeadToHead().getHomeTeamWins()));

                mAwayTeamWinsTextView.setText(String.format("%s: %d",
                        mFixtureDetail.getFixture().getAwayTeamName(),
                        mFixtureDetail.getHeadToHead().getAwayTeamWins()));

                mDrawsTextView.setText(String.format("Draws: %s",
                        mFixtureDetail.getHeadToHead().getDraws()));

                mHeadToHeadListView.setAdapter(new HeadToHeadFixturesAdapter(getApplicationContext(), mFixtureDetail.getHeadToHead().getFixtureList()));

                // Fetch home team
                String homeTeamUrl = mFixtureDetail.getFixture().getLinks().get("homeTeam").get("href");
                final int homeTeamId = Integer.parseInt(homeTeamUrl.substring(homeTeamUrl.lastIndexOf('/') + 1));
                new RestClient(getApplicationContext()).getFootballDataService().getTeam(homeTeamId, new Callback<Team>() {
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

                // Fetch away team
                String awayTeamUrl = mFixtureDetail.getFixture().getLinks().get("awayTeam").get("href");
                final int awayTeamId = Integer.parseInt(awayTeamUrl.substring(awayTeamUrl.lastIndexOf('/') + 1));
                new RestClient(getApplicationContext()).getFootballDataService().getTeam(awayTeamId, new Callback<Team>() {
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

                // Fetch league table
                String soccerSeasonUrl = mFixtureDetail.getFixture().getLinks().get("soccerseason").get("href");
                int soccerSeasonId = Integer.parseInt(soccerSeasonUrl.substring(soccerSeasonUrl.lastIndexOf('/') + 1));
                new RestClient(getApplicationContext()).getFootballDataService().getSoccerSeasonLeagueTable(soccerSeasonId, new Callback<LeagueTable>() {
                    @Override
                    public void success(LeagueTable leagueTable, Response response) {
                        mLeagueTable = leagueTable;
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        // TODO: Handle error
                    }
                });
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

        // Return true to display menu
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
                if (mFixtureDetail != null) {
                    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                    SimpleDateFormat tf = new SimpleDateFormat("hh:mm a");

                    // Build share text
                    StringBuilder sb = new StringBuilder();
                    if (mFixtureDetail.getFixture().getStatus().equals("FINISHED")) {
                        sb.append(String.format("%s (%d) vs. %s (%d) ",
                                mFixtureDetail.getFixture().getHomeTeamName(),
                                mFixtureDetail.getFixture().getResult().get("goalsHomeTeam"),
                                mFixtureDetail.getFixture().getAwayTeamName(),
                                mFixtureDetail.getFixture().getResult().get("goalsAwayTeam")));
                    } else {
                        sb.append(String.format("%s vs. %s ",
                                mFixtureDetail.getFixture().getHomeTeamName(),
                                mFixtureDetail.getFixture().getAwayTeamName()));
                    }
                    sb.append(String.format("(%s @ %s) ", df.format(mFixtureDetail.getFixture().getDate()),
                            tf.format(mFixtureDetail.getFixture().getDate())));
                    sb.append("\n");
                    sb.append("https://play.google.com/store/apps?hl=es");

                    // Create share intent
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, sb.toString());
                    sendIntent.setType("text/plain");
                    startActivity(Intent.createChooser(sendIntent, "Share fixture to..."));
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
