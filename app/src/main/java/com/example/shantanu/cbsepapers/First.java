package com.example.shantanu.cbsepapers;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Set;


public class First extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        final CheckBox MAT = (CheckBox) findViewById(R.id.cbMAT);
        final CheckBox PHY = (CheckBox) findViewById(R.id.cbPHY);
        final CheckBox CHM = (CheckBox) findViewById(R.id.cbCHM);
        final CheckBox BIO = (CheckBox) findViewById(R.id.cbBIO);
        final CheckBox ENG = (CheckBox) findViewById(R.id.cbENG);
        final CheckBox CS = (CheckBox) findViewById(R.id.cbCS);
        final TextView firstMessage = (TextView) findViewById(R.id.tvChange);
        final Button firstButton = (Button) findViewById(R.id.bNext);
        final RelativeLayout laayout = (RelativeLayout) findViewById(R.id.layout);

        final SharedPreferences preferences = getSharedPreferences("Subjects", MODE_PRIVATE);
        if(preferences.getBoolean("MAT",false)){
            MAT.setChecked(true);
        }
        if(preferences.getBoolean("PHY",false)){
            PHY.setChecked(true);
        }
        if(preferences.getBoolean("CHM",false)){
            CHM.setChecked(true);
        }
        if(preferences.getBoolean("ENG",false)){
            ENG.setChecked(true);
        }
        if(preferences.getBoolean("CS",false)){
            CS.setChecked(true);
        }
        if(preferences.getBoolean("BIO",false)){
            BIO.setChecked(true);
        }

        setTitle("Getting Started");



        Bundle b = getIntent().getExtras();

        if(b.getBoolean("isFromSettings",false)){
            laayout.removeView(firstMessage);
            laayout.removeView(firstButton);
            setTitle("Choose Subjects");
        }

        MAT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("MAT", MAT.isChecked());
                editor.commit();
            }
        });
        PHY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("PHY", PHY.isChecked());
                editor.commit();
            }
        });
        CHM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("CHM", CHM.isChecked());
                editor.commit();
            }
        });
        ENG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("ENG", ENG.isChecked());
                editor.commit();
            }
        });
        CS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("CS", CS.isChecked());
                editor.commit();
            }
        });
        BIO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("BIO", BIO.isChecked());
                editor.commit();
            }
        });




        firstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("MAT", MAT.isChecked());
                editor.putBoolean("PHY", PHY.isChecked());
                editor.putBoolean("CHM", CHM.isChecked());
                editor.putBoolean("ENG", ENG.isChecked());
                editor.putBoolean("BIO", BIO.isChecked());
                editor.putBoolean("CS", CS.isChecked());
                editor.putBoolean("first", false);
                editor.commit();
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("grade",12);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_first, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
