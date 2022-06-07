package at.aau.se2.chessify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
    TextView TextViewBack;
    ImageView Wallpaper;

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
        TextViewBack = findViewById(R.id.textView);
        Wallpaper = (ImageView) findViewById(R.id.imageView2);


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
                sound.setText("Sound off");
                Toast.makeText(this, "Sound off", Toast.LENGTH_SHORT).show();
                Helper.setBackgroundSound(this, false);
                Helper.stopMusicBackground(this);
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
                darkmode.setText("Darkmode on");
                Wallpaper.setImageResource(R.drawable.chesssettingsbackground_min_dark);
                Toast.makeText(this, "Darkmode on", Toast.LENGTH_SHORT).show();
                Helper.setDarkmode(this, false);
            } else {
                darkmode.setBackground(getDrawable(R.drawable.custom_button1));
                vibrations.setBackground(getDrawable(R.drawable.custom_button1));
                sound.setBackground(getDrawable(R.drawable.custom_button1));
                darkmode.setText("Darkmode off");
                Wallpaper.setImageResource(R.drawable.chesssettingsbackground_min);
                Toast.makeText(this, "Darkmode off", Toast.LENGTH_SHORT).show();
                Helper.setDarkmode(this, true);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getBack();
            }
        });

        TextViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getBack();
            }
        });
    }

    public void getBack() {
        //Intent intentgetBack = new Intent(this, MainActivity.class);
        //startActivity(intentgetBack);
        onBackPressed();
    }

    @Override
    public void onResume() {
        super.onResume();

        // --> update Soundbutton
        if (Helper.getBackgroundSound(this)) {
            sound.setText("Sound on");
        } else {
            sound.setText("Sound off");
        }

        // --> Update Color Scheme
        if (Helper.getDarkmode(this)){
            Wallpaper.setImageResource(R.drawable.chessmainbackground_min);
            darkmode.setText("Darkmode off");
            darkmode.setBackground(getDrawable(R.drawable.custom_button1));
            vibrations.setBackground(getDrawable(R.drawable.custom_button1));
            sound.setBackground(getDrawable(R.drawable.custom_button1));
            darkmode.setText("Darkmode off");
        }else{
            Wallpaper.setImageResource(R.drawable.chessmainbackground_min_dark);
            darkmode.setText("Darkmode on");
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