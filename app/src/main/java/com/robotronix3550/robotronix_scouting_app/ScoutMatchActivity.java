package com.robotronix3550.robotronix_scouting_app;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.robotronix3550.robotronix_scouting_app.data.ScoutContract;
import com.robotronix3550.robotronix_scouting_app.data.ScoutContract.ScoutEntry;
import com.robotronix3550.robotronix_scouting_app.data.ScoutInfo;

import static com.robotronix3550.robotronix_scouting_app.CreateMatchActivity.PREFS_SCOUTER;

public class ScoutMatchActivity extends AppCompatActivity {

    public static final String TAG = ScoutMatchActivity.class.getSimpleName();

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


    private ToggleButton mLineTogglebutton;
    private ToggleButton mPickTogglebutton;
    private ToggleButton mSwitchTogglebutton;
    private ToggleButton mScaleTogglebutton;
    private ToggleButton mHelpTogglebutton;
    private ToggleButton mBrokenTogglebutton;
    private ToggleButton mClimbTogglebutton;
    private ToggleButton mParkTogglebutton;


    private SharedPreferences mPrefs;

    Uri mCurrentScoutUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scout_match);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        mCurrentScoutUri = intent.getData();

        Integer db_id = intent.getIntExtra(ReviewMatchActivity.EXTRA_DB_ID, -1);

        if( mCurrentScoutUri == null) {
            mMatch = intent.getIntExtra(CreateMatchActivity.EXTRA_MATCH, 0);
            mRobot = intent.getIntExtra(CreateMatchActivity.EXTRA_ROBOT, 0);

            // mPrefs = getPreferences(MODE_PRIVATE);
            mPrefs = getSharedPreferences(PREFS_SCOUTER, MODE_PRIVATE);
            mScouter = mPrefs.getString("PREF_SCOUTER", "Prenom");

            mCubeExchangeCnt = 0;
            mCubeAllySwitchCnt = 0;
            mCubeEnemySwitchCnt = 0;
            mCubeScaleCnt = 0;

        } else {

            String[] column = null;  // return all columns
            String selection = null; // db_id.toString(); // return all rows
            String[] selectionArgs = null; // not used
            String sortOrder = null;  // unordered
            Cursor cursor = null;

            try {

                cursor = getContentResolver().query(mCurrentScoutUri, column, selection, selectionArgs, sortOrder);

                String debug = DatabaseUtils.dumpCursorToString(cursor);
                Log.d(TAG, debug);

                // Find the columns of pet attributes that we're interested in
                int matchColumnIndex = cursor.getColumnIndex(ScoutEntry.COLUMN_SCOUT_MATCH);
                int robotColumnIndex = cursor.getColumnIndex(ScoutEntry.COLUMN_SCOUT_ROBOT);
                /*
                int count = cursor.getCount();
                int pos = cursor.getPosition();
                boolean isAfterLast = cursor.isAfterLast();
                boolean isBeforeFirst = cursor.isBeforeFirst();
                boolean isFirst = cursor.isFirst();
                boolean isLast = cursor.isLast();
                */
                cursor.moveToFirst();
                boolean isFirst = cursor.isFirst();

                // db_id = cursor.getInt(0);
                mMatch = cursor.getInt(matchColumnIndex);
                mRobot = cursor.getInt(robotColumnIndex);

                mCubeExchangeCnt = 0;
                mCubeAllySwitchCnt = 0;
                mCubeEnemySwitchCnt = 0;
                mCubeScaleCnt = 0;


            }
            catch(Exception throwable){
                    Log.e(TAG, "Could not get data from cursor", throwable);
            }
            finally {
                if(cursor!=null)
                    cursor.close();
            }
        }

        // Capture the layout's TextView and set the string as its text
        mMatchEditText = findViewById(R.id.MatchEditText);
        mMatchEditText.setText(mMatch.toString());

        mRobotEditText = findViewById(R.id.RobotEditText);
        mRobotEditText.setText(mRobot.toString());


        mCubeExchangeText = (TextView) findViewById(R.id.ExchangeCntTextView);
        mCubeExchangeText.setText(mCubeExchangeCnt.toString());

        mCubeAllySwitchText = (TextView) findViewById(R.id.AllySwitchTextView);
        mCubeAllySwitchText.setText(mCubeAllySwitchCnt.toString());

        mCubeEnemySwitchText = (TextView) findViewById(R.id.EnemySwitchCntTextView);
        mCubeEnemySwitchText.setText(mCubeEnemySwitchCnt.toString());

        mCubeScaleText = (TextView) findViewById(R.id.ScaleCntTextView);
        mCubeScaleText.setText(mCubeScaleCnt.toString());


        mLineTogglebutton = (ToggleButton) findViewById(R.id.LineToggleButton);
        mPickTogglebutton = (ToggleButton) findViewById(R.id.PickToggleButton);
        mScaleTogglebutton = (ToggleButton) findViewById(R.id.ScaleToggleButton);
        mSwitchTogglebutton = (ToggleButton) findViewById(R.id.SwitchToggleButton);
        mHelpTogglebutton = (ToggleButton) findViewById(R.id.HelpToggleButton);
        mBrokenTogglebutton = (ToggleButton) findViewById(R.id.BrokenToggleButton);
        mClimbTogglebutton = (ToggleButton) findViewById(R.id.ClimbToggleButton);
        mParkTogglebutton = (ToggleButton) findViewById(R.id.ParkToggleButton);

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

        Integer auto_line;
        Integer auto_pick;
        Integer auto_scale;
        Integer auto_switch;

        Integer end_help;
        Integer end_broken;
        Integer end_climb;
        Integer end_park;

        Integer alliance_score;
        Integer enemy_score;

        EditText AllyScoreEditText = findViewById(R.id.AllyScoreEditText);
        EditText EnemyScoreEditText = findViewById(R.id.EnemyScoreEditText);

        String AllianceScoreString = AllyScoreEditText.getText().toString().trim();
        String EnemyScoreString = EnemyScoreEditText.getText().toString().trim();
        alliance_score = Integer.parseInt(AllianceScoreString);
        enemy_score = Integer.parseInt(EnemyScoreString);

        if(mLineTogglebutton.isChecked())
            auto_line = 1;
        else
            auto_line = 0;

        if(mPickTogglebutton.isChecked())
            auto_pick = 1;
        else
            auto_pick = 0;

        if(mScaleTogglebutton.isChecked())
            auto_scale= 1;
        else
            auto_scale = 0;

        if(mSwitchTogglebutton.isChecked())
            auto_switch = 1;
        else
            auto_switch = 0;

        if(mHelpTogglebutton.isChecked())
            end_help = 1;
        else
            end_help = 0;

        if(mBrokenTogglebutton.isChecked())
            end_broken = 1;
        else
            end_broken = 0;

        if(mClimbTogglebutton.isChecked())
            end_climb = 1;
        else
            end_climb = 0;

        if(mParkTogglebutton.isChecked())
            end_park = 1;
        else
            end_park = 0;


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


        values.put(ScoutEntry.COLUMN_SCOUT_AUTO_LINE, auto_line );
        values.put(ScoutEntry.COLUMN_SCOUT_AUTO_CUBE, auto_pick );
        values.put(ScoutEntry.COLUMN_SCOUT_AUTO_SCALE, auto_scale );
        values.put(ScoutEntry.COLUMN_SCOUT_AUTO_SWITCH, auto_switch );

        values.put(ScoutEntry.COLUMN_SCOUT_TELE_HELP_CLIMB, end_help );
        values.put(ScoutEntry.COLUMN_SCOUT_TELE_BROKEN, end_broken );
        values.put(ScoutEntry.COLUMN_SCOUT_TELE_CLIMB, end_climb );
        values.put(ScoutEntry.COLUMN_SCOUT_TELE_PARK, end_park );

        values.put(ScoutEntry.COLUMN_SCOUT_GAME_ALLY_SCORE, alliance_score);
        values.put(ScoutEntry.COLUMN_SCOUT_GAME_ENEMY_SCORE, enemy_score);


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
        //intent.putExtra(CreateMatchActivity.EXTRA_SCOUTER, mScouter);

        startActivity(intent);

    }

}
