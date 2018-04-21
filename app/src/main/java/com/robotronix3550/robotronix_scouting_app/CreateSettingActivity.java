package com.robotronix3550.robotronix_scouting_app;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.robotronix3550.robotronix_scouting_app.data.ScoutContract;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class CreateSettingActivity extends AppCompatActivity {

    /** EditText field to enter the event name */
    private EditText mEventEditText;

    /** EditText field to enter tablet name */
    private EditText mTabletNameEditText;

    /** EditText field to enter the directory */
    private TextView mPathEditText;

    private String mFileName;

    private String mEvent;

    private String mTablet;

    private SharedPreferences mPrefs;

    private MediaScannerConnection mConnection;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_setting);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();

        mPrefs = getPreferences(MODE_PRIVATE);
        // mPrefs = getSharedPreferences(PREFS_SCOUTER, MODE_PRIVATE);
        mTablet = mPrefs.getString("PREF_TABLET", "tab1");
        mEvent = mPrefs.getString("PREF_EVENT", "det");

        String message = "Paramètres";

        // Capture the layout's TextView and set the string as its text
        TextView titleTextView = findViewById(R.id.titleSettingTextView);
        titleTextView.setText(message);

        // Find all relevant views that we will need to read user input from
        mEventEditText = (EditText) findViewById(R.id.eventNameTextEdit);
        mEventEditText.setText(mEvent);

        mTabletNameEditText = (EditText) findViewById(R.id.tabletNameEditText);
        mTabletNameEditText.setText(mTablet);

        mPathEditText = (TextView) findViewById(R.id.dirEditText);
        mPathEditText.setText("/Storage/Documents/scouting");

        TextView fileNameTextView = findViewById(R.id.fileNameTextView);

        String DefaultFileName =  "scout_" + mEventEditText.getText().toString().trim() +
                "_" + mTabletNameEditText.getText().toString().trim() + ".csv";

        mFileName = mPrefs.getString("PREF_FILENAME", DefaultFileName);

        fileNameTextView.setText(mFileName);

    }

    public void exportDB(View view) {

        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        mEvent = mEventEditText.getText().toString().trim();
        mTablet = mTabletNameEditText.getText().toString().trim();

        if( mEvent.equals("")) {

            Toast.makeText(this, getString(R.string.missing_event_failed),
                    Toast.LENGTH_LONG).show();

        } else if ( mTablet.equals("")) {

            Toast.makeText(this, getString(R.string.missing_tablet_failed),
                    Toast.LENGTH_LONG).show();

        } else {

            /* Create unique file as media rescan doesn't work well with same file name
            *  that potentially copy-paste the previous .csv file and not the latest updated one
            *
            * */
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-hh-mm");
            String format = simpleDateFormat.format(new Date());

            mFileName = "scout_" + mEvent + "_" + mTablet + "_" + format + ".csv";

            // Insert a new scout into the provider, returning the content URI for the new scout.
            Bundle bu = getContentResolver().call(ScoutContract.ScoutEntry.CONTENT_URI, "exportDB", mFileName, null);

            // Show a toast message depending on whether or not the insertion was successful
            if (bu == null) {
                // If the new content URI is null, then there was an error with insertion.
                Toast.makeText(this, getString(R.string.exportDB_scout_failed),
                        Toast.LENGTH_LONG).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.exportDB_scout_successful),
                        Toast.LENGTH_LONG).show();

                /* Media Scan doesn't work */
                /*
                String ScanPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath().toString();
                String ScanPathFile = (ScanPath + "/" + mFileName);

                ArrayList<String> toBeScanned = new ArrayList<String>();
                // toBeScanned.add("/Storage/Documents/scouting");
                toBeScanned.add(ScanPath);
                toBeScanned.add(ScanPathFile);

                String[] toBeScannedStr = new String[toBeScanned.size()];
                toBeScannedStr = toBeScanned.toArray(toBeScannedStr);

                // String toBeScannedStr =
                MediaScannerConnection.scanFile(getBaseContext(), toBeScannedStr, null, new MediaScannerConnection.OnScanCompletedListener() {

                    @Override
                    public void onScanCompleted(String path, Uri uri) {
                        System.out.println("SCAN COMPLETED: " + path);
                        //Toast.makeText(this, getString(R.string.exportDB_scout_successful),
                        Toast.makeText(getBaseContext(), ("SCAN COMPLETED: " + path),
                                Toast.LENGTH_LONG).show();

                    }
                });

                mConnection.connect();

                mConnection.scanFile(ScanPathFile, null);
                mConnection.scanFile(ScanPath, null);

                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + ScanPathFile)));
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + ScanPath)));
                */

            }

            SharedPreferences.Editor ed = mPrefs.edit();
            ed.putString("PREF_TABLET", mTablet);
            ed.putString("PREF_EVENT", mEvent);
            ed.putString("PREF_FILENAME", mFileName);
            ed.commit();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public void onBackPressed(){

        mTablet = mTabletNameEditText.getText().toString().trim();
        mEvent = mEventEditText.getText().toString().trim();

        SharedPreferences.Editor ed = mPrefs.edit();
        ed.putString("PREF_TABLET", mTablet);
        ed.putString("PREF_EVENT", mEvent);
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
