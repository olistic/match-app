package uy.edu.ucu.matchapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.parceler.Parcels;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import uy.edu.ucu.matchapp.models.Fixture;
import uy.edu.ucu.matchapp.models.FixtureDetail;
import uy.edu.ucu.matchapp.network.RestClient;


public class FixtureActivity extends Activity {

    private FixtureDetail mFixtureDetail;
    private TextView mMatchDayTextView;
    private TextView mStatusTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixture);

        // Look up view for data population
        mMatchDayTextView = (TextView) findViewById(R.id.match_day);
        mStatusTextView = (TextView) findViewById(R.id.status);

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

                // Populate the data into the template view using the data object
                mMatchDayTextView.setText(String.format("Match day %d", mFixtureDetail.getFixture().getMatchDay()));
                mStatusTextView.setText(mFixtureDetail.getFixture().getStatus());
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
