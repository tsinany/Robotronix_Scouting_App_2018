package com.robotronix3550.robotronix_scouting_app;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
//import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class CreateMatchActivity extends AppCompatActivity {

    /** EditText field to enter the scout's scouter name */
    private EditText mNameEditText;

    /** EditText field to enter the scout's match */
    private EditText mMatchEditText;

    /** EditText field to enter the scout's robot */
    private EditText mRobotEditText;

    String mScouter;

    private SharedPreferences mPrefs;
    public static final String PREFS_SCOUTER = "MyPrefScouterFile";


    public static final String EXTRA_SCOUTER = "com.robotronix3550.robotronix_scouting_app.SCOUTER";

    public static final String EXTRA_MATCH = "com.robotronix3550.robotronix_scouting_app.MATCH";

    public static final String EXTRA_ROBOT = "com.robotronix3550.robotronix_scouting_app.ROBOT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_match);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        //mScouter = intent.getStringExtra(EXTRA_SCOUTER);
        //if(mScouter==null) mScouter = "Name";
        mPrefs = getSharedPreferences(PREFS_SCOUTER, MODE_PRIVATE );
        mScouter = mPrefs.getString("PREF_SCOUTER", "Prenom");


        String message = "Scouter un Match";

        // Capture the layout's TextView and set the string as its text
        TextView titleTextView = findViewById(R.id.titleMatchTextView);
        titleTextView.setText(message);

        // Find all relevant views that we will need to read user input from
        mNameEditText = (EditText) findViewById(R.id.scoutNameEditText);
        mMatchEditText = (EditText) findViewById(R.id.matchNoEditText);
        mRobotEditText = (EditText) findViewById(R.id.robotNoEditText);

        mNameEditText.setText(mScouter);

    }


    /**
     * Get user input from editor and save new scout into database.
     */
    public void createMatch(View view) {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        mScouter = mNameEditText.getText().toString().trim();
        String matchString = mMatchEditText.getText().toString().trim();
        String robotString = mRobotEditText.getText().toString().trim();
        int match = Integer.parseInt(matchString);
        int robot = Integer.parseInt(robotString);

        SharedPreferences.Editor ed = mPrefs.edit();
        ed.putString("PREF_SCOUTER", mScouter);
        ed.commit();

        Intent intent = new Intent(this, ScoutMatchActivity.class);
        //intent.putExtra(EXTRA_SCOUTER, mScouter);
        intent.putExtra(EXTRA_MATCH, match);
        intent.putExtra(EXTRA_ROBOT, robot);

        startActivity(intent);

    }

    @Override
    public void onBackPressed(){

        // Intent intent = new Intent(this, MainActivity.class);
        // mScouter = mNameEditText.getText().toString().trim();
        // intent.putExtra(EXTRA_SCOUTER, mScouter);

        SharedPreferences.Editor ed = mPrefs.edit();
        mScouter = mNameEditText.getText().toString().trim();
        ed.putString("PREF_SCOUTER", mScouter);
        ed.commit();

        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }

}
