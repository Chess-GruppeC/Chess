package at.aau.se2.chessify.Dice;

import android.content.Context;
import android.hardware.SensorListener;
import android.hardware.SensorManager;

public class ShakeSensor implements SensorListener {

    private static final int FORCE_THRESHOLD = 350;
    private static final int TIME_THRESHOLD = 100;
    private static final int SHAKE_TIMEOUT = 500;
    private static final int SHAKE_DURATION = 1000;
    private static final int SHAKE_COUNT = 3;

    private SensorManager sensorManager;
    private float lastX =-1.0f;
    private float lastY =-1.0f;
    private float lastZ =-1.0f;
    private long lastTime;
    private OnShakeListener shakeListener;
    private Context context;
    private int shakeCount = 0;
    public int activCount = 0;

    private long lastShake;
    private long lastForce;

    public interface OnShakeListener {
        public void onShake() throws InterruptedException;

    }

    public ShakeSensor(Context context) {
        this.context = context;
        resume();
    }

    public void setOnShakeListener(OnShakeListener onShakeListener){
        shakeListener = onShakeListener;
    }

    public void resume() {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager == null) {
            throw new UnsupportedOperationException("Sensors not supported");
        }
        boolean supported = sensorManager.registerListener(this, SensorManager.SENSOR_ACCELEROMETER, SensorManager.SENSOR_DELAY_GAME);
        if (!supported) {
            sensorManager.unregisterListener(this, SensorManager.SENSOR_ACCELEROMETER);
            throw new UnsupportedOperationException("Accelerometer not supported");
        }
    }

    public void pause() {
        if (sensorManager != null) {
            sensorManager.unregisterListener(this, SensorManager.SENSOR_ACCELEROMETER);
            sensorManager = null;
        }
    }
    
    @Override
    public void onSensorChanged(int i, float[] floats) {
        int sensor = i;
        float[] values = floats;
        if (sensor != SensorManager.SENSOR_ACCELEROMETER) return;
        long now = System.currentTimeMillis();

        if ((now - lastForce) > SHAKE_TIMEOUT) {
            shakeCount = 0;

        }

        if ((now - lastTime) > TIME_THRESHOLD) {
            long diff = now - lastTime;
            float speed = Math.abs(values[SensorManager.DATA_X] + values[SensorManager.DATA_Y] + values[SensorManager.DATA_Z] - lastX - lastY - lastZ) / diff * 10000;
            if (speed > FORCE_THRESHOLD) {
                if ((++shakeCount >= SHAKE_COUNT) && (now - lastShake > SHAKE_DURATION)) {
                    lastShake = now;
                    shakeCount = 0;
                    if (shakeListener != null) {
                        try {
                            shakeListener.onShake();
                        } catch (Exception e) {
                            // unhandled
                        }
                    }
                }
                lastForce = now;
            }
            lastTime = now;
            lastX = values[SensorManager.DATA_X];
            lastY = values[SensorManager.DATA_Y];
            lastZ = values[SensorManager.DATA_Z];
        }

    }

    @Override
    public void onAccuracyChanged(int i, int i1) {

    }
}
