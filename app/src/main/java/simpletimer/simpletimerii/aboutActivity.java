package simpletimer.simpletimerii;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class aboutActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SimTim_SETTING";
    public static final String KEY_MESSAGE = "SimTim.MES";
    public static final String MESSAGE_KEY = "SimTim.MSG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String value = getIntent().getStringExtra(KEY_MESSAGE);
        View saveBut = findViewById(R.id.saveBut);
        saveBut.setOnClickListener(this);
        Log.d(TAG,"on create: received: "+ value);
    }

    public void onClick(View v){
        long roundValue=0L;
        long roundRestTime =0L;
        long workOtime=0L;
        long workOrestTime=0L;
        long alterOtime=0L;
        long alterOrestTime=0L;

        EditText roundCount = (EditText) findViewById(R.id.roundText);
        String rc = roundCount.getText().toString();
        roundValue = Integer.parseInt(rc);
        Log.d(TAG,"round cound: "+ roundValue);

        EditText roundGap = (EditText) findViewById(R.id.roundGap);
        rc = roundGap.getText().toString();
        roundRestTime = Integer.parseInt(rc);
        Log.d(TAG,"round Gap: "+ roundRestTime);

        EditText workTime = (EditText) findViewById(R.id.workTime);
        rc = workTime.getText().toString();
        workOtime = Integer.parseInt(rc);
        Log.d(TAG,"Exec time: "+ workOtime);

        EditText workGap  = (EditText) findViewById(R.id.workGap);
        rc = workGap.getText().toString();
        workOrestTime = Integer.parseInt(rc);
        Log.d(TAG,"rest time: "+ workOrestTime);

        EditText alterWorkTime = (EditText) findViewById(R.id.alterWorkTime);
        rc = alterWorkTime.getText().toString();
        alterOtime = Integer.parseInt(rc);
        Log.d(TAG,"change time: "+ alterOtime);

        EditText alterWorkGap = (EditText) findViewById(R.id.alterWorkGap);
        rc = alterWorkGap.getText().toString();
        alterOrestTime = Integer.parseInt(rc);
        Log.d(TAG,"change rest time: "+ alterOrestTime);

        rc = ""+ roundValue + "," + roundRestTime +"," +workOtime+","+workOrestTime+","+alterOtime+","+alterOrestTime+", 0";
        Log.d(TAG,"Sending setting change: "+ rc );
        Intent intent = new Intent();
        intent.putExtra(MESSAGE_KEY, rc); //value should be your string from the edittext
        setResult(2, intent); //The data you want to send back
       finish();
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



}
