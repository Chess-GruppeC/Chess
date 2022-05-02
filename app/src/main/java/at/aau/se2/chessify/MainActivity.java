package at.aau.se2.chessify;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import at.aau.se2.chessify.util.Helper;

public class MainActivity extends AppCompatActivity {
    Button play;
    Button exit;
    Button settings;
    ImageView soundbutton;
    private TextView displayPlayername;
    MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        play = findViewById(R.id.btn_play);
        exit = findViewById(R.id.btn_exit);
        settings = findViewById(R.id.btn_settings);
        soundbutton = findViewById(R.id.btn_sound_main);
        displayPlayername = findViewById(R.id.TextViewPlayerName);

        //Display PlayerName - Setup in Settings
        if (!Helper.getPlayerName(this).equals("Player"))
            displayPlayername.setText(Helper.getPlayerName(this));


        mediaPlayer = MediaPlayer.create(this, R.raw.backgroundmusic);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

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
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettings();
            }
        });

        soundbutton.setOnClickListener(view -> {
            if (Helper.getBackgroundSound(this)) {
                soundbutton.setImageResource(R.drawable.volume_off_white);
                Helper.setBackgroundSound(this, false);
                mediaPlayer.pause();
            } else {
                soundbutton.setImageResource(R.drawable.volume_on_white);
                Helper.setBackgroundSound(this, true);
                mediaPlayer.start();
            }
        });

    }


    public void play() {
        Intent intent = new Intent(this, LobbyActivity.class);               //Nicht aktiv, Spielstart klären
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
        if (Helper.getBackgroundSound(this)) {
            soundbutton.setImageResource(R.drawable.volume_on_white);
            mediaPlayer.start();
        } else {
            soundbutton.setImageResource(R.drawable.volume_off_white);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        mediaPlayer.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.pause();
    }

}