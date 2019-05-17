package com.josancamon19.compass;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.josancamon19.compass.custom_views.CompassView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {


    private CompassView compass;

    SensorManager sensorManager;

    private float[] valuesAccelerometer;
    private float[] valuesMagneticField;

    private float[] matrixR;
    private float[] matrixI;
    private float[] matrixValues;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        compass = findViewById(R.id.compass);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        valuesAccelerometer = new float[3];
        valuesMagneticField = new float[3];

        matrixR = new float[9];
        matrixI = new float[9];
        matrixValues = new float[3];
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                // This array copy is like a for ... [0,3]
                System.arraycopy(event.values, 0, valuesAccelerometer, 0, 3);
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                System.arraycopy(event.values, 0, valuesMagneticField, 0, 3);
                break;
        }
        boolean success = SensorManager.getRotationMatrix(
                matrixR,
                matrixI,
                valuesAccelerometer,
                valuesMagneticField);
        if (success) {
            SensorManager.getOrientation(matrixR, matrixValues);
            compass.update(matrixValues[0]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
