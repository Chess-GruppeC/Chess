package at.aau.se2.chessify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

public class SettingActivity extends AppCompatActivity {

    Button sound;
    Button vibrations;
    Button darkmode;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_setting);

        sound = findViewById(R.id.btn_sound);
        vibrations = findViewById(R.id.btn_vibrations);
        darkmode = findViewById(R.id.btn_darkmode);
        back = findViewById(R.id.ArrowBack);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               getBack();
            }
        });
    }

    public void getBack(){
        Intent intentgetBack = new Intent(this, MainActivity.class);
        startActivity(intentgetBack);
    }

}