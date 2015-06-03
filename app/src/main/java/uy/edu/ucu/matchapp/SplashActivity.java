package uy.edu.ucu.matchapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;


public class SplashActivity extends Activity {

    // TODO: Use splash screen to load resources
    private static final int SPLASH_DURATION = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start MainActivity
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);

                // Close splash screen
                finish();
            }
        }, SPLASH_DURATION);
    }
}
