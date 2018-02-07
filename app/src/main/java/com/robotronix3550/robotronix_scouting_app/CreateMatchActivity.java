package com.robotronix3550.robotronix_scouting_app;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.robotronix3550.robotronix_scouting_app.data.ScoutContract.ScoutEntry;

public class CreateMatchActivity extends AppCompatActivity {

    /** EditText field to enter the scout's scouter name */
    private EditText mNameEditText;

    /** EditText field to enter the scout's match */
    private EditText mMatchEditText;

    /** EditText field to enter the scout's robot */
    private EditText mRobotEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_match);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = "Scouter un Match";

        // Capture the layout's TextView and set the string as its text
        TextView titleTextView = findViewById(R.id.titleMatchTextView);
        titleTextView.setText(message);

        // Find all relevant views that we will need to read user input from
        mNameEditText = (EditText) findViewById(R.id.scoutNameEditText);
        mMatchEditText = (EditText) findViewById(R.id.matchNoEditText);
        mRobotEditText = (EditText) findViewById(R.id.robotNoEditText);

    }


    /**
     * Get user input from editor and save new scout into database.
     */
    public void insertScout(View view) {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String nameString = mNameEditText.getText().toString().trim();
        String matchString = mMatchEditText.getText().toString().trim();
        String robotString = mRobotEditText.getText().toString().trim();
        int match = Integer.parseInt(matchString);
        int robot = Integer.parseInt(robotString);

        // Create a ContentValues object where column names are the keys,
        // and scout attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(ScoutEntry.COLUMN_SCOUT_SCOUTER, nameString);
        values.put(ScoutEntry.COLUMN_SCOUT_MATCH, match);
        values.put(ScoutEntry.COLUMN_SCOUT_ROBOT, robot);

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
    }

}
