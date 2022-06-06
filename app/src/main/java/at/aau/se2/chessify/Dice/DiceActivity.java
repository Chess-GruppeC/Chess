package at.aau.se2.chessify.Dice;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import at.aau.se2.chessify.AndroidGameUI.BoardView;
import at.aau.se2.chessify.LobbyActivity;
import at.aau.se2.chessify.Player;
import at.aau.se2.chessify.R;
import at.aau.se2.chessify.network.WebSocketClient;
import at.aau.se2.chessify.network.dto.DiceResultDTO;
import at.aau.se2.chessify.network.dto.PlayerDTO;
import at.aau.se2.chessify.util.Helper;

public class DiceActivity extends AppCompatActivity {
    private ImageView dice;
    private TextView player1Color;
    private TextView player2Color;
    private TextView player1Num;
    private TextView player2Num;
    private int number;
    private ShakeSensor mShaker;
    private Button creatBoard;
    Button abort;
    private Player player;
    private int diceNumber;
    ImageView Wallpaper;

    private WebSocketClient webSocketClient;
    private DiceResultDTO diceResultDTO;



    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dice);
        dice = findViewById(R.id.image_dice);
        creatBoard = findViewById(R.id.createBoard);
        abort = findViewById(R.id.btn_abort);
        Wallpaper= findViewById(R.id.imageView3);

        webSocketClient = WebSocketClient.getInstance(Helper.getPlayerName(this));
        getDiceWinner();
        creatBoard.setEnabled(false);


        dice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runShakeSensor();

                if (mShaker.activCount != 0){
                    //getDiceWinner();
                    getDiceNumber();
                    //sendDiceValue();
                }
            }
        });
        creatBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        dice.setEnabled(false);
        //player.setDiceNumber1(diceNumber);
        //compareWhoStart();
        //getDiceWinner();
    }

    private void runShakeSensor(){

        final Vibrator vibe = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

        mShaker = new ShakeSensor(this);
        mShaker.setOnShakeListener(new ShakeSensor.OnShakeListener(){
            public void onShake() {
                mShaker.activCount = 1;
                vibe.vibrate(500);
                new AlertDialog.Builder(DiceActivity.this)
                        .setPositiveButton(android.R.string.ok, null);
                      //  .setMessage("SHAKE!")
                     //   .show();
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

    private void getDiceWinner(){
        webSocketClient.receiveStartingPlayer(Helper.getGameId(this)).subscribe(stompMessage -> {
            diceResultDTO = new ObjectMapper().readValue(stompMessage.getPayload(),DiceResultDTO.class);
            Log.d("dicewinner", "aaaaaaaaaaaaaaaaaaaaaaaaaaa");
            if (diceResultDTO.getWinner() == null){
                dice.setEnabled(true);
                // wiederholen
                getDiceValue();
            }else{
                PlayerDTO winner = diceResultDTO.getWinner();
                PlayerDTO loser = diceResultDTO.getPlayers()
                        .stream().filter(playerDTO -> !playerDTO.getName().equals(winner.getName())).findFirst().get();
                runOnUiThread(() -> {
                    player1Color = findViewById(R.id.player1);
                    player1Color.setText(winner.getName());
               //     player1Num = findViewById(R.id.player1_color);
               //     player1Num.setText(winner.getDiceValue().toString());

                    player2Color = findViewById(R.id.player2);
                    player2Color.setText(loser.getName());
                //    player2Num = findViewById(R.id.player2_color);
               //     player2Num.setText(loser.getDiceValue().toString());


                    creatBoard.setEnabled(true);

                });

                // show winner
               /* Log.d("dicewinner", "aaaaaaaaaaaaaaaaaaaaaaaaaaa");

                List<PlayerDTO> playerDTOList = diceResultDTO.getPlayers();
                System.out.println(playerDTOList.get(0).getName());
                for (PlayerDTO playerDTO : playerDTOList) {
                    if (playerDTO.equals(diceResultDTO.getWinner())) {
                        // text
                        int num1 = playerDTOList.get(0).getDiceValue();
                        String p1 = playerDTOList.get(0).getName();
                        Log.d("dicewinner",p1);
                        player1Color = findViewById(R.id.player1_color);
                        player1Color.setText(diceNumber);

*/
               /* int num2 = playerDTOList.get(1).getDiceValue();
                String p2 = playerDTOList.get(1).getName();
                player1Color = findViewById(R.id.player1_color);
                player2Color = findViewById(R.id.player2_color);
                player1Color.setText(diceNumber);*/
                    }



        });
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

}