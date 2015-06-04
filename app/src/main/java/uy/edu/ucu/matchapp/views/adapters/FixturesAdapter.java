package uy.edu.ucu.matchapp.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import uy.edu.ucu.matchapp.R;
import uy.edu.ucu.matchapp.models.Fixture;

public class FixturesAdapter extends ArrayAdapter<Fixture> implements Filterable {
    private List<Fixture> mFixtureList;
    private List<Fixture> mFilteredFixtureList;
    private Filter mFilter;

    public FixturesAdapter(Context context, ArrayList<Fixture> fixtureList) {
        super(context, 0, fixtureList);
        this.mFixtureList = fixtureList;
        this.mFilteredFixtureList = fixtureList;
    }

    @Override
    public int getCount() {
        return mFilteredFixtureList.size();
    }

    @Override
    public Fixture getItem(int position) {
        return mFilteredFixtureList.get(position);
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
        TextView teamsTextView = (TextView) convertView.findViewById(R.id.teams);
        TextView dateTextView = (TextView) convertView.findViewById(R.id.date);
        TextView soccerSeasonTextView = (TextView) convertView.findViewById(R.id.soccer_season);

        // Populate the data into the template view using the data object
        teamsTextView.setText(String.format("%s vs. %s", fixture.getHomeTeamName(), fixture.getAwayTeamName()));
        dateTextView.setText(fixture.getDate().toString());
        if (fixture.getSoccerSeason() != null) {
            soccerSeasonTextView.setText(fixture.getSoccerSeason().getCaption());
        }

        // Return the completed view to render on screen
        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults results = new FilterResults();
                    ArrayList<Fixture> filteredFixtures = new ArrayList<>();

                    if (constraint == null || constraint.length() == 0) {
                        results.count = mFixtureList.size();
                        results.values = mFixtureList;
                    } else {
                        constraint = constraint.toString();
                        for (int index = 0; index < mFixtureList.size(); index++) {
                            Fixture fixture = mFixtureList.get(index);

                            System.out.println(fixture.getSoccerSeason().getCaption());
                            if (fixture.getSoccerSeason().getCaption().equals(constraint)) {
                                filteredFixtures.add(fixture);
                            }
                        }

                        results.count = filteredFixtures.size();
                        results.values = filteredFixtures;
                    }

                    return results;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    mFilteredFixtureList = (ArrayList<Fixture>) results.values;
                    notifyDataSetChanged();
                }
            };
        }

        return mFilter;
    }
}
