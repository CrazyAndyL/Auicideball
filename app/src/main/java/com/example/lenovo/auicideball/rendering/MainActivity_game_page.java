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
import android.support.annotation.IntRange;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.lenovo.auicideball.backstage.Boom_coordinate;
import com.example.lenovo.auicideball.backstage.Lightning_coordinate;
import com.example.lenovo.auicideball.R;
import com.example.lenovo.auicideball.backstage.MazePoint;
import com.example.lenovo.auicideball.backstage.Score_coordinate;
import com.example.lenovo.auicideball.backstage.Wall_coordinate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Stack;

import static android.hardware.SensorManager.DATA_X;

public class  MainActivity_game_page extends AppCompatActivity {

    MyView mAnimView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_gamepage);

        mAnimView = new MyView(this);
        setContentView(mAnimView);
    }

    public class MyView extends SurfaceView implements SurfaceHolder.Callback,Runnable,SensorEventListener{

        int Soced = 50;
        int Lightning = 50 ;
        int Boom = 50 ;

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

        /*结束资源文件*/
        private Bitmap mbitmapEnd;

        /*背景图片资源文件*/
        private Bitmap mbitmapBg;

        /*闪电速度资源*/
        private Bitmap mbitmapLightning;

        /*速度值*/
        float a = (float) 0.5;

        /*分数资源图片*/
        private Bitmap mbitmapSoced;

        /*地雷资源图片*/
        private Bitmap mbitmapBoom;

        /*wall资源图片*/
        private Bitmap mbitmapWall;

        /*小球的坐标位置*/
        public float mPosX = 80;
        public float mPosY = 100;

        /*分数数组*/
        ArrayList<Score_coordinate> score_coordinates = new ArrayList<>();

        /*闪电数组*/
        ArrayList<Lightning_coordinate> lightning_coordinates = new ArrayList<>();

        /*地雷数组*/
        ArrayList<Boom_coordinate> boom_coordinates = new ArrayList<>();

        /*Wall数组*/
        ArrayList<Wall_coordinate> wall_coordinates = new ArrayList<>();

        HashMap<Wall_coordinate,String> map = new HashMap<>();

        /*随机数*/
        Random random = new Random();

        /*坐标可误差值*/
        private float mK= 48;

        /*游戏得分*/
        int game_Score = 0;

        /*游戏速度值*/
        int game_Linght = 1;

        /*游戏地雷个数*/
        int game_Boom = 1;

        /*重力感应X轴 Y轴 Z轴的重力值*/
        private float mGX = 0;
        private float mGY = 0;
        private float mGZ = 0;

        /*迷宫的长宽*/
        int height =9;
        int width = 5;
        Maze mazes = new Maze(height,width);


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
            /*加载wall资源*/
            mbitmapWall = BitmapFactory.decodeResource(this.getResources(),R.drawable.wall);
            /*加载地雷资源*/
            mbitmapBoom = BitmapFactory.decodeResource(this.getResources(),R.drawable.boom);
            /*闪电速度资源*/
            mbitmapLightning = BitmapFactory.decodeResource(this.getResources(),R.drawable.lightning);
            /*加载分数随机资源*/
            mbitmapSoced = BitmapFactory.decodeResource(this.getResources(),R.drawable.scored);
            /*加载目标结束*/
            mbitmapEnd = BitmapFactory.decodeResource(this.getResources(),R.drawable.end);

            /*迷宫*/
            mazes.traversal();
            mazes.create();
            mazes.findPath();
            /*绘制地图*/
            for (int i = 0 ; i < 2*height+1 ; i++){
                for (int j = 0 ; j < 2*width+1 ; j++){
                    if (mazes.maze[i][j] == mazes.gridWall){
                        mCanvas.drawBitmap(mbitmapWall,i*70,j*70,mPaint);
                        Wall_coordinate wall_coordinate = new Wall_coordinate();
                        wall_coordinate.setmWX(i*70);
                        wall_coordinate.setmWY(j*70);
                        wall_coordinates.add(wall_coordinate);
                        map.put(wall_coordinate,"IsWall");

                    }
                }
            }
            mazes.reset();

            /*添加分数*/
            for(int i = 0 ; i < Soced ; i++){
                Score_coordinate score_coordinate =new Score_coordinate();
                score_coordinate.setmSX(random.nextInt(1500)%(1500-5+1) + 5);
                score_coordinate.setmSY(random.nextInt(1000)%(1000-5+1) + 5);
                if (score_coordinate.getmSX()< mScreenWidth*0.3){
                    for (int j = 0 ; j < wall_coordinates.size()*0.3 ; j ++){
                        if(Math.abs(score_coordinate.getmSX() - wall_coordinates.get(j).getmWX())<50 && Math.abs(score_coordinate.getmSY() - wall_coordinates.get(j).getmWY())<50){
                            score_coordinate.setmSX(wall_coordinates.get(j).getmWX());
                            score_coordinate.setmSY(wall_coordinates.get(j).getmWY());
                        }
                    }
                }
                else if (score_coordinate.getmSX()>= mScreenWidth*0.3 && score_coordinate.getmSX()< mScreenWidth*0.6){
                    for (int j = (int) (mScreenWidth*0.3); j < wall_coordinates.size()*0.3 ; j ++){
                        if(Math.abs(score_coordinate.getmSX() - wall_coordinates.get(j).getmWX())<50 && Math.abs(score_coordinate.getmSY() - wall_coordinates.get(j).getmWY())<50){
                            score_coordinate.setmSX(wall_coordinates.get(j).getmWX());
                            score_coordinate.setmSY(wall_coordinates.get(j).getmWY());
                        }
                    }
                }else {
                    for (int j = (int) (mScreenWidth*0.6); j < wall_coordinates.size() ; j ++){
                        if(Math.abs(score_coordinate.getmSX() - wall_coordinates.get(j).getmWX())<50 && Math.abs(score_coordinate.getmSY() - wall_coordinates.get(j).getmWY())<50){
                            score_coordinate.setmSX(wall_coordinates.get(j).getmWX());
                            score_coordinate.setmSY(wall_coordinates.get(j).getmWY());
                        }
                    }
                }
                score_coordinates.add(score_coordinate);
            }

            /*添加闪电*/
            for (int i = 0 ; i < Lightning ; i++){
                Lightning_coordinate lightning_coordinate = new Lightning_coordinate();
                lightning_coordinate.setmLX(random.nextInt(1500)%(1500-5+1) + 5);
                lightning_coordinate.setmLY(random.nextInt(1000)%(1000-5+1) + 5);
                if (lightning_coordinate.getmLX()< mScreenWidth*0.3){
                    for (int j = 0 ; j < wall_coordinates.size()*0.3 ; j ++){
                        if (Math.abs(lightning_coordinate.getmLX() - wall_coordinates.get(j).getmWX())<50 && Math.abs(lightning_coordinate.getmLY() - wall_coordinates.get(j).getmWY())<50){
                            lightning_coordinate.setmLX(wall_coordinates.get(j).getmWX());
                            lightning_coordinate.setmLY(wall_coordinates.get(j).getmWY());
                        }
                    }
                }
                else if (lightning_coordinate.getmLX()>= mScreenWidth*0.3 && lightning_coordinate.getmLX()< mScreenWidth*0.6){
                    for (int j = (int) (mScreenWidth*0.3); j < wall_coordinates.size()*0.3 ; j ++){
                        if (Math.abs(lightning_coordinate.getmLX() - wall_coordinates.get(j).getmWX())<50 && Math.abs(lightning_coordinate.getmLY() - wall_coordinates.get(j).getmWY())<50){
                            lightning_coordinate.setmLX(wall_coordinates.get(j).getmWX());
                            lightning_coordinate.setmLY(wall_coordinates.get(j).getmWY());
                        }
                    }
                }else {
                    for (int j = (int) (mScreenWidth*0.6); j < wall_coordinates.size() ; j ++){
                        if (Math.abs(lightning_coordinate.getmLX() - wall_coordinates.get(j).getmWX())<50 && Math.abs(lightning_coordinate.getmLY() - wall_coordinates.get(j).getmWY())<50){
                            lightning_coordinate.setmLX(wall_coordinates.get(j).getmWX());
                            lightning_coordinate.setmLY(wall_coordinates.get(j).getmWY());
                        }
                    }
                }
                lightning_coordinates.add(lightning_coordinate);
            }

            /*添加地雷*/
            for (int k = 0 ; k < Boom ; k++){
                Boom_coordinate boom_coordinate = new Boom_coordinate();
                boom_coordinate.setmBX(random.nextInt(1500)%(1500-5+1) + 5);
                boom_coordinate.setmBY(random.nextInt(1000)%(1000-5+1) + 5);
                if (boom_coordinate.getmBX()< mScreenWidth*0.3){
                    for (int j = 0 ; j < wall_coordinates.size()*0.3 ; j ++) {
                        if (Math.abs(boom_coordinate.getmBX() - wall_coordinates.get(j).getmWX()) <50 && Math.abs(boom_coordinate.getmBY() - wall_coordinates.get(j).getmWY()) <50){
                            boom_coordinate.setmBX(wall_coordinates.get(j).getmWX());
                            boom_coordinate.setmBY(wall_coordinates.get(j).getmWY());
                        }
                    }
                }
                else if (boom_coordinate.getmBX()>= mScreenWidth*0.3 && boom_coordinate.getmBX()< mScreenWidth*0.6){
                    for (int j = (int) (mScreenWidth*0.3); j < wall_coordinates.size()*0.3 ; j ++){
                        if(Math.abs(boom_coordinate.getmBX() - wall_coordinates.get(j).getmWX()) <50 && Math.abs(boom_coordinate.getmBY() - wall_coordinates.get(j).getmWY()) <50) {
                            boom_coordinate.setmBX(wall_coordinates.get(j).getmWX());
                            boom_coordinate.setmBY(wall_coordinates.get(j).getmWY());
                        }
                    }
                }else {
                    for (int j = (int) (mScreenWidth*0.6); j < wall_coordinates.size() ; j ++){
                        if (Math.abs(boom_coordinate.getmBX() - wall_coordinates.get(j).getmWX()) <50 && Math.abs(boom_coordinate.getmBY() - wall_coordinates.get(j).getmWY()) <50){
                            boom_coordinate.setmBX(wall_coordinates.get(j).getmWX());
                            boom_coordinate.setmBY(wall_coordinates.get(j).getmWY());
                        }
                    }
                }
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
            for (int k = 0 ; k < Boom ; k++){
                mCanvas.drawBitmap(mbitmapBoom,
                        boom_coordinates.get(k).getmBX(),
                        boom_coordinates.get(k).getmBY(),
                        mPaint);
            }

            /*描绘分数*/
            for (int i = 0 ; i < Soced ; i++){
                mCanvas.drawBitmap(mbitmapSoced,
                        score_coordinates.get(i).getmSX(),
                        score_coordinates.get(i).getmSY(),
                        mPaint);
            }

            /*绘制闪电*/
            for (int j = 0 ; j < Lightning ; j++){
                mCanvas.drawBitmap(mbitmapLightning,
                        lightning_coordinates.get(j).getmLX(),
                        lightning_coordinates.get(j).getmLY(),
                        mPaint);
            }

            /*绘制地图*/
            for (int i = 0 ; i < wall_coordinates.size(); i++){
                mCanvas.drawBitmap(mbitmapWall,wall_coordinates.get(i).getmWX(),wall_coordinates.get(i).getmWY(),mPaint);
            }

            /*结束*/
            mCanvas.drawBitmap(mbitmapEnd,1190,620,mPaint);

            /*小球*/
            mCanvas.drawBitmap(mbitmapBall,mPosX,mPosY,mPaint);

            /*小球误差范围内加速度*/
            for (int j = 0 ; j < Lightning ; j++){
                if (mPosX-lightning_coordinates.get(j).getmLX()<=mK && mPosX > lightning_coordinates.get(j).getmLX()){

                    if (mPosY-lightning_coordinates.get(j).getmLY()<=mK && mPosY > lightning_coordinates.get(j).getmLY()){
                        lightning_coordinates.get(j).setmLX(2000);
                        lightning_coordinates.get(j).setmLY(1000);
                        a= (float) (a+0.3);
                        game_Linght++;

                    }
                    if (lightning_coordinates.get(j).getmLY()-mPosY<=mK && mPosY < lightning_coordinates.get(j).getmLY()){
                        lightning_coordinates.get(j).setmLX(2000);
                        lightning_coordinates.get(j).setmLY(1000);
                        a= (float) (a+0.3);
                        game_Linght++;
                    }
                }
                if (lightning_coordinates.get(j).getmLX()-mPosX<=mK && mPosX < lightning_coordinates.get(j).getmLX()){

                    if (mPosY-lightning_coordinates.get(j).getmLY()<=mK && mPosY > lightning_coordinates.get(j).getmLY()){
                        lightning_coordinates.get(j).setmLX(2000);
                        lightning_coordinates.get(j).setmLY(1000);
                        a= (float) (a+0.3);
                        game_Linght++;

                    }
                    else if (lightning_coordinates.get(j).getmLY()-mPosY<=mK && mPosY < lightning_coordinates.get(j).getmLY()){
                        lightning_coordinates.get(j).setmLX(2000);
                        lightning_coordinates.get(j).setmLY(1000);
                        a= (float) (a+0.3);
                        game_Linght++;
                    }
                }
            }

            /*小球误差值内加分*/
            for (int i = 0 ; i<Soced ; i++){
                if (mPosX-score_coordinates.get(i).getmSX()<=mK && mPosX > score_coordinates.get(i).getmSX()){

                    if (mPosY-score_coordinates.get(i).getmSY()<=mK && mPosY > score_coordinates.get(i).getmSY()){
                        score_coordinates.get(i).setmSX(2000);
                        score_coordinates.get(i).setmSY(1000);
                        game_Score++;
                    }
                    else if (score_coordinates.get(i).getmSY()-mPosY<=mK && mPosY < score_coordinates.get(i).getmSY()){
                        score_coordinates.get(i).setmSX(2000);
                        score_coordinates.get(i).setmSY(1000);
                        game_Score++;
                    }
                }
                else if (score_coordinates.get(i).getmSX()-mPosX<=mK && mPosX < score_coordinates.get(i).getmSX()){

                    if (mPosY-score_coordinates.get(i).getmSY()<=mK && mPosY > score_coordinates.get(i).getmSY()){
                        score_coordinates.get(i).setmSX(2000);
                        score_coordinates.get(i).setmSY(1000);
                        game_Score++;
                    }
                    else if (score_coordinates.get(i).getmSY()-mPosY<=mK && mPosY < score_coordinates.get(i).getmSY()){
                        score_coordinates.get(i).setmSX(2000);
                        score_coordinates.get(i).setmSY(1000);
                        game_Score++;
                    }
                }
            }

            /*小球误差值内加地雷*/
            for (int i = 0 ; i<Boom ; i++){
                if (mPosX-boom_coordinates.get(i).getmBX()<=mK && mPosX > boom_coordinates.get(i).getmBX()){

                    if (mPosY-boom_coordinates.get(i).getmBY()<=mK && mPosY > boom_coordinates.get(i).getmBY()){
                        boom_coordinates.get(i).setmBX(2000);
                        boom_coordinates.get(i).setmBY(1000);
                        game_Boom++;
                    }
                    else if (boom_coordinates.get(i).getmBY()-mPosY<=mK && mPosY < boom_coordinates.get(i).getmBY()){
                        boom_coordinates.get(i).setmBX(2000);
                        boom_coordinates.get(i).setmBY(1000);
                        game_Boom++;
                    }
                }
                else if (boom_coordinates.get(i).getmBX()-mPosX<=mK && mPosX < boom_coordinates.get(i).getmBX()){

                    if (mPosY-boom_coordinates.get(i).getmBY()<=mK && mPosY > boom_coordinates.get(i).getmBY()){
                        boom_coordinates.get(i).setmBX(2000);
                        boom_coordinates.get(i).setmBY(1000);
                        game_Boom++;
                    }
                    else if (boom_coordinates.get(i).getmBY()-mPosY<=mK && mPosY < boom_coordinates.get(i).getmBY()){
                        boom_coordinates.get(i).setmBX(2000);
                        boom_coordinates.get(i).setmBY(1000);
                        game_Boom++;
                    }
                }
            }

            /*小球游戏结束*/
            if (mIsRunning == false){
                Intent intent = new Intent(MainActivity_game_page.this,MainActivity_game_end_page.class);
                startActivity(intent);
            }

            mCanvas.drawText("分数"+game_Score,20,50,mPaint);
            mCanvas.drawText("雷数"+game_Boom,220,50,mPaint);
            if (game_Linght<=10){
                mCanvas.drawText("速度"+game_Linght,420,50,mPaint);
            }else {
                mCanvas.drawText("速度"+"MAX",420,50,mPaint);
            }
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
            }

            if(mIsRunning == false){
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

            /*是否完成迷宫*/
            if (mPosX-1190<=40 && mPosX > 1190){
                if (mPosY - 620 <= 40 && mPosY > 620){
                    surfaceDestroyed(mSurfaceHolder);
                }
                else if (620 - mPosY<=40 && mPosY < 620){
                    surfaceDestroyed(mSurfaceHolder);
                }
            }else if (1190-mPosX<=40 && 1190 > mPosX){
                if (mPosY - 620 <= 40 && mPosY > 620){
                    surfaceDestroyed(mSurfaceHolder);
                }
                else if (620 - mPosY<=40 && mPosY < 620){
                    surfaceDestroyed(mSurfaceHolder);
                }
            }

            /*是否碰到wall*/
            if (mPosX < (int)(mScreenWidth*0.3)){
                for (int i = 0 ; i < (int)(wall_coordinates.size()*0.3)+20 ; i++){

                    if (mPosX - wall_coordinates.get(i).getmWX() <= 50 && mPosX>wall_coordinates.get(i).getmWX()){

                        if (mPosY - wall_coordinates.get(i).getmWY() <= 50 && mPosY>wall_coordinates.get(i).getmWY()){
                            if (game_Boom>0){
                                wall_coordinates.get(i).setmWX(2000);
                                wall_coordinates.get(i).setmWY(1000);
                                game_Boom--;
                            }else {
                                surfaceDestroyed(mSurfaceHolder);
                            }

                        }
                        else if (wall_coordinates.get(i).getmWY() - mPosY <= 50 && mPosY<wall_coordinates.get(i).getmWY()){
                            if (game_Boom>0){
                                wall_coordinates.get(i).setmWX(2000);
                                wall_coordinates.get(i).setmWY(1000);
                                game_Boom--;
                            }else {
                                surfaceDestroyed(mSurfaceHolder);
                            }
                        }
                    }
                    else if (wall_coordinates.get(i).getmWX() - mPosX <= 50 && mPosX<wall_coordinates.get(i).getmWX()){

                        if (wall_coordinates.get(i).getmWY() - mPosY <= 50 && mPosY<wall_coordinates.get(i).getmWY()){
                            if (game_Boom>0){
                                wall_coordinates.get(i).setmWX(2000);
                                wall_coordinates.get(i).setmWY(1000);
                                game_Boom--;
                            }else {
                                surfaceDestroyed(mSurfaceHolder);
                            }
                        }
                        else if (mPosY - wall_coordinates.get(i).getmWY() <= 50 && mPosY>wall_coordinates.get(i).getmWY()){
                            if (game_Boom>0){
                                wall_coordinates.get(i).setmWX(2000);
                                wall_coordinates.get(i).setmWY(1000);
                                game_Boom--;
                            }else {
                                surfaceDestroyed(mSurfaceHolder);
                            }
                        }
                    }
                }
            }else if (mPosX >=(int)( mScreenWidth*0.3) && mPosX < (int)(mScreenWidth*0.6)){
                for (int i = (int) (wall_coordinates.size()*0.3); i < (int)(wall_coordinates.size()*0.6)+20 ; i ++){

                    if (mPosX - wall_coordinates.get(i).getmWX() <= 50 && mPosX>wall_coordinates.get(i).getmWX()){

                        if (mPosY - wall_coordinates.get(i).getmWY() <= 50 && mPosY>wall_coordinates.get(i).getmWY()){
                            if (game_Boom>0){
                                wall_coordinates.get(i).setmWX(2000);
                                wall_coordinates.get(i).setmWY(1000);
                                game_Boom--;
                            }else {
                                surfaceDestroyed(mSurfaceHolder);
                            }
                        }
                        else if (wall_coordinates.get(i).getmWY() - mPosY <= 50 && mPosY<wall_coordinates.get(i).getmWY()){
                            if (game_Boom>0){
                                wall_coordinates.get(i).setmWX(2000);
                                wall_coordinates.get(i).setmWY(1000);
                                game_Boom--;
                            }else {
                                surfaceDestroyed(mSurfaceHolder);
                            }
                        }
                    }
                    else if (wall_coordinates.get(i).getmWX() - mPosX <= 50 && mPosX<wall_coordinates.get(i).getmWX()){

                        if (wall_coordinates.get(i).getmWY() - mPosY <= 50 && mPosY<wall_coordinates.get(i).getmWY()){
                            if (game_Boom>0){
                                wall_coordinates.get(i).setmWX(2000);
                                wall_coordinates.get(i).setmWY(1000);
                                game_Boom--;
                            }else {
                                surfaceDestroyed(mSurfaceHolder);
                            }
                        }
                        else if (mPosY - wall_coordinates.get(i).getmWY() <= 50 && mPosY>wall_coordinates.get(i).getmWY()){
                            if (game_Boom>0){
                                wall_coordinates.get(i).setmWX(2000);
                                wall_coordinates.get(i).setmWY(1000);
                                game_Boom--;
                            }else {
                                surfaceDestroyed(mSurfaceHolder);
                            }
                        }
                    }
                }
            }else {
                for (int i = (int) (wall_coordinates.size()*0.6); i < wall_coordinates.size() ; i ++){

                    if (mPosX - wall_coordinates.get(i).getmWX() <= 50 && mPosX>wall_coordinates.get(i).getmWX()){

                        if (mPosY - wall_coordinates.get(i).getmWY() <= 50 && mPosY>wall_coordinates.get(i).getmWY()){
                            if (game_Boom>0){
                                wall_coordinates.get(i).setmWX(2000);
                                wall_coordinates.get(i).setmWY(1000);
                                game_Boom--;
                            }else {
                                surfaceDestroyed(mSurfaceHolder);
                            }
                        }
                        else if (wall_coordinates.get(i).getmWY() - mPosY <= 50 && mPosY<wall_coordinates.get(i).getmWY()){
                            if (game_Boom>0){
                                wall_coordinates.get(i).setmWX(2000);
                                wall_coordinates.get(i).setmWY(1000);
                                game_Boom--;
                            }else {
                                surfaceDestroyed(mSurfaceHolder);
                            }
                        }
                    }
                    else if (wall_coordinates.get(i).getmWX() - mPosX <= 50 && mPosX<wall_coordinates.get(i).getmWX()){

                        if (wall_coordinates.get(i).getmWY() - mPosY <= 50 && mPosY<wall_coordinates.get(i).getmWY()){
                            if (game_Boom>0){
                                wall_coordinates.get(i).setmWX(2000);
                                wall_coordinates.get(i).setmWY(1000);
                                game_Boom--;
                            }else {
                                surfaceDestroyed(mSurfaceHolder);
                            }
                        }
                        else if (mPosY - wall_coordinates.get(i).getmWY() <= 50 && mPosY>wall_coordinates.get(i).getmWY()){
                            if (game_Boom>0){
                                wall_coordinates.get(i).setmWX(2000);
                                wall_coordinates.get(i).setmWY(1000);
                                game_Boom--;
                            }else {
                                surfaceDestroyed(mSurfaceHolder);
                            }
                        }
                    }
                }
            }

        }
    }

    public class Maze{
        private final static int dirUp = 0;
        private final static int dirRight = 1;
        private final static int dirDown = 2;
        private final static int dirLeft = 3;

        public final static int gridWall = 1;
        public final static int gridEmpty = 0;
        public final static int gridBlind = -1;
        public final static int gridPath = 2;

        private int width;
        private int height;
        private MazePoint[][] matrix;
        public int[][] maze;

        public Maze(int height , int width){
            this.width = width;
            this.height = height;
            this.matrix = new MazePoint[height][width];
            for (int i = 0 ; i < height ; i++){
                for (int j = 0 ; j < width ; j++){
                    matrix[i][j] = new MazePoint();
                }
            }
            this.maze = new int[2*height+1][2*width+1];
        }
        public boolean isNeighborOk(int x , int y , int dir){
            boolean isNeightborVisited = false;

            switch (dir){
                case dirUp:
                    if (x <= 0){
                        isNeightborVisited = true;
                    }else {
                        isNeightborVisited = matrix[x-1][y].isVisited();
                    }break;
                case dirRight:
                    if (y >= width-1){
                        isNeightborVisited = true;
                    }else {
                        isNeightborVisited = matrix[x][y+1].isVisited();
                    }break;
                case dirDown:
                    if (x >= height - 1){
                        isNeightborVisited = true;
                    }else {
                        isNeightborVisited = matrix[x+1][y].isVisited();
                    }break;
                case  dirLeft:
                    if (y <= 0){
                        isNeightborVisited = true;
                    }else {
                        isNeightborVisited = matrix[x][y-1].isVisited();
                    }break;
            }
            return !isNeightborVisited;
        }

        public boolean isNeighborOK(int x,int y){
            return (this.isNeighborOk(x,y,dirUp) || this.isNeighborOk(x,y,dirRight)
             || this.isNeighborOk(x,y,dirDown) || this.isNeighborOk(x,y,dirLeft));
        }

        public int getRandomDir(int x, int y){
            int dir = -1;
            Random random = new Random();
            if (isNeighborOK(x,y)){
                do {
                    dir = random.nextInt(4);
                }while (!isNeighborOk(x,y,dir));
            }
            return dir;
        }

        public void pusWall(int x , int y , int dir){
            switch (dir){
                case dirUp:
                    matrix[x][y].setWallUp(false);
                    matrix[x-1][y].setWallLeft(false);
                    break;
                case dirRight:
                    matrix[x][y].setWallRight(false);
                    matrix[x][y+1].setWallLeft(false);
                    break;
                case dirDown:
                    matrix[x][y].setWallDown(false);
                    matrix[x+1][y].setWallUp(false);
                    break;
                case dirLeft:
                    matrix[x][y].setWallLeft(false);
                    matrix[x][y-1].setWallRight(false);
                    break;
            }
        }

        public void traversal(){
            int x = 0 ;
            int y = 0 ;
            Stack<Integer> stackX = new Stack<Integer>();
            Stack<Integer> stackY = new Stack<Integer>();
            do {
                MazePoint mazePoint = matrix[x][y];
                if (!mazePoint.isVisited()){
                    mazePoint.setVisited(true);
                }
                if (isNeighborOK(x,y)){
                    int dir = this.getRandomDir(x,y);//获得随机可访问的方向
                    this.pusWall(x,y,dir); //设置通路
                    stackX.add(x);  //保存坐标信息
                    stackY.add(y);

                    switch (dir){
                        case dirUp:
                            x--;
                            break;
                        case dirRight:
                            y++;
                            break;
                        case dirDown:
                            x++;
                            break;
                        case dirLeft:
                            y--;
                            break;
                    }
                }
                else {
                    x = stackX.pop();
                    y = stackY.pop();
                }
            }while (!stackX.isEmpty());
        }

        public void create(){
            for (int j = 0 ; j <2*width+1 ; j++){
                maze[0][j] = gridWall;
            }
            for (int i = 0 ; i < height ; i++){
                maze[2*i+1][0] = gridWall;
                for (int j = 0 ; j < width ; j++){
                    maze[2*i+1][2*j+1] = gridEmpty;
                    if (matrix[i][j].isWallRight()){
                        maze[2*i+1][2*j+2] = gridWall;
                    }else {
                        maze[2*i+1][2*j+2] = gridEmpty;
                    }
                }
                maze[2*i+2][0] = 1;
                for (int j = 0 ; j <width ; j++){
                    if (matrix[i][j].isWallDown()){
                        maze[2*i+2][2*j+1] = gridWall;
                    }else {
                        maze[2*i+2][2*j+1] = gridEmpty;
                    }
                    maze[2*i+2][2*j+2] = gridWall;
                }
            }
        }

        public int getBreakOutDir(int x , int y){
            int dir = -1;
            if (maze[x][y+1] == 0){
                dir = dirRight;
            }else if (maze[x+1][y] == 0){
                dir = dirDown;
            }else if (maze[x][y-1] == 0){
                dir = dirLeft;
            }else if (maze[x-1][y] == 0){
                dir = dirUp;
            }
            return dir;
        }

        public void findPath(){
            int x = 1 ;
            int y = 1;
            Stack<Integer> stackX = new Stack<Integer>();
            Stack<Integer> stackY = new Stack<Integer>();
            do {
                int dir = this.getBreakOutDir(x,y);
                if (dir == -1){
                    maze[x][y] = gridBlind;
                    if(stackX != null && stackX.size() > 0){
                        x = stackX.pop();
                    }else {
                        Log.e("This is stack x error:","!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    }
                    if(stackY != null && stackY.size() > 0){
                        y = stackY.pop();
                    }else {
                        Log.e("This is stack y error:","!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    }


                }else {
                    maze[x][y] = gridPath;
                    stackX.add(x);
                    stackY.add(y);

                    switch (dir){
                        case dirUp:
                            x--;
                            break;
                        case dirRight:
                            y++;
                            break;
                        case dirDown:
                            x++;
                            break;
                        case dirLeft:
                            y--;
                            break;
                    }
                }
            }while (!(x <= 2*height-1 && y <= 2*width-1));
            maze[x][y] = gridPath;
        }
        public void reset(){
            for (int i = 0 ; i < 2*height+1 ; i++){
                for (int j = 0 ; j < 2*width+1 ; j++){
                    if (maze[i][j] != gridWall){
                        maze[i][j] = gridEmpty;
                    }
                }
            }
        }
    }
}
