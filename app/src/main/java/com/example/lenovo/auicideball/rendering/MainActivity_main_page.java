package com.example.lenovo.auicideball.rendering;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.example.lenovo.auicideball.R;

public class MainActivity_main_page extends AppCompatActivity {
    private Button mStart_game_button;

//    MynewView mynewView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_mainpage);
        mStart_game_button = (Button) findViewById(R.id.start_game_button);
        mStart_game_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent i = new Intent(MainActivity_main_page.this,MainActivity_game_page.class);
               startActivity(i);
            }
        });

    }

//    public class MynewView extends SurfaceView implements SurfaceHolder,SurfaceHolder.Callback,Runnable,SensorEventListener{
//
//        public MynewView(Context context){
//            super(context);
//
//        }
//
//        private void Down(){
//
//        }
//
//        @Override
//        public void onSensorChanged(SensorEvent sensorEvent) {
//
//        }
//
//        @Override
//        public void surfaceCreated(SurfaceHolder surfaceHolder) {
//
//        }
//
//        @Override
//        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
//
//        }
//
//        @Override
//        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
//
//        }
//
//        @Override
//        public void run() {
//
//        }
//
//        @Override
//        public void onAccuracyChanged(Sensor sensor, int i) {
//
//        }
//
//        @Override
//        public void addCallback(Callback callback) {
//
//        }
//
//        @Override
//        public void removeCallback(Callback callback) {
//
//        }
//
//        @Override
//        public boolean isCreating() {
//            return false;
//        }
//
//        @Override
//        public void setType(int i) {
//
//        }
//
//        @Override
//        public void setFixedSize(int i, int i1) {
//
//        }
//
//        @Override
//        public void setSizeFromLayout() {
//
//        }
//
//        @Override
//        public void setFormat(int i) {
//
//        }
//
//        @Override
//        public Canvas lockCanvas() {
//            return null;
//        }
//
//        @Override
//        public Canvas lockCanvas(Rect rect) {
//            return null;
//        }
//
//        @Override
//        public void unlockCanvasAndPost(Canvas canvas) {
//
//        }
//
//        @Override
//        public Rect getSurfaceFrame() {
//            return null;
//        }
//
//        @Override
//        public Surface getSurface() {
//            return null;
//        }
//    }
}
