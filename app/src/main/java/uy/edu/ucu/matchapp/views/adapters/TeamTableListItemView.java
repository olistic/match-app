package uy.edu.ucu.matchapp.views.adapters;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import uy.edu.ucu.matchapp.R;

/**
 * Created by Diego on 04/06/2015.
 */
public class TeamTableListItemView extends FrameLayout {

    public TeamTableListItemView(Context context, String position, String teamName, String pj, String gf, String ga, String dif, String pts) {
        super(context);
        View.inflate(context, R.layout.playeritem, this);

    }
}
