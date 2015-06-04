package uy.edu.ucu.matchapp.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import uy.edu.ucu.matchapp.R;
import uy.edu.ucu.matchapp.models.StandingTeam;

public class StandingTeamsAdapter extends ArrayAdapter<StandingTeam> {
    public StandingTeamsAdapter(Context context, ArrayList<StandingTeam> standingTeams) {
        super(context, 0, standingTeams);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the fixture for this position
        StandingTeam standingTeam = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_soccer_season_item, parent, false);
        }

        // Lookup view for data population


        // Populate the data into the template view using the data object


        // Return the completed view to render on screen
        return convertView;
    }
}
