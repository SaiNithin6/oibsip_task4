package com.example.stopwatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private int seconds = 0;
    private boolean running;
    private boolean wasrunning;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState!=null) {
            savedInstanceState.getInt("seconds");
            savedInstanceState.getBoolean("running");
            savedInstanceState.getBoolean("wasrunning");
        }
        runTimer();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("seconds",seconds);
        outState.putBoolean("running",running);
        outState.putBoolean("wasrunning",wasrunning);
    }

    private void runTimer() {
        TextView timer = findViewById(R.id.textView);
        Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds/3600;
                int minutes= (seconds % 3600) /60;
                int secs = seconds%60;

                String time = String.format(Locale.getDefault(),"%02d:%02d:%02d",hours,minutes,secs);

                timer.setText(time);

                if(running) {
                    seconds++;
                }
                handler.postDelayed(this, 0);
            }
        });
    }

    public void onStop(View view) {
        running = false;
    }

    public void onStart(View view) {
        running = true;
    }

    public void onRestart(View view) {
        running=false;
        seconds = 0;
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasrunning = running;
        running=false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(wasrunning) {
            running=true;
        }
    }
}