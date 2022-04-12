package at.aau.se2.chessify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    Button play;
    Button exit;
    Button settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        play = findViewById(R.id.btn_play);
        exit = findViewById(R.id.btn_exit);
        settings = findViewById(R.id.btn_settings);

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

    }

    /*
    public void play(){
        Intent intentPlay = new Intent(this, DiceActivity.class);               //Nicht aktiv, Spielstart klären
        startActivity(intentPlay);
    */

    public void closeApp() {
        finishAndRemoveTask();
    }

    public void openSettings() {
        Intent intentopenSettings = new Intent(this, SettingActivity.class);
        startActivity(intentopenSettings);
    }


}