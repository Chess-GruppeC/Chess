package at.aau.se2.chessify;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import at.aau.se2.chessify.network.WebSocketClient;
import at.aau.se2.chessify.util.Helper;

public class MainActivity extends AppCompatActivity {
    Button play;
    Button exit;
    Button settings;
    ImageView soundbutton;
    private TextView displayPlayername;
    ImageView wallpaper;
    TextView heading;
    private WebSocketClient webSocketClient;

    private ProgressDialog connectingToServerDialog;
    private boolean tryingToConnect;
    private Handler handler;
    private boolean loading;

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
        wallpaper = (ImageView) findViewById(R.id.imageView2);
        heading = findViewById(R.id.textview_welcome);

        play.setOnClickListener(view -> play());

        exit.setOnClickListener(view -> {
            closeApp();
            Helper.stopMusicBackground();
        });

        settings.setOnClickListener(view -> openSettings());

        soundbutton.setOnClickListener(view -> {
            if (Helper.getBackgroundSound(this)) {
                soundbutton.setImageResource(R.drawable.volume_off_white);
                Helper.setBackgroundSound(this, false);
                Helper.stopMusicBackground();
            } else {
                Helper.playMusicBackground(this);
                soundbutton.setImageResource(R.drawable.volume_on_white);
                Helper.setBackgroundSound(this, true);
            }
        });

        connectingToServerDialog = new ProgressDialog(this);
        connectingToServerDialog.setMessage("Connecting to server..");
        connectingToServerDialog.setTitle("Please wait");
        connectingToServerDialog.setIndeterminate(false);
        connectingToServerDialog.setCancelable(false);

        handler = new Handler(Looper.getMainLooper());
    }

    public void play() {

        connectingToServerDialog.show();
        tryingToConnect = true;
        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(() -> {
            tryingToConnect = false;
            loading = false;
            Toast.makeText(MainActivity.this, "Connection timeout", Toast.LENGTH_SHORT).show();
        }, 10000);

        if (!loading) {
            new Thread(() -> {
                loading = true;
                Intent intent = new Intent(this, LobbyActivity.class);
                webSocketClient = WebSocketClient.getInstance(Helper.getUniquePlayerName(getBaseContext()));
                while (tryingToConnect && !webSocketClient.isConnected()) ;
                connectingToServerDialog.cancel();
                handler.removeCallbacksAndMessages(null);
                if (tryingToConnect)
                    startActivity(intent);
                loading = false;
            }).start();
        }

    }

    public void closeApp() {
        finishAndRemoveTask();
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

        // --> Update Color Scheme
        if (Helper.getDarkmode(this)) {
            wallpaper.setImageResource(R.drawable.chessmainbackground_min);
            play.setBackground(getDrawable(R.drawable.custom_button1));
            settings.setBackground(getDrawable(R.drawable.custom_button1));
            exit.setBackground(getDrawable(R.drawable.custom_button1));
        } else {
            wallpaper.setImageResource(R.drawable.chessmainbackground_min_dark);
            play.setBackground(getDrawable(R.drawable.custom_button2));
            settings.setBackground(getDrawable(R.drawable.custom_button2));
            exit.setBackground(getDrawable(R.drawable.custom_button2));
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
        Helper.setBackgroundSound(this, false);
        Helper.stopMusicBackground();
    }

}