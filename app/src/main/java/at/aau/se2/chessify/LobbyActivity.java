package at.aau.se2.chessify;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import at.aau.se2.chessify.Diec.DiceActivity;
import at.aau.se2.chessify.network.WebSocketClient;
import at.aau.se2.chessify.util.Helper;

public class LobbyActivity extends AppCompatActivity {

    Button JoinGame;
    Button CreateGame;
    ImageView SoundLobby;
    ImageView Back;
    TextView TextViewBack;
    TextView viewGameID;
    EditText inputGameID;

    private WebSocketClient webSocketClient;

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

        webSocketClient = WebSocketClient.getInstance(Helper.getPlayerName(this));

        CreateGame.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onClick(View view) {
                if (!webSocketClient.isConnected())
                    return;
                webSocketClient.requestNewGame().subscribe(gameId -> {
                    showToast("Received game ID " + gameId.getPayload());
                    runOnUiThread(() -> viewGameID.setText(gameId.getPayload()));
                    startDiceActivity();
                }, Throwable::printStackTrace);
            }
        });

        JoinGame.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onClick(View view) {
                String input = inputGameID.getText().toString();
                if (input.isEmpty()) {
                    showToast("Please enter Game ID");
                } else {
                    if (!webSocketClient.isConnected())
                        return;
                    webSocketClient.joinGame(inputGameID.getText().toString()).subscribe(response -> {
                        switch (response.getPayload()) {
                            case "1":
                                showToast("Successfully joined game");
                                startDiceActivity();
                                break;
                            case "0":
                                showToast("Game is already full");
                                break;
                            case "-1":
                                showToast("Game does not exist");
                                break;
                        }
                    }, Throwable::printStackTrace);
                }
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

    private void showToast(String text) {
        runOnUiThread(() ->
                Toast.makeText(getBaseContext(), text, Toast.LENGTH_SHORT).show());
    }

}