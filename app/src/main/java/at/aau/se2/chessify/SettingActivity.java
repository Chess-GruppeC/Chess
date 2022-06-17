package at.aau.se2.chessify;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import at.aau.se2.chessify.network.WebSocketClient;
import at.aau.se2.chessify.util.Helper;

public class SettingActivity extends AppCompatActivity {

    Button sound;
    Button vibrations;
    Button darkmode;
    ImageView back;
    EditText setName;
    TextView textViewBack;
    ImageView wallpaper;

    String soundOff = "Sound off";
    String darkModeOn = "Darkmode on";
    String darkModeOff = "Darkmode off";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_setting);

        sound = findViewById(R.id.btn_sound);
        vibrations = findViewById(R.id.btn_vibrations);
        darkmode = findViewById(R.id.btn_darkmode);
        back = findViewById(R.id.ArrowBack);
        setName = findViewById(R.id.PlaintextEnterGameID);
        textViewBack = findViewById(R.id.textView);
        wallpaper = (ImageView) findViewById(R.id.imageView2);


        // Set Player Name
        if (!Helper.getPlayerName(this).equals("Player"))
            setName.setText(Helper.getPlayerName(this));

        findViewById(R.id.btn_save).setOnClickListener(view -> {
            if (TextUtils.isEmpty(setName.getText())) {
                Toast.makeText(this, "please Enter Name", Toast.LENGTH_SHORT).show();
            } else {
                String newPlayerName = setName.getText().toString();
                Helper.setPlayerName(this, newPlayerName);
                WebSocketClient.reconnectWithPlayerName(newPlayerName);
                Toast.makeText(this, "Player name updated successfully", Toast.LENGTH_SHORT).show();
            }
        });


        // --> toogle Sound
        sound.setOnClickListener(view -> {
            if (Helper.getBackgroundSound(this)) {
                sound.setText(soundOff);
                Toast.makeText(this, soundOff, Toast.LENGTH_SHORT).show();
                Helper.setBackgroundSound(this, false);
                Helper.stopMusicBackground();
            } else {
                Helper.playMusicBackground(this);
                sound.setText("Sound on");
                sound.setTextColor(Color.WHITE);
                Toast.makeText(this, "Sound on", Toast.LENGTH_SHORT).show();
                Helper.setBackgroundSound(this, true);
            }
        });

        // --> toogle vibrations
        vibrations.setOnClickListener(view -> {
            if (Helper.getVibration(this)) {
                vibrations.setText("Vibrations off");
                Toast.makeText(this, "Vibration off", Toast.LENGTH_SHORT).show();
                Helper.setVibration(this, false);
            } else {
                vibrations.setText("Vibrations on");
                Toast.makeText(this, "Vibration on", Toast.LENGTH_SHORT).show();
                Helper.setVibration(this, true);
            }
        });

        // --> toogle color scheme
        darkmode.setOnClickListener(view -> {
            if (Helper.getDarkmode(this)) {
                darkmode.setBackground(getDrawable(R.drawable.custom_button2));
                vibrations.setBackground(getDrawable(R.drawable.custom_button2));
                sound.setBackground(getDrawable(R.drawable.custom_button2));
                darkmode.setText(darkModeOn);
                wallpaper.setImageResource(R.drawable.chesssettingsbackground_min_dark);
                Toast.makeText(this, darkModeOn, Toast.LENGTH_SHORT).show();
                Helper.setDarkmode(this, false);
            } else {
                darkmode.setBackground(getDrawable(R.drawable.custom_button1));
                vibrations.setBackground(getDrawable(R.drawable.custom_button1));
                sound.setBackground(getDrawable(R.drawable.custom_button1));
                darkmode.setText(darkModeOff);
                wallpaper.setImageResource(R.drawable.chesssettingsbackground_min);
                Toast.makeText(this, darkModeOff, Toast.LENGTH_SHORT).show();
                Helper.setDarkmode(this, true);
            }
        });

        back.setOnClickListener(view -> getBack());

        textViewBack.setOnClickListener(view -> getBack());
    }

    public void getBack() {
        onBackPressed();
    }

    @Override
    public void onResume() {
        super.onResume();

        // --> update Soundbutton
        if (Helper.getBackgroundSound(this)) {
            sound.setText("Sound on");
        } else {
            sound.setText(soundOff);
        }

        // --> Update Color Scheme
        if (Helper.getDarkmode(this)){
            wallpaper.setImageResource(R.drawable.chessmainbackground_min);
            darkmode.setText(darkModeOff);
            darkmode.setBackground(getDrawable(R.drawable.custom_button1));
            vibrations.setBackground(getDrawable(R.drawable.custom_button1));
            sound.setBackground(getDrawable(R.drawable.custom_button1));
            darkmode.setText(darkModeOff);
        }else{
            wallpaper.setImageResource(R.drawable.chessmainbackground_min_dark);
            darkmode.setText(darkModeOn);
            darkmode.setBackground(getDrawable(R.drawable.custom_button2));
            vibrations.setBackground(getDrawable(R.drawable.custom_button2));
            sound.setBackground(getDrawable(R.drawable.custom_button2));
        }



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