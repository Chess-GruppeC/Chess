package at.aau.se2.chessify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import at.aau.se2.chessify.Diec.DiceActivity;
import at.aau.se2.chessify.util.Helper;

public class LobbyActivity extends AppCompatActivity {

    Button JoinGame;
    Button CreateGame;
    ImageView SoundLobby;
    ImageView Back;
    TextView TextViewBack;
    TextView viewGameID;
    EditText inputGameID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_lobby);

        JoinGame = findViewById(R.id.btn_JoinGame);
        CreateGame = findViewById(R.id.btn_CreateGame);
        Back = findViewById(R.id.ArrowBacklobby);
        TextViewBack = findViewById(R.id.textView2);
        viewGameID = findViewById(R.id.textView_viewGameID);
        inputGameID = findViewById(R.id.PlaintextEnterGameID);
        SoundLobby = findViewById(R.id.btn_sound_lobby);

        CreateGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDiceActivity();
            }
        });

        JoinGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDiceActivity();
            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getToMainActivity();
            }
        });

        TextViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getToMainActivity();
            }
        });

        SoundLobby.setOnClickListener(view -> {
            if (Helper.getBackgroundSound(this)) {
                SoundLobby.setImageResource(R.drawable.volume_off_white);
                Helper.setBackgroundSound(this, false);
            } else {
                SoundLobby.setImageResource(R.drawable.volume_on_white);
                Helper.setBackgroundSound(this, true);
            }
        });


    }

    public void getToMainActivity() {
        Intent intentgetBack = new Intent(this, MainActivity.class);
        startActivity(intentgetBack);
    }

    public void startDiceActivity() {
        Intent intentgetBack = new Intent(this, DiceActivity.class);
        startActivity(intentgetBack);
    }
}