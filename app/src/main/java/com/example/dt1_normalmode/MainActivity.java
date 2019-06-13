package com.example.dt1_normalmode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    int player1, player2;
    String gridDimension;
    int mode;

    public final static String EXTRA_WIDTH= "com.example.dt1_normalmode.width";
    public final static String EXTRA_HEIGHT= "com.example.dt1_normalmode.height";
    public final static String EXTRA_MODE= "com.example.dt1_normalmode.mode";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spPlayer1= findViewById(R.id.spPlayer1);
        Spinner spPlayer2= findViewById(R.id.spPlayer2);
        Spinner spGridDimension= findViewById(R.id.spGridDimension);

        ArrayAdapter<CharSequence> adPlayer= ArrayAdapter.createFromResource(this, R.array.am_player, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adGridDimension= ArrayAdapter.createFromResource(this, R.array.am_GridDimension, android.R.layout.simple_spinner_item);

        adPlayer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adGridDimension.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spPlayer1.setAdapter(adPlayer);
        spPlayer2.setAdapter(adPlayer);
        spGridDimension.setAdapter(adGridDimension);

        spPlayer1.setOnItemSelectedListener(this);
        spPlayer2.setOnItemSelectedListener(this);
        spGridDimension.setOnItemSelectedListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.am_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.amMenuOk :
                Bundle bundle= calculate();

                if (mode==0) {
                    Intent intent= new Intent(this, GameActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    return true;
                }

                else {
                    Intent intent= new Intent(this, DifficultyActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    return true;
                }

            default: return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spPlayer1 : player1= position;
            return;
            case R.id.spPlayer2 : player2= position;
            return;
            case R.id.spGridDimension : gridDimension= parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        switch (parent.getId()) {
            case R.id.spPlayer1 : player1= 0;
                return;
            case R.id.spPlayer2 : player2= 1;
                return;
            case R.id.spGridDimension : gridDimension= "7x6";
        }
    }

    private Bundle calculate() {
        int width, height;
        Bundle bundle= new Bundle();

        if (player1== 0 && player2== 0)
            mode= 0;
        else if (player1== 0 && player2== 1)
            mode= 1;
        else if (player1== 1 && player2==0)
            mode= 2;
        else mode= 3;

        width= Character.getNumericValue(gridDimension.charAt(0));
        height= Character.getNumericValue(gridDimension.charAt(2));

        bundle.putInt(EXTRA_WIDTH, width);
        bundle.putInt(EXTRA_HEIGHT, height);
        bundle.putInt(EXTRA_MODE, mode);

        return bundle;
    }
}
