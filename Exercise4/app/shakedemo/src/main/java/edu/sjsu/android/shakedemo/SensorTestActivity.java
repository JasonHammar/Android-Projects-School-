package edu.sjsu.android.shakedemo;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class SensorTestActivity extends AppCompatActivity {
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private boolean color = false;
    private View view;
    private long lastUpdate;
    TextView textx, texty, textz;

    private SensorEventListener sensoreventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];
            if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
                displayAccelerometer(sensorEvent);
                checkShake(sensorEvent);

            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };
    private void displayAccelerometer(SensorEvent event){
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        textx.setText("X axis" + "\t\t" + x);
        texty.setText("Y axis" + "\t\t" + y);
        textz.setText("Z axis" + "\t\t" + z);
    }

    private void checkShake(SensorEvent event){
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        float accelerationSquareRoot = (x * x + y * y + z * z)
                / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
        long actualTime = System.currentTimeMillis();
        if(Math.abs(accelerationSquareRoot) >= 2){
            if(actualTime - lastUpdate < 200){
                return;
            }
            lastUpdate = actualTime;
            Toast.makeText(this, "Don't shake me!", Toast.LENGTH_SHORT).show();
            if(color){
                view.setBackgroundColor(Color.BLUE);
            }else{
                view.setBackgroundColor(Color.RED);
            }
            color = !color;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_test);

        textx = findViewById(R.id.xval);
        texty = findViewById(R.id.yval);
        textz = findViewById(R.id.zval);



        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        view = findViewById(R.id.textView);
        view.setBackgroundColor(Color.BLUE);
    }

    protected void onResume(){
        super.onResume();
        mSensorManager.registerListener(sensoreventListener, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause(){
        super.onPause();
        mSensorManager.unregisterListener(sensoreventListener);
    }
}