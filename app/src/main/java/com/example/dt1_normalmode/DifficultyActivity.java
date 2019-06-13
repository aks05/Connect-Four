package com.example.dt1_normalmode;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class DifficultyActivity extends AppCompatActivity {
    public static final String EXTRA_DIFFICULTY= "com.example.dt1_normalmode.difficulty";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty);
    }

}
