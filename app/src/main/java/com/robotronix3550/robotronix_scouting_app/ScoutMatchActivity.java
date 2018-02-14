package com.robotronix3550.robotronix_scouting_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ScoutMatchActivity extends AppCompatActivity {

    Integer mCubeExchangeCnt;
    Integer mCubeAllySwitchCnt;
    Integer mCubeEnemySwitchCnt;
    Integer mCubeScaleCnt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scout_match);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = "Scouter un Match";

        // Capture the layout's TextView and set the string as its text
        //TextView titleTextView = findViewById(R.id.titleMatchTextView);
        //titleTextView.setText(message);

        mCubeExchangeCnt = 0;
        mCubeAllySwitchCnt = 0;
        mCubeEnemySwitchCnt = 0;
        mCubeScaleCnt = 0;

        TextView cubeExchangeText = (TextView) findViewById(R.id.ExchangeCntTextView);
        cubeExchangeText.setText(mCubeExchangeCnt.toString());

        TextView cubeAllySwitchText = (TextView) findViewById(R.id.AllySwitchTextView);
        cubeAllySwitchText.setText(mCubeAllySwitchCnt.toString());

        TextView cubeEnemySwitchText = (TextView) findViewById(R.id.EnemySwitchCntTextView);
        cubeEnemySwitchText.setText(mCubeEnemySwitchCnt.toString());

        TextView cubeScaleText = (TextView) findViewById(R.id.ScaleCntTextView);
        cubeScaleText.setText(mCubeScaleCnt.toString());

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



}
