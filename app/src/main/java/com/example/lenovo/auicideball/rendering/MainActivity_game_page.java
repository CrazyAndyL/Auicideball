package com.example.lenovo.auicideball.rendering;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.example.lenovo.auicideball.backstage.Boom_coordinate;
import com.example.lenovo.auicideball.backstage.Lightning_coordinate;
import com.example.lenovo.auicideball.R;
import com.example.lenovo.auicideball.backstage.Score_coordinate;

import java.util.ArrayList;
import java.util.Random;

import static android.hardware.SensorManager.DATA_X;

public class MainActivity_game_page extends AppCompatActivity {

    MyView mAnimView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_gamepage);


        mAnimView = new MyView(this);
        setContentView(mAnimView);


    }

    public class MyView extends SurfaceView implements SurfaceHolder.Callback,Runnable,SensorEventListener{

        /*游戏画笔*/
        Paint mPaint;
        SurfaceHolder mSurfaceHolder;

        /*游戏画布*/
        Canvas mCanvas ;

        /*控制游戏循环*/
        boolean mIsRunning = false;

        /*SensorManager管理器*/
        private SensorManager mSensorMgr;
        Sensor mSensor;

        /*手机屏幕高和宽*/
        int mScreenWidth = 0;
        int mScreenHeight = 0;

        /*小球资源文件越界区域*/
        private int mScreenBallWidth = 0;
        private int mScreenBallHeight = 0;

        /*小球资源文件*/
        private Bitmap mbitmapBall;

        /*背景图片资源文件*/
        private Bitmap mbitmapBg;

        /*闪电速度资源*/
        private Bitmap mbitmapLightning;

        /*速度值*/
        float a = 2;

        /*分数资源图片*/
        private Bitmap mbitmapSoced;

        /*地雷资源图片*/
        private Bitmap mbitmapBoom;

        /*小球的坐标位置*/
        private float mPosX = 600;
        private float mPosY = 200;

        /*分数数组*/
        ArrayList<Score_coordinate> score_coordinates = new ArrayList<>();

        /*闪电数组*/
        ArrayList<Lightning_coordinate> lightning_coordinates = new ArrayList<>();

        /*地雷数组*/
        ArrayList<Boom_coordinate> boom_coordinates = new ArrayList<>();

        /*随机数*/
        Random random = new Random();

        /*坐标可误差值*/
        private float mK= 50;

        /*游戏得分*/
        int game_Score = 0;

        /*重力感应X轴 Y轴 Z轴的重力值*/
        private float mGX = 0;
        private float mGY = 0;
        private float mGZ = 0;



        public MyView(Context context) {
            super(context);
            /*设置当前View拥有控制焦点*/
            this.setFocusable(true);
            /*设置当前View拥有出触摸时间*/
            this.setFocusableInTouchMode(true);
            /*获取SurfaceHolder对象*/
            mSurfaceHolder = this.getHolder();
            /*将mSurfaceHolder添加到Callback回调函数中*/
            mSurfaceHolder.addCallback(this);
            /*创建画布*/
            mCanvas = new Canvas();
            /*创建曲线画笔*/
            mPaint = new Paint();
            mPaint.setColor(Color.parseColor("#ffffff"));
            mPaint.setTextSize(50);
            mPaint.setTypeface(Typeface.DEFAULT_BOLD);

            /*加载小球资源*/
            mbitmapBall = BitmapFactory.decodeResource(this.getResources(),R.drawable.ball);
            /*加载背景资源*/
            mbitmapBg = BitmapFactory.decodeResource(this.getResources(),R.drawable.bg);
            /*加载地雷资源*/
            mbitmapBoom = BitmapFactory.decodeResource(this.getResources(),R.drawable.boom);
            /*闪电速度资源*/
            mbitmapLightning = BitmapFactory.decodeResource(this.getResources(),R.drawable.lightning);
            /*加载分数随机资源*/
            mbitmapSoced = BitmapFactory.decodeResource(this.getResources(),R.drawable.scored);

            /*添加分数*/
            for(int i = 0 ; i < 30 ; i++){
                Score_coordinate score_coordinate =new Score_coordinate();
                score_coordinate.setmSX(random.nextInt(2000)%(2000-5+1) + 5);
                score_coordinate.setmSY(random.nextInt(1000)%(1000-5+1) + 5);
                score_coordinates.add(score_coordinate);
            }

            /*添加闪电*/
            for (int j = 0 ; j < 10 ; j++){
                Lightning_coordinate lightning_coordinate = new Lightning_coordinate();
                lightning_coordinate.setmLX(random.nextInt(2000)%(2000-5+1) + 5);
                lightning_coordinate.setmLY(random.nextInt(1000)%(1000-5+1) + 5);
                lightning_coordinates.add(lightning_coordinate);
            }

            /*添加地雷*/
            for (int k = 0 ; k < 5 ; k++){
                Boom_coordinate boom_coordinate = new Boom_coordinate();
                boom_coordinate.setmBX(random.nextInt(2000)%(2000-5+1) + 5);
                boom_coordinate.setmBY(random.nextInt(2000)%(2000-5+1) + 5);
                boom_coordinates.add(boom_coordinate);
            }

            /*得到SensorManage对象*/
            mSensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
            /*加速度传感器*/
            mSensor = mSensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            /*listener 的灵敏度 SENSOR_DELAY_GAME 游戏中的速度*/
            mSensorMgr.registerListener(this,mSensor,SensorManager.SENSOR_DELAY_UI);
        }

        private void Draw(){

            /*得到当前屏幕宽高*/
            mScreenWidth = this.getWidth();
            mScreenHeight = this.getHeight();

            /*背景*/
            mCanvas.drawBitmap(mbitmapBg,0,0,mPaint);

            /*描绘地雷*/
            for (int k = 0 ; k < 5 ; k++){
                mCanvas.drawBitmap(mbitmapBoom,boom_coordinates.get(k).getmBX(),
                        boom_coordinates.get(k).getmBY(),
                        mPaint);
            }

            /*描绘分数*/
            for (int i = 0 ; i < 30 ; i++){
                mCanvas.drawBitmap(mbitmapSoced,
                        score_coordinates.get(i).getmSX(),
                        score_coordinates.get(i).getmSY(),
                        mPaint);
            }

            /*绘制闪电*/
            for (int j = 0 ; j < 10 ; j++){
                mCanvas.drawBitmap(mbitmapLightning,
                        lightning_coordinates.get(j).getmLX(),
                        lightning_coordinates.get(j).getmLY(),
                        mPaint);
            }

            /*小球*/
            mCanvas.drawBitmap(mbitmapBall,mPosX,mPosY,mPaint);

            /*小球误差范围内加速度*/
            for (int j = 0 ; j < 10 ; j++){
                if (Math.abs((mPosX+30)-lightning_coordinates.get(j).getmLX())<=mK){
                    if (Math.abs((mPosY+40)-lightning_coordinates.get(j).getmLY())<=mK){
//                        mCanvas.drawText("速度加快",600,500,mPaint);
                        lightning_coordinates.get(j).setmLX(random.nextInt(mScreenWidth)%(mScreenWidth-5+1) + 5);
                        lightning_coordinates.get(j).setmLY(random.nextInt(mScreenHeight)%(mScreenHeight-5+1) + 5);
                        a= (float) (a+0.3);

                    }
                }
            }

            /*小球误差值内加分*/
            for (int i = 0 ; i<30 ; i++){
                if (Math.abs((mPosX+30)-score_coordinates.get(i).getmSX())<=mK){
                    if (Math.abs((mPosY+40)-score_coordinates.get(i).getmSY())<=mK){
                        score_coordinates.get(i).setmSX(random.nextInt(mScreenWidth)%(mScreenWidth-5+1) + 5);
                        score_coordinates.get(i).setmSY(random.nextInt(mScreenHeight)%(mScreenHeight-5+1) + 5);
                        game_Score++;
                    }
                }
            }

            /*小球游戏结束*/
            if (mIsRunning == false){
                mCanvas.drawText("游戏结束！！！",500,200,mPaint);
                //TODO
            }

            mCanvas.drawText("分数"+game_Score,20,50,mPaint);
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            /*开始游戏主循环线程*/
            mIsRunning = true;
            new Thread(this).start();
            /*得到当前屏幕宽高*/
            mScreenWidth = this.getWidth();
            mScreenHeight = this.getHeight();
            /*得到小球越界区域*/
            mScreenBallWidth = mScreenWidth - mbitmapBall.getWidth();
            mScreenBallHeight = mScreenHeight - mbitmapBall.getHeight();

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            mIsRunning = false;

        }

        @Override
        public void run() {
            while (mIsRunning){
                /*在这里加上安全锁*/
                synchronized (mSurfaceHolder){
                    /*拿到当前画布 然后锁定*/
                    mCanvas = mSurfaceHolder.lockCanvas();
                    Draw();

                    /*绘制完成后解锁显示*/
                    mSurfaceHolder.unlockCanvasAndPost(mCanvas);
                }

                /*小球吃闪电*/
                for (int j = 0 ; j < 10 ; j++){
                    if (Math.abs((mPosX+30)-lightning_coordinates.get(j).getmLX())<=mK){
                        if (Math.abs((mPosY+40)-lightning_coordinates.get(j).getmLY())<=mK){
                            Draw();
                        }
                    }
                }

                /*小球得分*/
                for (int i = 0 ; i<30 ; i++){
                    if (Math.abs((mPosX+30)-score_coordinates.get(i).getmSX())<=mK){
                        if (Math.abs((mPosY+40)-score_coordinates.get(i).getmSY())<=mK){
                           Draw();
                        }
                    }
                }

                /*小球吃雷*/
                for (int k =  0 ; k < 5 ; k++){
                    if (Math.abs((mPosX+30)-boom_coordinates.get(k).getmBX())<=mK){
                        if (Math.abs((mPosY+40)-boom_coordinates.get(k).getmBY())<=mK){
                            surfaceDestroyed(mSurfaceHolder);
                            Draw();
                        }
                    }
                }

            }

            if((mIsRunning == false)){
                Draw();
            }

    }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {}

        @Override
        public void onSensorChanged(SensorEvent event) {
            mGY = event.values[DATA_X];
            mGX = -event.values[SensorManager.DATA_Y];
            mGZ = event.values[SensorManager.DATA_Z];

            /*速度加快*/
            mPosX-=mGX * a;
            mPosY+=mGY * a;

            /*是否超出边界*/
            if (mPosX < 0){
                mPosX = 0;
                surfaceDestroyed(mSurfaceHolder);
            }else if (mPosX > mScreenBallWidth){
                mPosX = mScreenBallWidth;
                surfaceDestroyed(mSurfaceHolder);
            }
            if (mPosY < 0){
                mPosY = 0;
                surfaceDestroyed(mSurfaceHolder);
            }else if (mPosY > mScreenBallHeight){
                mPosY = mScreenBallHeight;
                surfaceDestroyed(mSurfaceHolder);
            }

        }
    }
}
