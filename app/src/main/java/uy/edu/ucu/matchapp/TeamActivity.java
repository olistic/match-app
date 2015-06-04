package uy.edu.ucu.matchapp;

import android.app.Activity;
import android.os.Bundle;

import org.parceler.Parcels;

import uy.edu.ucu.matchapp.models.Team;


public class TeamActivity extends Activity {

    private Team mTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        // Get mTeam from intent
        mTeam = Parcels.unwrap(this.getIntent().getParcelableExtra("TEAM"));

        // Set Action Bar title to mTeam's name
        if (mTeam.getName() != null) {
            getActionBar().setTitle(mTeam.getName());
        }
    }
}
