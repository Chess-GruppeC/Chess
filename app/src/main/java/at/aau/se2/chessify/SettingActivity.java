package at.aau.se2.chessify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import at.aau.se2.chessify.util.Helper;

public class SettingActivity extends AppCompatActivity {

    Button sound;
    Button vibrations;
    Button darkmode;
    ImageView back;
    EditText setName;
    TextView TextViewBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_setting);

        sound = findViewById(R.id.btn_sound);
        vibrations = findViewById(R.id.btn_vibrations);
        darkmode = findViewById(R.id.btn_darkmode);
        back = findViewById(R.id.ArrowBack);
        setName = findViewById(R.id.PlaintextSetName);
        TextViewBack = findViewById(R.id.textView);

        // Set Player Name
        if (!Helper.getPlayerName(this).equals("Player"))
            setName.setText(Helper.getPlayerName(this));

        findViewById(R.id.btn_save).setOnClickListener(view -> {
            if (TextUtils.isEmpty(setName.getText())) {
                Toast.makeText(this, "please Enter Name", Toast.LENGTH_SHORT).show();
            } else {
                Helper.setPlayerName(this, setName.getText().toString());
                Toast.makeText(this, "Player name updated successfully", Toast.LENGTH_SHORT).show();
            }
        });

        // Change appearance of Button depending on Sound off/on
        sound.setOnClickListener(view -> {
            if (Helper.getBackgroundSound(this)) {
                sound.setBackground(getDrawable(R.drawable.custom_button_deactivated));
                //Sound will follow
                Toast.makeText(this, "Sound off", Toast.LENGTH_SHORT).show();
                Helper.setBackgroundSound(this, false);
            } else {
                sound.setBackground(getDrawable(R.drawable.custom_button2));
                //Sound will follow
                Toast.makeText(this, "Sound on", Toast.LENGTH_SHORT).show();
                Helper.setBackgroundSound(this, true);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getBack();
            }
        });

        TextViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getBack();
            }
        });
    }

    public void getBack() {
        Intent intentgetBack = new Intent(this, MainActivity.class);
        startActivity(intentgetBack);
    }

}