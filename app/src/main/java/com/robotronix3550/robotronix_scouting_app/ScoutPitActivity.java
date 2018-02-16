package com.robotronix3550.robotronix_scouting_app;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.robotronix3550.robotronix_scouting_app.data.ScoutContract;

import static com.robotronix3550.robotronix_scouting_app.CreateMatchActivity.PREFS_SCOUTER;

public class ScoutPitActivity extends AppCompatActivity {

    private EditText mRobotEditText;

    private ToggleButton mAutoLineTogglebutton;
    private ToggleButton mAutoSwitchTogglebutton;
    private ToggleButton mAutoScaleTogglebutton;
    private ToggleButton mAutoPickTogglebutton;
    private ToggleButton mTeleExchangeTogglebutton;
    private ToggleButton mTeleSwitchTogglebutton;
    private ToggleButton mTeleScaleTogglebutton;
    private ToggleButton mTelePickTogglebutton;
    private ToggleButton mTelePortalTogglebutton;
    private ToggleButton mTeleClimbTogglebutton;
    private ToggleButton mTeleHelpClimbTogglebutton;

    private SharedPreferences mPrefs;

    String mScouter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scout_pit);

        mPrefs = getSharedPreferences(PREFS_SCOUTER, MODE_PRIVATE );
        mScouter = mPrefs.getString("PREF_SCOUTER", "Prenom");

        mRobotEditText = (EditText) findViewById(R.id.RobotEditText);

        mAutoLineTogglebutton = (ToggleButton) findViewById(R.id.AutoLineToggleButton);
        mAutoSwitchTogglebutton = (ToggleButton) findViewById(R.id.AutoSwitchToggleButton);
        mAutoScaleTogglebutton = (ToggleButton) findViewById(R.id.AutoScaleToggleButton);
        mAutoPickTogglebutton = (ToggleButton) findViewById(R.id.AutoPickToggleButton);
        mTeleExchangeTogglebutton = (ToggleButton) findViewById(R.id.TeleExchangeToggleButton);
        mTeleSwitchTogglebutton = (ToggleButton) findViewById(R.id.TeleSwitchToggleButton);
        mTeleScaleTogglebutton = (ToggleButton) findViewById(R.id.TeleScaleToggleButton);
        mTelePickTogglebutton = (ToggleButton) findViewById(R.id.TelePickToggleButton);
        mTelePortalTogglebutton = (ToggleButton) findViewById(R.id.TelePortalToggleButton);
        mTeleClimbTogglebutton = (ToggleButton) findViewById(R.id.TeleClimbToggleButton);
        mTeleHelpClimbTogglebutton = (ToggleButton) findViewById(R.id.TeleHelpToggleButton);

    }

    public void savePit(View view) {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space

        Integer auto_line;
        Integer auto_pick;
        Integer auto_scale;
        Integer auto_switch;

        Integer tele_exchange;
        Integer tele_switch;
        Integer tele_scale;
        Integer tele_pick;
        Integer tele_portal;
        Integer tele_climb;
        Integer tele_help;

        if(mAutoLineTogglebutton.isChecked())
            auto_line = 1;
        else
            auto_line = 0;

        if(mAutoPickTogglebutton.isChecked())
            auto_pick = 1;
        else
            auto_pick = 0;

        if(mAutoScaleTogglebutton.isChecked())
            auto_scale= 1;
        else
            auto_scale = 0;

        if(mAutoSwitchTogglebutton.isChecked())
            auto_switch = 1;
        else
            auto_switch = 0;


        if(mTeleExchangeTogglebutton.isChecked())
            tele_exchange = 1;
        else
            tele_exchange = 0;

        if(mTeleSwitchTogglebutton.isChecked())
            tele_switch = 1;
        else
            tele_switch = 0;

        if(mTeleScaleTogglebutton.isChecked())
            tele_scale = 1;
        else
            tele_scale = 0;

        if(mTelePickTogglebutton.isChecked())
            tele_pick = 1;
        else
            tele_pick = 0;

        if(mTelePortalTogglebutton.isChecked())
            tele_portal = 1;
        else
            tele_portal = 0;

        if(mTeleClimbTogglebutton.isChecked())
            tele_climb = 1;
        else
            tele_climb = 0;

        if(mTeleHelpClimbTogglebutton.isChecked())
            tele_help = 1;
        else
            tele_help = 0;


        String robotString = mRobotEditText.getText().toString().trim();
        Integer robot;

        if (robotString.equals("")) {

            Toast.makeText(this, getString(R.string.missing_robot_failed),
                    Toast.LENGTH_LONG).show();

        } else {


            robot = Integer.parseInt(robotString);

            // Create a ContentValues object where column names are the keys,
            // and scout attributes from the editor are the values.
            ContentValues values = new ContentValues();
            values.put(ScoutContract.ScoutEntry.COLUMN_SCOUT_SCOUTER, mScouter);
            values.put(ScoutContract.ScoutEntry.COLUMN_SCOUT_MATCH, 0);
            values.put(ScoutContract.ScoutEntry.COLUMN_SCOUT_ROBOT, robot);

            values.put(ScoutContract.ScoutEntry.COLUMN_SCOUT_AUTO_LINE, auto_line);
            values.put(ScoutContract.ScoutEntry.COLUMN_SCOUT_AUTO_CUBE, auto_pick);
            values.put(ScoutContract.ScoutEntry.COLUMN_SCOUT_AUTO_SCALE, auto_scale);
            values.put(ScoutContract.ScoutEntry.COLUMN_SCOUT_AUTO_SWITCH, auto_switch);

            values.put(ScoutContract.ScoutEntry.COLUMN_SCOUT_TELE_EXCHANGE, tele_exchange);
            values.put(ScoutContract.ScoutEntry.COLUMN_SCOUT_TELE_ALLY_SWITCH, tele_switch);
            values.put(ScoutContract.ScoutEntry.COLUMN_SCOUT_TELE_ENEMY_SWITCH, tele_switch);
            values.put(ScoutContract.ScoutEntry.COLUMN_SCOUT_TELE_SCALE, tele_scale);

            values.put(ScoutContract.ScoutEntry.COLUMN_SCOUT_TELE_CUBE, tele_pick);
            values.put(ScoutContract.ScoutEntry.COLUMN_SCOUT_TELE_PORTAL, tele_portal);
            values.put(ScoutContract.ScoutEntry.COLUMN_SCOUT_TELE_CLIMB, tele_climb);
            values.put(ScoutContract.ScoutEntry.COLUMN_SCOUT_TELE_HELP_CLIMB, tele_help);
            values.put(ScoutContract.ScoutEntry.COLUMN_SCOUT_TELE_PARK, 0);
            values.put(ScoutContract.ScoutEntry.COLUMN_SCOUT_TELE_BROKEN, 0);


            values.put(ScoutContract.ScoutEntry.COLUMN_SCOUT_GAME_ALLY_SCORE, 0);
            values.put(ScoutContract.ScoutEntry.COLUMN_SCOUT_GAME_ENEMY_SCORE, 0);


            // Insert a new scout into the provider, returning the content URI for the new scout.
            Uri newUri = getContentResolver().insert(ScoutContract.ScoutEntry.CONTENT_URI, values);

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
            startActivity(intent);
        }
    }


}
