package uy.edu.ucu.matchapp.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import uy.edu.ucu.matchapp.R;
import uy.edu.ucu.matchapp.models.Fixture;

public class FixturesAdapter extends ArrayAdapter<Fixture> {
    public FixturesAdapter(Context context, ArrayList<Fixture> fixtures) {
        super(context, 0, fixtures);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the fixture for this position
        Fixture fixture = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_main_item, parent, false);
        }

        // Lookup view for data population
        ImageView ivHomeTeam = (ImageView) convertView.findViewById(R.id.home_team_image);
        ImageView ivAwayTeam = (ImageView) convertView.findViewById(R.id.away_team_image);
        TextView tvTeams = (TextView) convertView.findViewById(R.id.teams);
        TextView tvSoccerSeason = (TextView) convertView.findViewById(R.id.soccer_season);
        TextView tvDate = (TextView) convertView.findViewById(R.id.date);

        // Populate the data into the template view using the data object
        tvTeams.setText(String.format("%s vs. %s", fixture.getHomeTeamName(), fixture.getAwayTeamName()));
        tvDate.setText(fixture.getDate().toString());
        if (fixture.getSoccerSeason() != null) {
            tvSoccerSeason.setText(fixture.getSoccerSeason().getCaption());
        }
//        if (fixture.getHomeTeam() != null) {
//            Picasso.with(getContext()).load(fixture.getHomeTeam().getImageUrl()).into(ivHomeTeam);
//        }
//        if (fixture.getAwayTeam() != null) {
//            Picasso.with(getContext()).load(fixture.getAwayTeam().getImageUrl()).into(ivAwayTeam);
//        }

        // Return the completed view to render on screen
        return convertView;
    }
}
