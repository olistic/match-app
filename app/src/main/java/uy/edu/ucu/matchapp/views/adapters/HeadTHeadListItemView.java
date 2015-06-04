package uy.edu.ucu.matchapp.views.adapters;


import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.Date;

import uy.edu.ucu.matchapp.R;

public class HeadTHeadListItemView extends FrameLayout {

    public HeadTHeadListItemView(Context context, Date date, String HomeTeamInfo, String AwayTeamInfo) {
        super(context);
        View.inflate(context, R.layout.headtoheaditem, this);

        ((TextView)findViewById(R.id.lblHeadToHeadDate)).setText(date.toString());
        ((TextView)findViewById(R.id.lblHeadToHeadTeam1)).setText(HomeTeamInfo);
        ((TextView)findViewById(R.id.lblHeadToHeadTeam2)).setText(AwayTeamInfo);

    }
}
