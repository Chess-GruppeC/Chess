package at.aau.se2.chessify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class LobbyActivity extends AppCompatActivity {

    Button JoinGame;
    Button CreateGame;
    ImageView Back;
    TextView TextViewBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        JoinGame = findViewById(R.id.btn_JoinGame);
        CreateGame = findViewById(R.id.btn_CreateGame);
        Back = findViewById(R.id.ArrowBacklobby);
        TextViewBack = findViewById(R.id.textView2);

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


    }

    public void getToMainActivity() {
        Intent intentgetBack = new Intent(this, MainActivity.class);
        startActivity(intentgetBack);
    }
}