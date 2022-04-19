package at.aau.se2.chessify.Diec;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import at.aau.se2.chessify.R;

public class DiceActivity extends AppCompatActivity {
    private ImageView dice;
    private int number; // calculator
    private Button shake;
    private ShakeSensor mShaker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);
        dice = findViewById(R.id.image_dice);
        shake = findViewById(R.id.shake);

        dice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runShakeSensor();
                if (runShakeSensor()){
                    getDiceNumber();
                }

                //getDiceNumber();
            }
        });

        shake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runShakeSensor();
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

    private boolean runShakeSensor(){
        boolean activ = true;

        final Vibrator vibe = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

        mShaker = new ShakeSensor(this);
        mShaker.setOnShakeListener(new ShakeSensor.OnShakeListener(){
            public void onShake() {
                vibe.vibrate(500);
                new AlertDialog.Builder(DiceActivity.this)
                        .setPositiveButton(android.R.string.ok, null)
                        .setMessage("SHAKE!")
                        .show();

            }

        });

        return false;
    }

    private int getRandomNumber(int min, int max) {
        int randomNum = (int) ((Math.random() * (max - min)) + min);
        return randomNum;
    }
    /*private void openGameView() {
        Intent intent = new Intent(this, ChessActivity.class);
        startActivity(intent);
    }*/

    @Override
    public void onPause()
    {
        mShaker.pause();
        super.onPause();
    }

}