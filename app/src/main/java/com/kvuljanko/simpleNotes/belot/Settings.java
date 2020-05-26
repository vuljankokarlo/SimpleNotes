package com.kvuljanko.simpleNotes.belot;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.kvuljanko.simpleNotes.belot.DataClass.ConfigData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Settings extends AppCompatActivity {

    public ConfigData configData = null;

    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        EditText belotPlayer1 = findViewById(R.id.belotPlayer1);
        EditText belotPlayer2 = findViewById(R.id.belotPlayer2);
        EditText belotPlayer3 = findViewById(R.id.belotPlayer3);
        EditText belotPlayer4 = findViewById(R.id.belotPlayer4);

        file = getFileStreamPath("cfg");
        if(file == null || !file.exists()) {
            file = new File(getFilesDir(), "cfg");
        }

        loadCfg();

        if (configData != null) {
            if (configData.getBelotName1() != null) {
                belotPlayer1.setText(configData.getBelotName1());
            } else {
                configData.setBelotName1("Player 1");
                belotPlayer1.setText(configData.getBelotName1());
            }
            if (configData.getBelotName2() != null) {
                belotPlayer2.setText(configData.getBelotName2());
            } else {
                configData.setBelotName2("Player 2");
                belotPlayer2.setText(configData.getBelotName2());
            }
            if (configData.getBelotName3() != null) {
                belotPlayer3.setText(configData.getBelotName3());
            } else {
                configData.setBelotName3("Player 3");
                belotPlayer3.setText(configData.getBelotName3());
            }
            if (configData.getBelotName4() != null) {
                belotPlayer4.setText(configData.getBelotName4());
            } else {
                configData.setBelotName4("Player 4");
                belotPlayer4.setText(configData.getBelotName4());
            }
        } else {
            configData = new ConfigData();

        }

        belotPlayer1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                configData.setBelotName1(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        belotPlayer2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                configData.setBelotName2(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        belotPlayer3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                configData.setBelotName3(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        belotPlayer4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                configData.setBelotName4(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        FloatingActionButton fab = findViewById(R.id.saveCfg);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Config data saved!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                saveCfg();
            }
        });

    }

    public void loadCfg(){
        FileInputStream fis = null;
        try {
            fis = openFileInput("cfg");
            ObjectInputStream is = new ObjectInputStream(fis);
            configData = (ConfigData) is.readObject();
            is.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveCfg() {
        FileOutputStream fos = null;
        try {
            fos = openFileOutput("cfg", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(configData);
            os.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
