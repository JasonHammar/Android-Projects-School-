package edu.sjsu.android.accelerometer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;

public class SimulationView extends View implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mSensor;

    private Display mDisplay;
    
    private Bitmap mField;
    private Bitmap mBasket;
    private Bitmap mBitMAP;
    private static final int BALL_SIZE = 64;
    private static final int BASKET_SIZE = 80;

    private final Particle mBall = new Particle();
    private float mSensorX;
    private float mSensorY;
    private float mSensorZ;
    private long mSensorTimeStamp;

    private float mXOrigin;
    private float mYOrigin;
    private float mZOrigin;
    private float mHorizontalBound;
    private float mVerticalBound;



    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
         mSensorX = sensorEvent.values[0];
         mSensorY = sensorEvent.values[1];
         mSensorZ = sensorEvent.values[2];
        mSensorTimeStamp = sensorEvent.timestamp;

        int rotation = mDisplay.getRotation();
        if(rotation == Surface.ROTATION_0){
            mSensorX = sensorEvent.values[0];
            mSensorY = sensorEvent.values[1];
        }else if(rotation == Surface.ROTATION_90){
            mSensorX = -sensorEvent.values[1];
            mSensorY = sensorEvent.values[0];
        }else if(rotation == Surface.ROTATION_180){
            mSensorX = sensorEvent.values[1];
            mSensorY = sensorEvent.values[0];
        }else if(rotation == Surface.ROTATION_270){
            mSensorX = sensorEvent.values[0];
            mSensorY = -sensorEvent.values[1];
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    @Override
    public void onSizeChanged(int x, int y, int oldX, int oldY){


        mXOrigin = getWidth() * 0.5f;
        mYOrigin = getHeight() * 0.5f;
        mHorizontalBound = (getWidth() - BALL_SIZE) * 0.5f;
        mVerticalBound = (getHeight() - BALL_SIZE) * 0.5f;
        Bitmap ball = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
        mBitMAP = Bitmap.createScaledBitmap(ball, BALL_SIZE, BALL_SIZE, true);
        Bitmap basket = BitmapFactory.decodeResource(getResources(), R.drawable.basket);
        mBasket = Bitmap.createScaledBitmap(basket, BASKET_SIZE, BASKET_SIZE, true);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Bitmap.Config.RGB_565;
        mField = BitmapFactory.decodeResource(getResources(), R.drawable.field, opts);


        super.onSizeChanged(x, y, oldX, oldY);
    }

    public SimulationView(Context context) {
        super(context);
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        // Initialize images from drawable
        WindowManager mWindowManager = (WindowManager)
                context.getSystemService(Context.WINDOW_SERVICE);
        mDisplay = mWindowManager.getDefaultDisplay();
        Bitmap ball = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
        mBitMAP = Bitmap.createScaledBitmap(ball, BALL_SIZE, BALL_SIZE, true);
        Bitmap basket = BitmapFactory.decodeResource(getResources(), R.drawable.basket);
        mBasket = Bitmap.createScaledBitmap(basket, BASKET_SIZE, BASKET_SIZE, true);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Bitmap.Config.RGB_565;
        mField = BitmapFactory.decodeResource(getResources(), R.drawable.field, opts);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        canvas.drawBitmap(mField, 0, 0, null);
        canvas.drawBitmap(mBasket, mXOrigin - BASKET_SIZE / 2, mYOrigin - BASKET_SIZE / 2, null);




        mBall.updatePosition(mSensorX, mSensorY, mSensorZ, mSensorTimeStamp);
        mBall.resolveCollisionWithBounds(mHorizontalBound, mVerticalBound);

        canvas.drawBitmap(mBitMAP,
                (mXOrigin - BALL_SIZE / 2) + mBall.mPosX,
                (mYOrigin - BALL_SIZE / 2) - mBall.mPosY, null);

        invalidate();
    }

    public void startSimulation(){

        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stopSimulation() {
        mSensorManager.unregisterListener(this);
    }
}


