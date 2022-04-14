package at.aau.se2.chessify;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DiceActivity extends AppCompatActivity {

    private ImageView dice;
    private int number = 5; //add sensor + calculator

    //Shake Sensor
    private float changeInAcceleration;

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    TextView text_currentAcceleration, text_prevAcceleration, text_Acceleration;
    private float accelerationCurrentValue;
    private float accelerationPreviousValue;
    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            accelerationCurrentValue = (float) (x  + y  + z );
            accelerationPreviousValue = accelerationCurrentValue;
            float i = 1.5F;
            changeInAcceleration = (Math.abs(accelerationCurrentValue * i - accelerationPreviousValue)) ;


            //update textViews
            text_Acceleration.setText("Acceleration Change: " + (int) changeInAcceleration);
            text_currentAcceleration.setText("Shake Counter = " + accelerationCurrentValue);
            text_prevAcceleration.setText("Previous: " + accelerationPreviousValue);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    private int checkShake(float changeInAcceleration) {
        int currentAcceleration = (int) changeInAcceleration;
        int previousAcceleration = (int) changeInAcceleration;
        int shakeCounter = 0;

        if (previousAcceleration <= 4){
            if (currentAcceleration >= 7){
                    shakeCounter++;

            }
        }
        return shakeCounter;
    }
    //Shake Sensor End

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);
        dice = findViewById(R.id.image_dice);

        //Shake Sensor
        //initialze sensor objects
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        text_Acceleration = findViewById(R.id.text_accel);
        text_currentAcceleration = findViewById(R.id.text_currentAccel);
        text_prevAcceleration = findViewById(R.id.text_prevAccel);
        //Shake Sensor End




        dice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                getDiceNumber();
            }

        });
        /*
        Button createBoard = findViewById(R.id.createBoard);
        createBoard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openGameView();
            }
        });*/

    }
    /*
    private void openGameView() {
        Intent intent = new Intent(this, ChessActivity.class);
        startActivity(intent);
    }*/


    private void getDiceNumber(){
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

    //Shake Sensor
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(sensorEventListener, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(sensorEventListener);
    }
    //Shake Sensor End


}
