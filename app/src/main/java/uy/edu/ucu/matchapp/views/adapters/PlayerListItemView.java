package uy.edu.ucu.matchapp.views.adapters;


import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;


import uy.edu.ucu.matchapp.R;

public class PlayerListItemView extends FrameLayout {

    public PlayerListItemView(Context context, String playerNro, String playerName, String playerPosition, String playerCountry) {
        super(context);
        View.inflate(context, R.layout.playeritem, this);

        ((TextView)findViewById(R.id.playerNro)).setText(playerNro);
        ((TextView)findViewById(R.id.playerName)).setText(playerName);
        ((TextView)findViewById(R.id.playerPosition)).setText(playerPosition);
        ((TextView)findViewById(R.id.playerCountry)).setText(playerCountry);


    }
}
