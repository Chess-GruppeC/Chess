package at.aau.se2.chessify;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class DiceActivity extends AppCompatActivity {

    private ImageView dice;
    private int number = 5; //add sensor + calculator

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);
        dice = findViewById(R.id.image_dice);
        dice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                shake();
            }
        });
    }

    private void shake(){
        int diceNumber = number;

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

}
