package at.aau.se2.chessify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import at.aau.se2.chessify.Dice.DiceActivity;
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
    private Button btnStartGame;
    private TextView viewGameIdLabel;
    private ImageView iconCopy;
    ImageView Wallpaper;

    private WebSocketClient webSocketClient;

    private boolean triedToJoinAGame = false;
    private final Handler enterIdHandler = new Handler(Looper.getMainLooper());

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
        btnStartGame = findViewById(R.id.btn_startGame);
        viewGameIdLabel = findViewById(R.id.textView_GameIdLabel);
        iconCopy = findViewById(R.id.icon_copy);
        Wallpaper = (ImageView) findViewById(R.id.imageView3);

        webSocketClient = WebSocketClient.getInstance(Helper.getPlayerName(this));

        btnStartGame.setOnClickListener(view -> startDiceActivity());
        CreateGame.setOnClickListener(getCreateGameClickListener());
        viewGameID.setOnClickListener(getCopyToClipboardClickListener());
        iconCopy.setOnClickListener(getCopyToClipboardClickListener());
        inputGameID.addTextChangedListener(getIdTextChangedListener());
        JoinGame.setOnClickListener(getJoinGameClickListener());

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

        SoundLobby.setOnClickListener(view ->

        {
            if (Helper.getBackgroundSound(this)) {
                SoundLobby.setImageResource(R.drawable.volume_off_white);
                Helper.setBackgroundSound(this, false);
                Helper.stopMusicBackground(this);
            } else {
                Helper.playMusicBackground(this);
                SoundLobby.setImageResource(R.drawable.volume_on_white);
                Helper.setBackgroundSound(this, true);
            }
        });


    }

    private View.OnClickListener getJoinGameClickListener() {
        return view -> startDiceActivity();
    }

    @SuppressLint("CheckResult")
    private View.OnClickListener getCreateGameClickListener() {
        return view -> {
            if (!webSocketClient.isConnected()) {
                showNetworkError();
                return;
            }
            webSocketClient.requestNewGame()
                    .subscribe(gameId -> {
                        String id = gameId.getPayload();
                        runOnUiThread(() -> {
                            enableStartGame();
                            saveGameId(id);
                            viewGameID.setText(id);
                        });
                    }, throwable -> showNetworkError());
        };
    }

    private TextWatcher getIdTextChangedListener() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // method ignored
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                enterIdHandler.removeCallbacksAndMessages(null);
                if (triedToJoinAGame && (charSequence.length() == 0 || charSequence.length() == 4 || charSequence.length() == 6)) {
                    disableJoinGame();
                }
            }

            @SuppressLint("CheckResult")
            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 5) {
                    enterIdHandler.postDelayed(() -> {
                        if (!webSocketClient.isConnected()) {
                            showNetworkError();
                            return;
                        }
                        String input = editable.toString();
                        webSocketClient.joinGame(input).subscribe(response -> {
                            triedToJoinAGame = true;
                            switch (response.getPayload()) {
                                case "1":
                                    saveGameId(input);
                                    enableJoinGame();
                                    break;
                                case "0":
                                    showToast("Game is already full");
                                    disableJoinGame();
                                    break;
                                case "-1":
                                    showToast("Game does not exist");
                                    disableJoinGame();
                                    break;
                                default:
                                    showToast("Server error");
                                    break;
                            }
                        }, throwable -> showNetworkError());
                    }, 50);

                }
            }
        };
    }

    public void getToMainActivity() {
        //Intent intentgetBack = new Intent(this, MainActivity.class);
        //startActivity(intentgetBack);
        onBackPressed();
    }

    public void startDiceActivity() {
        Intent intentDiceActivity = new Intent(this, DiceActivity.class);
        startActivity(intentDiceActivity);

    }

    private void showToast(String text) {
        runOnUiThread(() ->
                Toast.makeText(getBaseContext(), text, Toast.LENGTH_SHORT).show());
    }

    private void enableJoinGame() {
        runOnUiThread(() -> {
            JoinGame.setEnabled(true);
            JoinGame.setBackground(AppCompatResources.getDrawable(this, R.drawable.custom_button_lobby_join_success));
            JoinGame.setTextColor(Color.BLACK);
        });
        triedToJoinAGame = true;
    }

    private void disableJoinGame() {
        runOnUiThread(() -> {
            JoinGame.setEnabled(false);
            JoinGame.setBackground(AppCompatResources.getDrawable(this, R.drawable.custom_button_lobby_join_error));
            JoinGame.setTextColor(Color.WHITE);
        });
    }

    private View.OnClickListener getCopyToClipboardClickListener() {
        return view -> {
            ClipboardManager clipboard = ContextCompat.getSystemService(getBaseContext(), ClipboardManager.class);
            if (clipboard != null) {
                clipboard.setPrimaryClip(ClipData.newPlainText("", Helper.getGameId(getBaseContext())));
                showToast("Copied Game ID to Clipboard");
            }
        };
    }

    private void saveGameId(String gameId) {
        Helper.setGameId(getBaseContext(), gameId);
    }

    private void setVisible(View view) {
        view.setVisibility(View.VISIBLE);
    }

    private void setInvisible(View view) {
        view.setVisibility(View.INVISIBLE);
    }

    private void enableStartGame() {
        setInvisible(CreateGame);
        setVisible(viewGameID);
        setVisible(viewGameIdLabel);
        setVisible(btnStartGame);
        setVisible(iconCopy);
    }

    private void showNetworkError() {
        showToast("Network error");
    }

    @Override
    public void onResume() {
        super.onResume();

        // --> update Soundsymbol
        if (Helper.getBackgroundSound(this)) {
            SoundLobby.setImageResource(R.drawable.volume_on_white);
        } else {
            SoundLobby.setImageResource(R.drawable.volume_off_white);
        }

        // --> Update Color Scheme
        if (Helper.getDarkmode(this)){
            Wallpaper.setImageResource(R.drawable.wallpaper1_material_min);
            CreateGame.setBackground(getDrawable(R.drawable.custom_button1));
            btnStartGame.setBackground(getDrawable(R.drawable.custom_button1));
            JoinGame.setBackground(getDrawable(R.drawable.custom_button1));
        }else{
            Wallpaper.setImageResource(R.drawable.wallpaper1_material_min_dark);
            CreateGame.setBackground(getDrawable(R.drawable.custom_button2));
            btnStartGame.setBackground(getDrawable(R.drawable.custom_button2));
            JoinGame.setBackground(getDrawable(R.drawable.custom_button2));
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