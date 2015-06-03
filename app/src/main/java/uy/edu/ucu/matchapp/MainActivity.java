package uy.edu.ucu.matchapp;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import org.parceler.Parcels;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import uy.edu.ucu.matchapp.models.Fixture;
import uy.edu.ucu.matchapp.models.Fixtures;
import uy.edu.ucu.matchapp.models.SoccerSeason;
import uy.edu.ucu.matchapp.network.RestClient;
import uy.edu.ucu.matchapp.views.adapters.FixturesAdapter;

public class MainActivity extends ListActivity {

    private ArrayList<SoccerSeason> soccerSeasonList;
    private ArrayList<Fixture> fixtureList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Spinner spnSoccerSeasons = (Spinner) findViewById(R.id.soccer_seasons);

        // Fetch soccer seasons
        new RestClient(this).getFootballDataService().getSoccerSeasons(new Callback<ArrayList<SoccerSeason>>() {
            @Override
            public void success(ArrayList<SoccerSeason> soccerSeasons, Response response) {
                soccerSeasonList = soccerSeasons;
                spnSoccerSeasons.setAdapter(new ArrayAdapter<SoccerSeason>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, soccerSeasonList));
            }

            @Override
            public void failure(RetrofitError error) {
                // TODO: Handle error
            }
        });

        // Fetch fixtures
        new RestClient(this).getFootballDataService().getFixtures(null, null, new Callback<Fixtures>() {
            @Override
            public void success(Fixtures fixtures, Response response) {
                fixtureList = fixtures.getFixtureList();

                for (final Fixture fixture : fixtureList) {
                    // Fetch fixture's soccer season
                    String soccerSeasonUrl = fixture.getLinks().get("soccerseason").get("href");
                    int soccerSeasonId = Integer.parseInt(soccerSeasonUrl.substring(soccerSeasonUrl.lastIndexOf('/') + 1));
                    new RestClient(getApplicationContext()).getFootballDataService().getSoccerSeason(soccerSeasonId, new Callback<SoccerSeason>() {
                        @Override
                        public void success(SoccerSeason soccerSeason, Response response) {
                            fixture.setSoccerSeason(soccerSeason);
                            ((FixturesAdapter) getListAdapter()).notifyDataSetChanged();
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            // TODO: Handle error
                        }
                    });

                    // Fetch home team
//                    String homeTeamUrl = fixture.getLinks().get("homeTeam").get("href");
//                    int homeTeamId = Integer.parseInt(homeTeamUrl.substring(homeTeamUrl.lastIndexOf('/') + 1));
//                    new RestClient().getFootballDataService().getTeam(homeTeamId, new Callback<Team>() {
//                        @Override
//                        public void success(Team team, Response response) {
//                            fixture.setHomeTeam(team);
//                            ((FixturesAdapter) getListAdapter()).notifyDataSetChanged();
//                        }
//
//                        @Override
//                        public void failure(RetrofitError error) {
//
//                        }
//                    });

                    // Fetch away team
//                    String awayTeamUrl = fixture.getLinks().get("awayTeam").get("href");
//                    int awayTeamId = Integer.parseInt(awayTeamUrl.substring(awayTeamUrl.lastIndexOf('/') + 1));
//                    new RestClient().getFootballDataService().getTeam(awayTeamId, new Callback<Team>() {
//                        @Override
//                        public void success(Team team, Response response) {
//                            fixture.setAwayTeam(team);
//                            ((FixturesAdapter) getListAdapter()).notifyDataSetChanged();
//                        }
//
//                        @Override
//                        public void failure(RetrofitError error) {
//
//                        }
//                    });
                }

                setListAdapter(new FixturesAdapter(getApplicationContext(), fixtureList));
            }

            @Override
            public void failure(RetrofitError error) {
                // TODO: Handle error
            }
        });
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Fixture fixture = (Fixture) getListAdapter().getItem(position);

        Intent intent = new Intent(getApplicationContext(), FixtureActivity.class);
        intent.putExtra("FIXTURE", Parcels.wrap(fixture));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}
