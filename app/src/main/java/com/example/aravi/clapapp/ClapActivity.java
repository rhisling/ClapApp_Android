package com.example.aravi.clapapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.constraint.solver.SolverVariable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ClapActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private boolean isSensorAvailable;
    private MediaPlayer mediaPlayer;
    private float distanceToPhone;
    private Sensor sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clap);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null) {
            sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            isSensorAvailable = true;
        } else {
            isSensorAvailable = false;
        }
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.clap);

    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        distanceToPhone = sensorEvent.values[0];
        if (distanceToPhone < sensor.getMaximumRange()) {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
            } else {
                mediaPlayer.stop();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(isSensorAvailable){
            mSensorManager.unregisterListener(this,sensor);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isSensorAvailable){
            mSensorManager.registerListener(this,sensor,mSensorManager.SENSOR_DELAY_UI);
        }
    }
}
