package com.nanodegree.displayjoking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayJokingActivity extends AppCompatActivity {

    public static final String EXTRA_JOKE = "com.udacity.gradle.builditbigger.EXTRA_JOKE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_joking);
        TextView jokeTextView = findViewById(R.id.joke_text_view);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_JOKE)){
            jokeTextView.setText(intent.getStringExtra(EXTRA_JOKE));
        }
    }
}
