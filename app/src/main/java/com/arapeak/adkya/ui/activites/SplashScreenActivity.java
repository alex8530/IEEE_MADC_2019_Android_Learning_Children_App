package com.arapeak.adkya.ui.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.arapeak.adkya.R;

public class SplashScreenActivity extends AppCompatActivity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 1000;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash_screen);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashScreenActivity.this,StartActivity.class);
                startActivity(mainIntent);
                 finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
