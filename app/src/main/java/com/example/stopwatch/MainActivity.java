package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;

public class MainActivity extends AppCompatActivity {
    private int seconds = 0;
    private boolean running;
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int themeColor = ContextCompat.getColor(this, R.color.transparent);
        MaterialButtonToggleGroup toggleGroup = findViewById(R.id.toggleGroup);
        MaterialButton startStopButton = findViewById(R.id.start_stop);
        toggleGroup.setBackgroundColor(themeColor);

        // Set the background color for the start_stop button
        startStopButton.setBackgroundColor(themeColor);
        toggleGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                onToggleStartStop(startStopButton);
            }
        });
    }

    public void onToggleStartStop(View view) {
        MaterialButtonToggleGroup toggleGroup = findViewById(R.id.toggleGroup);
        MaterialButton startStopButton = findViewById(R.id.start_stop);

        if (startStopButton != null) {
            running = !running;

            if (running) {
                startStopButton.setIconResource(R.drawable.img_1);
                startStopButton.setBackgroundColor(ContextCompat.getColor(this, R.color.theme));
                toggleGroup.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    if (!handler.hasCallbacks(timerRunnable)) {
                        handler.post(timerRunnable);
                    }
                }
            } else {
                startStopButton.setIconResource(R.drawable.img);
                startStopButton.setBackgroundColor(ContextCompat.getColor(this, R.color.theme));
                toggleGroup.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
                // Stop the timer
                handler.removeCallbacks(timerRunnable);
            }
        }
    }

    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            seconds++;
            updateTimerText();
            handler.postDelayed(this, 1000);
        }
    };

    public void onreset(View view) {
        running = false;
        seconds = 0;
        updateTimerText();
        // Stop the timer when resetting
        handler.removeCallbacks(timerRunnable);
    }

    private void updateTimerText() {
        TextView timer = findViewById(R.id.timer);
        int hrs = seconds / 3600;
        int mins = (seconds % 3600) / 60;
        int sec = seconds % 60;
        String time = String.format("%02d:%02d:%02d", hrs, mins, sec);
        timer.setText(time);
    }
}

