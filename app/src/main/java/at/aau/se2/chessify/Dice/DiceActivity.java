package at.aau.se2.chessify.Dice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import at.aau.se2.chessify.AndroidGameUI.BoardView;
import at.aau.se2.chessify.Player;
import at.aau.se2.chessify.R;

public class DiceActivity extends AppCompatActivity {
    private ImageView dice;
    private TextView player1Color;
    private TextView player2Color;
    private int number;
    private ShakeSensor mShaker;
    private Button creatBoard;
    private Player player;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);
        dice = findViewById(R.id.image_dice);
        creatBoard = findViewById(R.id.createBoard);

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
                openGameView();
            }
        });




    }

    private void getDiceNumber(){
        int diceNumber = getRandomNumber(1, 6);

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
        player.setDiceNumber1(diceNumber);
        compareWhoStart();
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
                }

            }

        });
        if (mShaker.activCount != 0) {
            getDiceNumber();
        }

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

    @Override
    public void onPause()
    {
        if(mShaker != null)
            mShaker.pause();
        super.onPause();
    }

}