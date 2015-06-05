package uy.edu.ucu.matchapp.views.adapters;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import uy.edu.ucu.matchapp.R;


public class TeamTableListItemView extends FrameLayout {

    public TeamTableListItemView(Context context, int position, String teamName, int pj, int gf, int ga, int dif, int pts) {
        super(context);
        View.inflate(context, R.layout.tableitem, this);

        ((TextView) findViewById(R.id.team_position)).setText(String.valueOf(position));
        ((TextView) findViewById(R.id.team_name)).setText(teamName);
        ((TextView) findViewById(R.id.team_pj)).setText(String.valueOf(pj));
        ((TextView) findViewById(R.id.team_gf)).setText(String.valueOf(gf));
        ((TextView) findViewById(R.id.team_ga)).setText(String.valueOf(ga));
        ((TextView) findViewById(R.id.team_dif)).setText(String.valueOf(dif));
        ((TextView) findViewById(R.id.team_pts)).setText(String.valueOf(pts));



    }
}
