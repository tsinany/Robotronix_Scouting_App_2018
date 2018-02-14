package com.robotronix3550.robotronix_scouting_app;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.robotronix3550.robotronix_scouting_app.data.ScoutContract.ScoutEntry;

public class ScoutMatchActivity extends AppCompatActivity {

    Integer mCubeExchangeCnt;
    Integer mCubeAllySwitchCnt;
    Integer mCubeEnemySwitchCnt;
    Integer mCubeScaleCnt;
    Integer mRobot;
    Integer mMatch;
    String mScouter;

    /** EditText field to enter the scout's match */
    private EditText mMatchEditText;

    /** EditText field to enter the scout's robot */
    private EditText mRobotEditText;

    private TextView mCubeExchangeText;

    private TextView mCubeAllySwitchText;

    private TextView mCubeEnemySwitchText;

    private TextView mCubeScaleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scout_match);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        mScouter = intent.getStringExtra(CreateMatchActivity.EXTRA_SCOUTER);
        mMatch = intent.getIntExtra(CreateMatchActivity.EXTRA_MATCH, 0);
        mRobot = intent.getIntExtra(CreateMatchActivity.EXTRA_ROBOT, 0);

        // Capture the layout's TextView and set the string as its text
        mMatchEditText = findViewById(R.id.MatchEditText);
        mMatchEditText.setText(mMatch.toString());

        mRobotEditText = findViewById(R.id.RobotEditText);
        mRobotEditText.setText(mRobot.toString());

        mCubeExchangeCnt = 0;
        mCubeAllySwitchCnt = 0;
        mCubeEnemySwitchCnt = 0;
        mCubeScaleCnt = 0;

        mCubeExchangeText = (TextView) findViewById(R.id.ExchangeCntTextView);
        mCubeExchangeText.setText(mCubeExchangeCnt.toString());

        mCubeAllySwitchText = (TextView) findViewById(R.id.AllySwitchTextView);
        mCubeAllySwitchText.setText(mCubeAllySwitchCnt.toString());

        mCubeEnemySwitchText = (TextView) findViewById(R.id.EnemySwitchCntTextView);
        mCubeEnemySwitchText.setText(mCubeEnemySwitchCnt.toString());

        mCubeScaleText = (TextView) findViewById(R.id.ScaleCntTextView);
        mCubeScaleText.setText(mCubeScaleCnt.toString());

    }

    public void incCubeExchangeCnt(View view) {
        mCubeExchangeCnt++;
        TextView cubeExchangeText = (TextView) findViewById(R.id.ExchangeCntTextView);
        cubeExchangeText.setText(mCubeExchangeCnt.toString());
    }

    public void decCubeExchangeCnt(View view) {

        if(mCubeExchangeCnt > 0) {
            mCubeExchangeCnt--;
        }
        TextView cubeExchangeText = (TextView) findViewById(R.id.ExchangeCntTextView);
        cubeExchangeText.setText(mCubeExchangeCnt.toString());
    }

    public void incCubeAllySwitchCnt(View view) {
        mCubeAllySwitchCnt++;
        TextView cubeAllySwitchText = (TextView) findViewById(R.id.AllySwitchTextView);
        cubeAllySwitchText.setText(mCubeAllySwitchCnt.toString());
    }

    public void decCubeAllySwitchCnt(View view) {
        if(mCubeAllySwitchCnt > 0) {
            mCubeAllySwitchCnt--;
        }
        TextView cubeAllySwitchText = (TextView) findViewById(R.id.AllySwitchTextView);
        cubeAllySwitchText.setText(mCubeAllySwitchCnt.toString());
    }

    public void incCubeEnemySwitchCnt(View view) {
        mCubeEnemySwitchCnt++;
        TextView cubeEnemySwitchText = (TextView) findViewById(R.id.EnemySwitchCntTextView);
        cubeEnemySwitchText.setText(mCubeEnemySwitchCnt.toString());
    }

    public void decCubeEnemySwitchCnt(View view) {
        if(mCubeEnemySwitchCnt > 0) {
            mCubeEnemySwitchCnt--;
        }
        TextView cubeEnemySwitchText = (TextView) findViewById(R.id.EnemySwitchCntTextView);
        cubeEnemySwitchText.setText(mCubeEnemySwitchCnt.toString());
    }

    public void incCubeScaleCnt(View view) {
        mCubeScaleCnt++;
        TextView cubeScaleText = (TextView) findViewById(R.id.ScaleCntTextView);
        cubeScaleText.setText(mCubeScaleCnt.toString());
    }

    public void decCubeScaleCnt(View view) {
        if(mCubeScaleCnt > 0) {
            mCubeScaleCnt--;
        }
        TextView cubeScaleText = (TextView) findViewById(R.id.ScaleCntTextView);
        cubeScaleText.setText(mCubeScaleCnt.toString());
    }


    public void saveMatch(View view) {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space


        String matchString = mMatchEditText.getText().toString().trim();
        String robotString = mRobotEditText.getText().toString().trim();

        mMatch = Integer.parseInt(matchString);
        mRobot = Integer.parseInt(robotString);

        /*
        togglebutton = (ToggleButton) findViewById(R.id.togglebutton);
    }

    public void toggleclick(View v){
        if(togglebutton.isChecked())
            Toast.makeText(TestActivity.this, "ON", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(TestActivity.this, "OFF", Toast.LENGTH_SHORT).show();
    }
    */

        // Create a ContentValues object where column names are the keys,
        // and scout attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(ScoutEntry.COLUMN_SCOUT_SCOUTER, mScouter);
        values.put(ScoutEntry.COLUMN_SCOUT_MATCH, mMatch);
        values.put(ScoutEntry.COLUMN_SCOUT_ROBOT, mRobot);

        values.put(ScoutEntry.COLUMN_SCOUT_TELE_EXCHANGE, mCubeExchangeCnt );
        values.put(ScoutEntry.COLUMN_SCOUT_TELE_ALLY_SWITCH, mCubeAllySwitchCnt );
        values.put(ScoutEntry.COLUMN_SCOUT_TELE_SCALE, mCubeScaleCnt );
        values.put(ScoutEntry.COLUMN_SCOUT_TELE_ENEMY_SWITCH, mCubeEnemySwitchCnt );

        // Insert a new scout into the provider, returning the content URI for the new scout.
        Uri newUri = getContentResolver().insert(ScoutEntry.CONTENT_URI, values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newUri == null) {
            // If the new content URI is null, then there was an error with insertion.
            Toast.makeText(this, getString(R.string.editor_insert_scout_failed),
                    Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast.
            Toast.makeText(this, getString(R.string.editor_insert_scout_successful),
                    Toast.LENGTH_SHORT).show();
        }



        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(CreateMatchActivity.EXTRA_SCOUTER, mScouter);

        startActivity(intent);

    }

}
