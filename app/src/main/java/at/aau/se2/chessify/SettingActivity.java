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

        // Set Player Name
        if (!Helper.getPlayerName(this).equals("Player"))
            setName.setText(Helper.getPlayerName(this));

        findViewById(R.id.btn_save).setOnClickListener(view -> {
            if (TextUtils.isEmpty(setName.getText())) {
                Toast.makeText(this, "please Enter Name", Toast.LENGTH_SHORT).show();
            } else {
                String newPlayerName = setName.getText().toString();
                Helper.setPlayerName(this, newPlayerName);
                WebSocketClient.reconnectWithNewPlayerName(newPlayerName);
                Toast.makeText(this, "Player name updated successfully", Toast.LENGTH_SHORT).show();
            }
        });


        // --> toogle Sound
        sound.setOnClickListener(view -> {
            if (Helper.getBackgroundSound(this)) {
                sound.setBackground(getDrawable(R.drawable.custom_button_deactivated));
                sound.setTextColor(Color.BLACK);
                Toast.makeText(this, "Sound off", Toast.LENGTH_SHORT).show();
                Helper.setBackgroundSound(this, false);
                Helper.stopMusicBackground(this);
            } else {
                Helper.playMusicBackground(this);
                sound.setBackground(getDrawable(R.drawable.custom_button2));
                sound.setTextColor(Color.WHITE);
                Toast.makeText(this, "Sound on", Toast.LENGTH_SHORT).show();
                Helper.setBackgroundSound(this, true);
            }
        });

        // --> toogle vibrations
        vibrations.setOnClickListener(view -> {
            if (Helper.getVibration(this)) {
                vibrations.setBackground(getDrawable(R.drawable.custom_button_deactivated));
                vibrations.setTextColor(Color.BLACK);
                Toast.makeText(this, "Vibration off", Toast.LENGTH_SHORT).show();
                Helper.setVibration(this, false);
            } else {
                vibrations.setBackground(getDrawable(R.drawable.custom_button2));
                vibrations.setTextColor(Color.WHITE);
                Toast.makeText(this, "Vibration on", Toast.LENGTH_SHORT).show();
                Helper.setVibration(this, true);
            }
        });

        // --> toogle color scheme
        darkmode.setOnClickListener(view -> {
            if (Helper.getDarkmode(this)) {
                darkmode.setBackground(getDrawable(R.drawable.custom_button_deactivated));
                darkmode.setTextColor(Color.BLACK);
                Toast.makeText(this, "Darkmode off", Toast.LENGTH_SHORT).show();
                Helper.setDarkmode(this, false);
            } else {
                darkmode.setBackground(getDrawable(R.drawable.custom_button2));
                darkmode.setTextColor(Color.WHITE);
                Toast.makeText(this, "Darkmode on", Toast.LENGTH_SHORT).show();
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
            sound.setBackground(getDrawable(R.drawable.custom_button2));
            sound.setTextColor(Color.WHITE);
        } else {
            sound.setBackground(getDrawable(R.drawable.custom_button_deactivated));
            sound.setTextColor(Color.BLACK);
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