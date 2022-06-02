package at.aau.se2.chessify.Dice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.fasterxml.jackson.databind.ObjectMapper;

import at.aau.se2.chessify.AndroidGameUI.BoardView;
import at.aau.se2.chessify.LobbyActivity;
import at.aau.se2.chessify.Player;
import at.aau.se2.chessify.R;
import at.aau.se2.chessify.network.WebSocketClient;
import at.aau.se2.chessify.network.dto.DiceResultDTO;
import at.aau.se2.chessify.network.dto.PlayerDTO;
import at.aau.se2.chessify.util.Helper;
import io.reactivex.disposables.Disposable;

public class DiceActivity extends AppCompatActivity {
    private ImageView dice;
    private TextView player1Color;
    private TextView player2Color;
    private int number;
    private ShakeSensor mShaker;
    private Button creatBoard;
    Button abort;
    private Player player;
    private int diceNumber;
    ImageView Wallpaper;

    private WebSocketClient webSocketClient;
    private DiceResultDTO diceResultDTO;

    private Disposable getDiceResultDisposable;

    private ObjectMapper objectMapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dice);
        dice = findViewById(R.id.image_dice);
        creatBoard = findViewById(R.id.createBoard);
        abort = findViewById(R.id.btn_abort);
        Wallpaper= findViewById(R.id.imageView3);

        webSocketClient = WebSocketClient.getInstance(Helper.getUniquePlayerName(this));
        objectMapper = new ObjectMapper();
        //creatBoard.setEnabled(false);

        getDiceResultDisposable = webSocketClient.receiveStartingPlayer(Helper.getGameId(this))
                .subscribe(stompMessage -> {
            diceResultDTO = objectMapper.readValue(stompMessage.getPayload(),DiceResultDTO.class);
            PlayerDTO winner = diceResultDTO.getWinner();
        });

        dice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runShakeSensor();

                if (mShaker.activCount != 0){
                    getDiceNumber();
                }
            }
        });
        creatBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                diceNumber = getRandomNumber(1, 6);

                sendDiceValue();
                openGameView();
            }
        });

        abort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToLobby();
                // Close Server - build new connection
            }
        });
    }

    private void getDiceNumber(){
        diceNumber = getRandomNumber(1, 6);

        sendDiceValue();

        switch (diceNumber){
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
        //player.setDiceNumber1(diceNumber);
        //compareWhoStart();
    }

    private void runShakeSensor(){

        final Vibrator vibe = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

        mShaker = new ShakeSensor(this);
        mShaker.setOnShakeListener(new ShakeSensor.OnShakeListener(){
            public void onShake() {
                mShaker.activCount = 1;
                vibe.vibrate(500);
                new AlertDialog.Builder(DiceActivity.this)
                        .setPositiveButton(android.R.string.ok, null)
                        .setMessage("SHAKE!")
                        .show();
                if (mShaker.activCount != 0) {
                    getDiceNumber();
                    onPause();
                }
            }
        });
    }

    private int getRandomNumber(int min, int max) {
        number = (int) ((Math.random() * (max - min)) + min);
        return number;
    }
    private void openGameView() {
        Intent intent = new Intent(this, BoardView.class);
        startActivity(intent);
    }

    private void compareWhoStart(){
        player.setCurrentPlayer(player.getWhitePlayer());
        if (player.getDiceNumber1() < player.getDiceNumber2()){
            player1Color.setId(R.id.player1_color);
            player1Color.setText("BLACK " + player.getDiceNumber1());
            player2Color.setId(R.id.player2_color);
            player2Color.setText("WHITE " + player.getDiceNumber2());
            player.changeCurrentPlayer();
        }else {
            player2Color.setId(R.id.player2_color);
            player2Color.setText("BLACK " + player.getDiceNumber2());
            player1Color.setId(R.id.player1_color);
            player1Color.setText("WHITE " + player.getDiceNumber1());
        }
    }

    public void backToLobby(){
        Intent intent = new Intent(this, LobbyActivity.class);
        startActivity(intent);
        // onBackPressed();
    }

    private void sendDiceValue(){
        webSocketClient.sendDiceValue(Helper.getGameId(this),diceNumber + "");
    }
    private void getDiceValue(){

    }

    @Override
    public void onResume() {
        super.onResume();

        // --> Update Color Scheme
        if (Helper.getDarkmode(this)){
            Wallpaper.setImageResource(R.drawable.wallpaper1_material_min);
            creatBoard.setBackground(getDrawable(R.drawable.custom_button1));
            abort.setBackground(getDrawable(R.drawable.custom_button1));

        }else{
            Wallpaper.setImageResource(R.drawable.wallpaper1_material_min_dark);
            creatBoard.setBackground(getDrawable(R.drawable.custom_button2));
            abort.setBackground(getDrawable(R.drawable.custom_button2));
        }
    }

    @Override
    public void onPause()
    {
        if(mShaker != null)
            mShaker.pause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getDiceResultDisposable.dispose();
    }
}