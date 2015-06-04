package uy.edu.ucu.matchapp;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
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

    private FixturesAdapter mFixturesAdapter;
    private ArrayAdapter<SoccerSeason> mSoccerSeasonAdapter;
    private Spinner mSoccerSeasonSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFixturesAdapter = new FixturesAdapter(getApplicationContext(), new ArrayList<Fixture>());
        setListAdapter(mFixturesAdapter);

        // Fetch fixtures
        // TODO: Fetch only today's fixtures
        new RestClient(this).getmFootballDataService().getFixtures(null, null, new Callback<Fixtures>() {
            @Override
            public void success(Fixtures fixtures, Response response) {
                for (final Fixture fixture : fixtures.getFixtureList()) {
                    // Get soccer season id from URL
                    String soccerSeasonUrl = fixture.getLinks().get("soccerseason").get("href");
                    int soccerSeasonId = Integer.parseInt(soccerSeasonUrl.substring(soccerSeasonUrl.lastIndexOf('/') + 1));

                    // Fetch fixture's soccer season
                    new RestClient(getApplicationContext()).getmFootballDataService().getSoccerSeason(soccerSeasonId, new Callback<SoccerSeason>() {
                        @Override
                        public void success(SoccerSeason soccerSeason, Response response) {
                            fixture.setSoccerSeason(soccerSeason);
                            mFixturesAdapter.add(fixture);
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            // TODO: Handle error
                        }
                    });
                }


            }

            @Override
            public void failure(RetrofitError error) {
                // TODO: Handle error
            }
        });

        mSoccerSeasonSpinner = (Spinner) findViewById(R.id.soccer_seasons);
        mSoccerSeasonAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, new ArrayList<SoccerSeason>());
        mSoccerSeasonSpinner.setAdapter(mSoccerSeasonAdapter);

        // Create fake soccer season
        SoccerSeason fakeSoccerSeason = new SoccerSeason();
        fakeSoccerSeason.setCaption("All soccer seasons");
        mSoccerSeasonAdapter.add(fakeSoccerSeason);

        // Fetch soccer seasons
        new RestClient(this).getmFootballDataService().getSoccerSeasons(new Callback<ArrayList<SoccerSeason>>() {
            @Override
            public void success(ArrayList<SoccerSeason> soccerSeasons, Response response) {
                mSoccerSeasonAdapter.addAll(soccerSeasons);
            }

            @Override
            public void failure(RetrofitError error) {
                // TODO: Handle error
            }
        });

        mSoccerSeasonSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Cause the action bar to redraw
                invalidateOptionsMenu();

                String constraint = null;
                if (position != 0) {
                    // Set filter constraint to selected soccer season caption
                    SoccerSeason selectedSoccerSeason = mSoccerSeasonAdapter.getItem(position);
                    constraint = selectedSoccerSeason.getCaption();
                }

                mFixturesAdapter.getFilter().filter(constraint);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If All soccer seasons is selected, hide action bar icon
        if (mSoccerSeasonSpinner.getSelectedItemPosition() == 0) {
            menu.getItem(0).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
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

        switch (id) {
            case R.id.action_league_table:
                SoccerSeason soccerSeason = (SoccerSeason) mSoccerSeasonSpinner.getSelectedItem();

                Intent intent = new Intent(getApplicationContext(), LeagueTableActivity.class);
                intent.putExtra("SOCCER_SEASON", Parcels.wrap(soccerSeason));
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
