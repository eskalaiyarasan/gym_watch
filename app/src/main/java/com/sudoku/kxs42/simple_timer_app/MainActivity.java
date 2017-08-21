package com.sudoku.kxs42.simple_timer_app;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    private Button startButton;
    private Button pauseButton;

    private TextView timerValue;
    private TextView timerCount;
    private long startTime = 0L;

    private Handler customHandler = new Handler();
    private int countTwenty ;
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    int prevTwenty = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerValue = (TextView) findViewById(R.id.timerValue);
        timerCount = (TextView) findViewById(R.id.timerCount);
        startButton = (Button) findViewById(R.id.startButton);

        startButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                startTime = SystemClock.uptimeMillis();
                customHandler.postDelayed(updateTimerThread, 0);

            }
        });

        pauseButton = (Button) findViewById(R.id.pauseButton);

        pauseButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                timeSwapBuff += timeInMilliseconds;
                customHandler.removeCallbacks(updateTimerThread);

            }
        });

    }

    private Runnable updateTimerThread = new Runnable() {

        public void run() {

            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;

            //updatedTime = timeSwapBuff + timeInMilliseconds;
            updatedTime  =  timeInMilliseconds;
            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            if(prevTwenty != secs)
                if(0 == secs % 20) {
                    countTwenty++;
                    prevTwenty = secs;
                }
            int milliseconds = (int) (updatedTime % 1000);
            timerValue.setText("" + mins + ":"
                    + String.format("%02d", secs) + ":"
                    + String.format("%03d", milliseconds));
            timerCount.setText(""+countTwenty);
            if(countTwenty == 5) {
                countTwenty = 0;
                customHandler.removeCallbacks(this);
            }
            else
                customHandler.postDelayed(this, 0);
        }

    };

}