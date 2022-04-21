package at.aau.se2.chessify;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
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


        /*
        Sound will follow later...
        Sound();
         */

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
            } else {
                soundbutton.setImageResource(R.drawable.volume_on_white);
                Helper.setBackgroundSound(this, true);
            }
        });

    }


    public void play() {
        Intent intent = new Intent(this, LobbyActivity.class);               //Nicht aktiv, Spielstart klären
        startActivity(intent);
    }

    public void closeApp() {
        finishAndRemoveTask();
    }

    public void openSettings() {
        Intent intentopenSettings = new Intent(this, SettingActivity.class);
        startActivity(intentopenSettings);
    }

    // Später Soundfunktion
    /*public void Sound(){
        if (Helper.getBackgroundSound(this)){
            soundbutton.setImageResource(R.drawable.volume_on_white);
        }else{
            soundbutton.setImageResource(R.drawable.volume_off_white);
        }
    }
    */

    @Override
    public void onResume() {
        super.onResume();
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