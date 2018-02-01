package com.robotronix3550.robotronix_scouting_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class CreateMatchActivity extends AppCompatActivity {

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

    }

}
