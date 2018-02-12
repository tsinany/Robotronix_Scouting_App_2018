package com.robotronix3550.robotronix_scouting_app;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.robotronix3550.robotronix_scouting_app.data.ScoutContract;

public class CreateSettingActivity extends AppCompatActivity {

    /** EditText field to enter the event name */
    private EditText mEventEditText;

    /** EditText field to enter tablet name */
    private EditText mTabletNameEditText;

    /** EditText field to enter the directory */
    private EditText mPathEditText;

    private String mFileName;

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
        // Find all relevant views that we will need to read user input from
        mEventEditText = (EditText) findViewById(R.id.eventNameTextEdit);
        mTabletNameEditText = (EditText) findViewById(R.id.tabletNameEditText);

        mPathEditText = (EditText) findViewById(R.id.dirEditText);
        mPathEditText.setText("/Storage/Documents/scouting");

        TextView fileNameTextView = findViewById(R.id.fileNameTextView);
        mFileName =  "scout_" + mEventEditText.getText().toString().trim() +
                    "_" + mTabletNameEditText.getText().toString().trim() + ".csv";
        fileNameTextView.setText(mFileName);

    }

    public void exportDB(View view) {


        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String eventString = mEventEditText.getText().toString().trim();
        String tabletString = mTabletNameEditText.getText().toString().trim();
        String pathString = mPathEditText.getText().toString().trim();

        mFileName =  "scout_" + mEventEditText.getText().toString().trim() +
                "_" + mTabletNameEditText.getText().toString().trim() + ".csv";

        // Insert a new scout into the provider, returning the content URI for the new scout.
        Bundle bu = getContentResolver().call(ScoutContract.ScoutEntry.CONTENT_URI, "exportDB", mFileName, null);
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
