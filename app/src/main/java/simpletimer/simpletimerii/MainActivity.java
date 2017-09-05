package simpletimer.simpletimerii;

import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "SimTim";
    private long startTime = 0L;
    private Handler customHandler = new Handler();
    private TextView timerText;
    private  View pauseBut;
    private TextView  dispText;
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long pauseTime;
    long updatedTime = 0L;
    Boolean pause = true;
    int roundValue=0;
    int roundRestTime =0;
    int stepTime=0;
    int stepRestTime=0;
    int alterTime=0;
    int alterRestTime=0;
    int sub_roundValue=0;
    int prevSec;
    int countDownTimer = 0;
    public static final String KEY_MESSAGE = "SimTim.MES";
    public static final String MESSAGE_KEY = "SimTim.MSG";
public enum enum_state {RELAX_TIME_I , WORK_TIME_I , RELAX_TIME_II , WORK_TIME_II , RELAX_TIME_III }
    enum_state state = enum_state.RELAX_TIME_I;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG , "on create \n");
        timerText = (TextView) findViewById(R.id.timerText);
        //timerText.setOnClickListener(this);

        pauseBut = findViewById(R.id.pauseBut);
        pauseBut.setClickable(false);

        View startBut = findViewById(R.id.startBut);
        pauseBut.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                if(pause == true) {

                    pauseTime = SystemClock.uptimeMillis() ;
                    Log.d(TAG , "PAUSED @ " +pauseTime );
                    customHandler.removeCallbacks(updateTimerThread);
                    pause = false;
                }
                else{

                    timeSwapBuff += SystemClock.uptimeMillis() - pauseTime;
                    Log.d(TAG , "RESUMED  total paused time" + timeSwapBuff);
                    customHandler.postDelayed(updateTimerThread,0);
                    pause = true;
                }

            }
        });

        startBut.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.d(TAG,"STARTED");
                startTime =SystemClock.uptimeMillis();
                pauseBut.setClickable(true);
                timeInMilliseconds  = 0;
                sub_roundValue      = 0;
                timeSwapBuff = 0L;
                countDownTimer = roundRestTime;
                customHandler.postDelayed(updateTimerThread,0);
            }
        });

        View stopBut =findViewById(R.id.stopBut);
        stopBut.setOnClickListener( new View.OnClickListener(){
            public void onClick(View v){
                customHandler.removeCallbacks(updateTimerThread);
                Log.d(TAG,"STOPPED  ");
                timerText.setText(""+String.format("%02d",0)+":"
                        + String.format("%02d",0) +":"
                        + String.format("%03d",0));
                timeInMilliseconds = 0L;
                timeSwapBuff = 0L;
                updatedTime = 0L;

                pauseBut.setClickable(false);
            }

        });


        View setBut =findViewById(R.id.setBut);
        setBut.setOnClickListener(this);
        dispText = (TextView) findViewById(R.id.dispText);

        Intent setting = new Intent(this, aboutActivity.class);
        setting.putExtra(KEY_MESSAGE,"new_setting");
        startActivityForResult(setting, 0);
    }
    public void onClick(View v){
        int reqCode= 0;
        Log.d(TAG,"Setting has to popup");
        Intent setting = new Intent(this, aboutActivity.class);
        setting.putExtra(KEY_MESSAGE,"new_setting");
        startActivityForResult(setting, reqCode);
    }
    //In your class
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Retrieve data in the intent
        int i=0;
        Log.d(TAG,"Received setting change: "+requestCode+" & "+resultCode );
        String editTextValue = data.getStringExtra(MESSAGE_KEY);
        Log.d(TAG,"Received setting change: "+ editTextValue );
        StringTokenizer tokens = new StringTokenizer(editTextValue, ",");

        while(tokens.hasMoreTokens()){
            i++;
            String element = tokens.nextToken();
            switch (i){
                case 1:
                    roundValue = Integer.parseInt(element); break;
                case 2:
                    roundRestTime = Integer.parseInt(element); break;
                case 3:
                    stepTime = Integer.parseInt(element); break;
                case 4:
                    stepRestTime = Integer.parseInt(element); break;
                case 5:
                    alterTime = Integer.parseInt(element); break;
                case 6:
                    alterRestTime =Integer.parseInt(element); break;
                default:  //no need to go further
                    sub_roundValue      = 0;
                    countDownTimer = roundRestTime;
                    state = enum_state.RELAX_TIME_I;
                    return;
            }
        }

        sub_roundValue      = 0;
        countDownTimer = roundRestTime;
        state = enum_state.RELAX_TIME_I;
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "The onStart() event");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "The onResume() event");
    }
    /** Called when another activity is taking focus. */
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "The onPause() event");
    }

    /** Called when the activity is no longer visible. */
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "The onStop() event");
    }

    /** Called just before the activity is destroyed. */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "The onDestroy() event");
    }
    private Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime ;
            updatedTime = timeInMilliseconds - timeSwapBuff;
            int secs = (int) (updatedTime / 1000);
            int minx = secs / 60;
            secs = secs % 60;
            if(secs != prevSec){
                prevSec = secs;
                if(countDownTimer == 0){
                    Log.e(TAG, "count down not valid settings ... change the setting\n");
                }
                else {
                    countDownTimer = countDownTimer - 1;
                    if(countDownTimer == 0){
                        if(state == enum_state.RELAX_TIME_I){
                            state = enum_state.WORK_TIME_I;
                            countDownTimer = stepTime;
                        }else if(state == enum_state.WORK_TIME_I){
                            state = enum_state.RELAX_TIME_II;
                            countDownTimer = stepRestTime;
                        }else if(state == enum_state.RELAX_TIME_II){
                            state = enum_state.WORK_TIME_II;
                            countDownTimer = alterTime;
                        }else if(state == enum_state.WORK_TIME_II){
                            state = enum_state.RELAX_TIME_III;
                            countDownTimer = alterRestTime;
                        }else if(state == enum_state.RELAX_TIME_III){
                            state= enum_state.RELAX_TIME_I;
                            sub_roundValue = sub_roundValue + 1;
                            countDownTimer = roundRestTime;
                        }
                    }
                }
            }

            int milliseconds = (int) (updatedTime % 1000);
            timerText.setText(""+String.format("%02d",minx)+":"
                    + String.format("%02d",secs) +":"
                    + String.format("%03d",milliseconds));
            dispText.setText(state.toString() + " : " +sub_roundValue +" :"+countDownTimer);
            customHandler.postDelayed(this,0);
        }
    };
}
