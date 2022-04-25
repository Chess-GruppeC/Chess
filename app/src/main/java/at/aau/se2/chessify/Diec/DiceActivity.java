package at.aau.se2.chessify.Diec;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import at.aau.se2.chessify.AndroidGameUI.BoardView;
import at.aau.se2.chessify.R;

public class DiceActivity extends AppCompatActivity {
    private ImageView dice;
    private int number;
    private ShakeSensor mShaker;
    private Button creatBoard;



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

    @Override
    public void onPause()
    {
        mShaker.pause();
        super.onPause();
    }

}