package uy.edu.ucu.matchapp.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import uy.edu.ucu.matchapp.R;
import uy.edu.ucu.matchapp.models.Fixture;

public class HeadToHeadFixturesAdapter extends ArrayAdapter<Fixture> {
    private List<Fixture> mFixtureList;

    public HeadToHeadFixturesAdapter(Context context, ArrayList<Fixture> fixtureList) {
        super(context, 0, fixtureList);
        this.mFixtureList = fixtureList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the fixture for this position
        Fixture fixture = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_fixture_item, parent, false);
        }

        // Lookup view for data population
        TextView dateTextView = (TextView) convertView.findViewById(R.id.date);
        TextView homeTeamGoals = (TextView) convertView.findViewById(R.id.home_team_result);
        TextView awayTeamGoals = (TextView) convertView.findViewById(R.id.away_team_result);

        // Populate the data into the template view using the data object
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        dateTextView.setText(df.format(fixture.getDate()));
        homeTeamGoals.setText(String.format("%s: %d", fixture.getHomeTeamName(), fixture.getResult().get("goalsHomeTeam")));
        awayTeamGoals.setText(String.format("%s: %d", fixture.getAwayTeamName(), fixture.getResult().get("goalsAwayTeam")));

        // Return the completed view to render on screen
        return convertView;
    }
}
