package com.robotronix3550.robotronix_scouting_app;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.robotronix3550.robotronix_scouting_app.data.ScoutContract;

public class CreateSettingActivity extends AppCompatActivity {

    public static final String EXTRA_TABLET = "com.robotronix3550.robotronix_scouting_app.TABLET";

    /** EditText field to enter the event name */
    private EditText mEventEditText;

    /** EditText field to enter tablet name */
    private EditText mTabletNameEditText;

    /** EditText field to enter the directory */
    private EditText mPathEditText;

    private String mFileName;

    private String mTablet;

    private SharedPreferences mPrefs;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_setting);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();

        mPrefs = getPreferences(MODE_PRIVATE);
        mTablet = mPrefs.getString("PREF_TABLET", "tab1");

        //mTablet = intent.getStringExtra(EXTRA_TABLET);
        //if (mTablet == null) mTablet = "tab1";

        String message = "Paramètres";

        // Capture the layout's TextView and set the string as its text
        TextView titleTextView = findViewById(R.id.titleSettingTextView);
        titleTextView.setText(message);

        // mDbHelper = new ScoutDBHelper(getContext());
        // Find all relevant views that we will need to read user input from
        mEventEditText = (EditText) findViewById(R.id.eventNameTextEdit);
        mTabletNameEditText = (EditText) findViewById(R.id.tabletNameEditText);
        mTabletNameEditText.setText(mTablet);

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
        mTablet = mTabletNameEditText.getText().toString().trim();
        String pathString = mPathEditText.getText().toString().trim();

        mFileName =  "scout_" + eventString + "_" + mTablet + ".csv";

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

        Intent intent = new Intent(this, MainActivity.class);
        //intent.putExtra(EXTRA_TABLET, mTablet);

        SharedPreferences.Editor ed = mPrefs.edit();
        //mTablet = mTabletNameEditText.getText().toString().trim();
        ed.putString("PREF_TABLET", mTablet);
        ed.commit();

        startActivity(intent);


    }

    @Override
    public void onBackPressed(){

        //Intent intent = new Intent(this, MainActivity.class);
        //intent.putExtra(EXTRA_TABLET, mTablet);

        SharedPreferences.Editor ed = mPrefs.edit();
        mTablet = mTabletNameEditText.getText().toString().trim();
        ed.putString("PREF_TABLET", mTablet);
        ed.commit();
        Toast.makeText(this, "saving preferences " + mTablet,
                Toast.LENGTH_LONG).show();

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
