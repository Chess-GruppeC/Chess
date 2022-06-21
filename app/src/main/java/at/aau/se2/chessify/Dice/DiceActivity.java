package at.aau.se2.chessify.Dice;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import at.aau.se2.chessify.AndroidGameUI.BoardView;
import at.aau.se2.chessify.LobbyActivity;
import at.aau.se2.chessify.R;
import at.aau.se2.chessify.chess_logic.pieces.PieceColour;
import at.aau.se2.chessify.network.WebSocketClient;
import at.aau.se2.chessify.network.dto.DiceResultDTO;
import at.aau.se2.chessify.network.dto.PlayerDTO;
import at.aau.se2.chessify.util.Helper;

public class DiceActivity extends AppCompatActivity {
    private ImageView dice;
    private TextView player1Color;
    private TextView player2Color;
    private TextView reroll;
    private ShakeSensor mShaker;
    private Button creatBoard;
    Button abort;
    private int diceNumber;
    ImageView wallpaper;

    private WebSocketClient webSocketClient;
    private DiceResultDTO diceResultDTO;

    private PlayerDTO opponent;

    private Context baseContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dice);
        dice = findViewById(R.id.image_dice);
        creatBoard = findViewById(R.id.createBoard);
        abort = findViewById(R.id.btn_abort);
        wallpaper = findViewById(R.id.imageView3);

        baseContext = getBaseContext();
        webSocketClient = WebSocketClient.getInstance(Helper.getPlayerName(this));
        getDiceWinner();
        creatBoard.setEnabled(false);

        //reroll
        reroll = (TextView) findViewById(R.id.reroll);
        reroll.setVisibility(View.INVISIBLE);

        webSocketClient = WebSocketClient.getInstance(Helper.getUniquePlayerName(this));

        dice.setOnClickListener(view -> {
            runShakeSensor();

            if (mShaker.activCount != 0) {
                getDiceNumber();
            }
        });
        creatBoard.setOnClickListener(view -> openGameView());

        abort.setOnClickListener(view -> {
            backToLobby();
            // Close Server - build new connection
        });
    }

    private void getDiceNumber() {
        diceNumber = getRandomNumber(1, 6);

        sendDiceValue();

        switch (diceNumber) {
            case 1:
                dice.setImageResource(R.drawable.dice1);
                break;
            case 2:
                dice.setImageResource(R.drawable.dice2);
                break;
            case 3:
                dice.setImageResource(R.drawable.dice3);
                break;
            case 4:
                dice.setImageResource(R.drawable.dice4);
                break;
            case 5:
                dice.setImageResource(R.drawable.dice5);
                break;
            case 6:
                dice.setImageResource(R.drawable.dice6);
                break;
        }
        dice.setEnabled(false);
        reroll.setVisibility(View.INVISIBLE);
    }

    private void runShakeSensor() {

        final Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        mShaker = new ShakeSensor(this);
        mShaker.setOnShakeListener(() -> {
            mShaker.activCount = 1;
            vibe.vibrate(500);
            new AlertDialog.Builder(DiceActivity.this)
                    .setPositiveButton(android.R.string.ok, null);
            if (mShaker.activCount != 0) {
                getDiceNumber();
                onPause();
            }
        });
    }

    private int getRandomNumber(int min, int max) {
        int number = (int) ((Math.random() * (max - min)) + min);
        return number;
    }

    private void openGameView() {
        Intent intent = new Intent(this, BoardView.class);
        startActivity(intent);
    }
    

    public void backToLobby() {
        Intent intent = new Intent(this, LobbyActivity.class);
        startActivity(intent);
    }

    private void sendDiceValue() {
        webSocketClient.sendDiceValue(Helper.getGameId(this), diceNumber + "");
    }

    @SuppressLint("CheckResult")
    private void getDiceWinner() {

        webSocketClient.receiveStartingPlayer(Helper.getGameId(this)).subscribe(stompMessage -> {
            diceResultDTO = new ObjectMapper().readValue(stompMessage.getPayload(), DiceResultDTO.class);
            if (diceResultDTO.getWinner() == null) {
                // wiederholen

                runOnUiThread(() -> {
                    dice.setEnabled(true);
                    reroll.setVisibility(View.VISIBLE);

                });

            } else {
                PlayerDTO winner = diceResultDTO.getWinner();
                PlayerDTO loser = diceResultDTO.getPlayers()
                        .stream().filter(playerDTO -> !playerDTO.getName().equals(winner.getName())).findFirst().get();
                runOnUiThread(() -> {

                    if (winner.getDiceValue().equals(loser.getDiceValue())) {
                        dice.setEnabled(true);
                        reroll.setVisibility(View.VISIBLE);
                    } else {
                        player1Color = findViewById(R.id.player1);
                        player1Color.setText(winner.getName());

                        player2Color = findViewById(R.id.player2);
                        player2Color.setText(loser.getName());
                    }

                    opponent = diceResultDTO.getPlayers()
                            .stream()
                            .filter(playerDTO -> !playerDTO.getName().equals(Helper.getUniquePlayerName(getApplicationContext())))
                            .findFirst().orElse(null);

                    try {
                        Helper.setOpponent(baseContext, opponent);
                    } catch (JsonProcessingException jsonProcessingException) {
                        // unhandled
                    }

                    if (winner.getName().equals(Helper.getUniquePlayerName(getApplicationContext()))) {
                        Helper.setPlayerColour(baseContext, PieceColour.WHITE);
                    } else {
                        Helper.setPlayerColour(baseContext, PieceColour.BLACK);
                    }

                    creatBoard.setEnabled(true);

                });
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        // --> Update Color Scheme
        if (Helper.getDarkmode(this)) {
            wallpaper.setImageResource(R.drawable.wallpaper1_material_min);
            creatBoard.setBackground(getDrawable(R.drawable.custom_button1));
            abort.setBackground(getDrawable(R.drawable.custom_button1));

        } else {
            wallpaper.setImageResource(R.drawable.wallpaper1_material_min_dark);
            creatBoard.setBackground(getDrawable(R.drawable.custom_button2));
            abort.setBackground(getDrawable(R.drawable.custom_button2));
        }
    }

    @Override
    public void onPause() {
        if (mShaker != null)
            mShaker.pause();
        super.onPause();
    }

}