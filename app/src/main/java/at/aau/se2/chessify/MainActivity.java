package at.aau.se2.chessify;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import at.aau.se2.chessify.util.Helper;

public class MainActivity extends AppCompatActivity {
    Button play;
    Button exit;
    Button settings;
    ImageView soundbutton;
    private TextView displayPlayername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        Helper.setBackgroundSound(this, true);
        Helper.playMusicBackground(this);

        play = findViewById(R.id.btn_play);
        exit = findViewById(R.id.btn_exit);
        settings = findViewById(R.id.btn_settings);
        soundbutton = findViewById(R.id.btn_sound_main);
        displayPlayername = findViewById(R.id.TextViewPlayerName);


        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                play();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeApp();
                Helper.stopMusicBackground(MainActivity.this);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettings();
            }
        });

        // --> toogle sound
        if (Helper.getBackgroundSound(this)) {
            soundbutton.setImageResource(R.drawable.volume_on_white);
        } else {
            soundbutton.setImageResource(R.drawable.volume_off_white);
        }
        soundbutton.setOnClickListener(view -> {
            if (Helper.getBackgroundSound(this)) {
                soundbutton.setImageResource(R.drawable.volume_off_white);
                Helper.setBackgroundSound(this, false);
                Helper.stopMusicBackground(this);
            } else {
                Helper.playMusicBackground(this);
                soundbutton.setImageResource(R.drawable.volume_on_white);
                Helper.setBackgroundSound(this, true);
            }
        });

    }


    public void play() {
        Intent intent = new Intent(this, LobbyActivity.class);
        startActivity(intent);
    }

    public void closeApp() {
        finishAndRemoveTask();
        //finish();
        //System.exit(0);
    }

    public void openSettings() {
        Intent intentopenSettings = new Intent(this, SettingActivity.class);
        startActivity(intentopenSettings);
    }


    @Override
    public void onResume() {
        super.onResume();

        // --> update Soundsymbol
        if (Helper.getBackgroundSound(this)) {
            soundbutton.setImageResource(R.drawable.volume_on_white);
        } else {
            soundbutton.setImageResource(R.drawable.volume_off_white);
        }

        // --> update Playername
        if (!Helper.getPlayerName(this).equals("Player"))
            displayPlayername.setText(Helper.getPlayerName(this));
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

}