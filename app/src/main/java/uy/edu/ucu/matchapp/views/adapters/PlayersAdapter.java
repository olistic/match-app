package uy.edu.ucu.matchapp.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import uy.edu.ucu.matchapp.R;
import uy.edu.ucu.matchapp.models.Player;

public class PlayersAdapter extends ArrayAdapter<Player> {
    private List<Player> mPlayerList;

    public PlayersAdapter(Context context, ArrayList<Player> playerList) {
        super(context, 0, playerList);
        this.mPlayerList = playerList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the fixture for this position
        Player player = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_team_player_item, parent, false);
        }

        // Lookup view for data population
        TextView jerseyNoTextView = (TextView) convertView.findViewById(R.id.jersey_no);
        TextView nameTextView = (TextView) convertView.findViewById(R.id.name);
        TextView positionTextView = (TextView) convertView.findViewById(R.id.position);
        TextView nationalityTextView = (TextView) convertView.findViewById(R.id.nationality);

        // Populate the data into the template view using the data object
        jerseyNoTextView.setText(String.valueOf(player.getJerseyNumber()));
        nameTextView.setText(player.getName());
        positionTextView.setText(player.getPosition());
        nationalityTextView.setText(player.getNationality());

        // Return the completed view to render on screen
        return convertView;
    }
}
