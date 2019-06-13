package com.example.dt1_normalmode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class GameActivity extends AppCompatActivity {
    ConnectFourView myConnectFourView;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent= getIntent();
        bundle= intent.getExtras();
        int width= bundle.getInt(MainActivity.EXTRA_WIDTH);
        int height= bundle.getInt(MainActivity.EXTRA_HEIGHT);
        int mode= bundle.getInt(MainActivity.EXTRA_MODE, 0);
        int difficulty= bundle.getInt(DifficultyActivity.EXTRA_DIFFICULTY,0);

        myConnectFourView= new ConnectFourView(this, width, height, mode, difficulty );
        setContentView(myConnectFourView);
    }

    @Override
    public void onBackPressed() {
        if (!myConnectFourView.reverse())
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.ga_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.ga_MenuReset :
                Intent intent= new Intent(this, GameActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
                return true;

                default: return super.onOptionsItemSelected(menuItem);
        }
    }
}
