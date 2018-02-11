package com.robotronix3550.robotronix_scouting_app;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.robotronix3550.robotronix_scouting_app.data.ScoutContract;
import com.robotronix3550.robotronix_scouting_app.data.ScoutDBHelper;
import com.robotronix3550.robotronix_scouting_app.data.SqliteExporter;

public class CreateSettingActivity extends AppCompatActivity {

    /** Database helper object */
    //private ScoutDBHelper mDbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_setting);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = "Paramètres";

        // Capture the layout's TextView and set the string as its text
        TextView titleTextView = findViewById(R.id.titleSettingTextView);
        titleTextView.setText(message);

        // mDbHelper = new ScoutDBHelper(getContext());


    }

    public void exportDB(View view) {

        /*
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
        values.put(ScoutContract.ScoutEntry.COLUMN_SCOUT_SCOUTER, nameString);
        values.put(ScoutContract.ScoutEntry.COLUMN_SCOUT_MATCH, match);
        values.put(ScoutContract.ScoutEntry.COLUMN_SCOUT_ROBOT, robot);
        */
        // Insert a new scout into the provider, returning the content URI for the new scout.
        Bundle bu = getContentResolver().call(ScoutContract.ScoutEntry.CONTENT_URI, "exportDB", null, null);
                //getContentResolver().insert(ScoutContract.ScoutEntry.CONTENT_URI, values);

        // Show a toast message depending on whether or not the insertion was successful
        if (bu == null) {
            // If the new content URI is null, then there was an error with insertion.
            Toast.makeText(this, getString(R.string.exportDB_scout_failed),
                    Toast.LENGTH_LONG).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast.
            Toast.makeText(this, getString(R.string.exportDB_scout_successful),
                    Toast.LENGTH_LONG).show();
        }



    }
}
