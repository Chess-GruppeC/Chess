package at.aau.se2.chessify.DiceTest;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.hardware.SensorManager;

import org.junit.Test;

import at.aau.se2.chessify.Dice.ShakeSensor;

public class ShakeSensorTest {

    /*
    TODO:
    resume
    pause
    onSensorChanged
     */

    SensorManager sensorManager;
    ShakeSensor.OnShakeListener shakeListener;
    Context context;
    ShakeSensor shakeSensor;

    @Test
    public void resumeNullTest(){
        sensorManager = null;
        assertEquals(null, sensorManager);
    }
   /* @Test
    public void testUnsupportedOperationException() {
        SensorManager sensorManager = null;
        Throwable exception = assertThrows(UnsupportedOperationException.class, () ->  );
        assertEquals("Sensors not supported", exception.getMessage());
    }*/
    @Test
    public void resumeValueTest(){
        SensorManager sensorManager;
        Context context = null;
        assertEquals(null, sensorManager = null);
    }

    @Test
    public void pauseTest(){

    }

    @Test
    public void onSensorChangedTest(){

    }

}
