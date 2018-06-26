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
import java.util.Random;
import java.util.Stack;

import static android.hardware.SensorManager.DATA_X;

public class MainActivity_game_page extends AppCompatActivity {

    MyView mAnimView = null;
    private int game_choose = 0 ;
    private static String str = "choose_game";
    public ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_gamepage);
//        constraintLayout = (ConstraintLayout) findViewById(R.id.root1);

        mAnimView = new MyView(this);
        setContentView(mAnimView);

//        mAnimView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                mAnimView.mPosX = event.getX();
//                mAnimView.mPosY = event.getY();
//                mAnimView.invalidate();
//                return true;
//            }
//        });
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//
//
//    }

    public static Intent newIntent(MainActivity_Choose_game_page mainActivity_choose_game_page, int game_choose) {
        Intent intent = new Intent(mainActivity_choose_game_page,MainActivity_game_page.class);
        intent.putExtra(str,game_choose);
        return intent;
    }



    public class MyView extends SurfaceView implements SurfaceHolder.Callback,Runnable,SensorEventListener{

        int Soced = 0;
        int Lightning = 0 ;
        int Boom = 0 ;
        int n = 0;

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

        /*小球撞墙的区域*/
        private int mWallWidth = 0;
        private int mWallHeight = 0;

        /*小球资源文件*/
        private Bitmap mbitmapBall;

        /*背景图片资源文件*/
        private Bitmap mbitmapBg;

//        /*闪电速度资源*/
//        private Bitmap mbitmapLightning;
//
//        /*速度值*/
//        float a = 2;
//
//        /*分数资源图片*/
//        private Bitmap mbitmapSoced;
//
//        /*地雷资源图片*/
//        private Bitmap mbitmapBoom;

        /*wall资源图片*/
        private Bitmap mbitmapWall;

        /*小球的坐标位置*/
        public float mPosX = 200;
        public float mPosY = 200;

//        /*分数数组*/
//        ArrayList<Score_coordinate> score_coordinates = new ArrayList<>();
//
//        /*闪电数组*/
//        ArrayList<Lightning_coordinate> lightning_coordinates = new ArrayList<>();
//
//        /*地雷数组*/
//        ArrayList<Boom_coordinate> boom_coordinates = new ArrayList<>();

        /*Wall数组*/
        ArrayList<Wall_coordinate> wall_coordinates = new ArrayList<>();

        /*随机数*/
        Random random = new Random();

        /*坐标可误差值*/
        private float mK= 50;

        /*游戏得分*/
//        int game_Score = 0;

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
//            /*加载地雷资源*/
//            mbitmapBoom = BitmapFactory.decodeResource(this.getResources(),R.drawable.boom);
//            /*闪电速度资源*/
//            mbitmapLightning = BitmapFactory.decodeResource(this.getResources(),R.drawable.lightning);
//            /*加载分数随机资源*/
//            mbitmapSoced = BitmapFactory.decodeResource(this.getResources(),R.drawable.scored);

            game_choose = getIntent().getIntExtra(str,game_choose);
            if (game_choose == 1){
                Soced = 10;
                Lightning = 10 ;
                Boom = 10 ;
            }
            else if (game_choose == 2){
                Soced = 10 ;
                Lightning = 10 ;
                Boom = 10 ;
            }
            else {
                Soced = 10;
                Lightning = 10 ;
                Boom = 10 ;
            }

//            /*添加分数*/
//            for(int i = 0 ; i < Soced ; i++){
//                Score_coordinate score_coordinate =new Score_coordinate();
//                score_coordinate.setmSX(random.nextInt(2000)%(2000-5+1) + 5);
//                score_coordinate.setmSY(random.nextInt(1000)%(1000-5+1) + 5);
//                score_coordinates.add(score_coordinate);
//            }
//
//            /*添加闪电*/
//            for (int j = 0 ; j < Lightning ; j++){
//                Lightning_coordinate lightning_coordinate = new Lightning_coordinate();
//                lightning_coordinate.setmLX(random.nextInt(2000)%(2000-5+1) + 5);
//                lightning_coordinate.setmLY(random.nextInt(1000)%(1000-5+1) + 5);
//                lightning_coordinates.add(lightning_coordinate);
//            }
//
//            /*添加地雷*/
//            for (int k = 0 ; k < Boom ; k++){
//                Boom_coordinate boom_coordinate = new Boom_coordinate();
//                boom_coordinate.setmBX(random.nextInt(2000)%(2000-5+1) + 5);
//                boom_coordinate.setmBY(random.nextInt(2000)%(2000-5+1) + 5);
//                boom_coordinates.add(boom_coordinate);
//            }

            /*得到SensorManage对象*/
            mSensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
            /*加速度传感器*/
            mSensor = mSensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            /*listener 的灵敏度 SENSOR_DELAY_GAME 游戏中的速度*/
            mSensorMgr.registerListener(this,mSensor,SensorManager.SENSOR_DELAY_UI);

            mazes.traversal();
            mazes.create();
            mazes.findPath();


        }

        private void Draw(){

            /*得到当前屏幕宽高*/
            mScreenWidth = this.getWidth();
            mScreenHeight = this.getHeight();
            /*背景*/
            mCanvas.drawBitmap(mbitmapBg,0,0,mPaint);

            /*绘制地图*/
            for (int i = 0 ; i < 2*height+1 ; i++){
                for (int j = 0 ; j < 2*width+1 ; j++){
                    if (mazes.maze[i][j] == mazes.gridWall){
                        mCanvas.drawBitmap(mbitmapWall,i*70,j*70,mPaint);
                        Wall_coordinate wall_coordinate = new Wall_coordinate();
                        wall_coordinate.setmWX(i*70);
                        wall_coordinate.setmWY(j*70);
                        wall_coordinates.add(wall_coordinate);

                    }
                }
            }
            mazes.reset();
//
//            /*描绘地雷*/
//            for (int k = 0 ; k < Boom ; k++){
//                mCanvas.drawBitmap(mbitmapBoom,boom_coordinates.get(k).getmBX(),
//                        boom_coordinates.get(k).getmBY(),
//                        mPaint);
//            }
//
//            /*描绘分数*/
//            for (int i = 0 ; i < Soced ; i++){
//                mCanvas.drawBitmap(mbitmapSoced,
//                        score_coordinates.get(i).getmSX(),
//                        score_coordinates.get(i).getmSY(),
//                        mPaint);
//            }
//
//            /*绘制闪电*/
//            for (int j = 0 ; j < Lightning ; j++){
//                mCanvas.drawBitmap(mbitmapLightning,
//                        lightning_coordinates.get(j).getmLX(),
//                        lightning_coordinates.get(j).getmLY(),
//                        mPaint);
//            }

            /*小球*/
            mCanvas.drawBitmap(mbitmapBall,mPosX,mPosY,mPaint);
//            mCanvas.drawCircle(mPosX,mPosY,15,mPaint);
//
//            /*小球误差范围内加速度*/
//            for (int j = 0 ; j < Lightning ; j++){
//                if (Math.abs((mPosX+30)-lightning_coordinates.get(j).getmLX())<=mK){
//                    if (Math.abs((mPosY+40)-lightning_coordinates.get(j).getmLY())<=mK){
//                        lightning_coordinates.get(j).setmLX(random.nextInt(mScreenWidth)%(mScreenWidth-5+1) + 5);
//                        lightning_coordinates.get(j).setmLY(random.nextInt(mScreenHeight)%(mScreenHeight-5+1) + 5);
//                        a= (float) (a+0.3);
//                        if (j%2 == 0){
//                            boom_coordinates.get(j).setmBX(random.nextInt(mScreenWidth)%(mScreenWidth-5+1) + 5);
//                            boom_coordinates.get(j).setmBY(random.nextInt(mScreenWidth)%(mScreenWidth-5+1) + 5);
//                            score_coordinates.get(j).setmSX(random.nextInt(mScreenWidth)%(mScreenWidth-5+1) + 5);
//                            score_coordinates.get(j).setmSY(random.nextInt(mScreenHeight)%(mScreenHeight-5+1) + 5);
//                        }
//
//                    }
//                }
//            }
////
//            /*小球误差值内加分*/
//            for (int i = 0 ; i<Soced ; i++){
//                if (Math.abs((mPosX+30)-score_coordinates.get(i).getmSX())<=mK){
//                    if (Math.abs((mPosY+40)-score_coordinates.get(i).getmSY())<=mK){
//                        score_coordinates.get(i).setmSX(random.nextInt(mScreenWidth)%(mScreenWidth-5+1) + 5);
//                        score_coordinates.get(i).setmSY(random.nextInt(mScreenHeight)%(mScreenHeight-5+1) + 5);
//                        game_Score++;
//                    }
//                }
//            }

            /*是否碰到wall*/
            if (mPosX < mScreenHeight*0.3){
                for (int i = 0 ; i < wall_coordinates.size()*0.3 ; i ++){
                    if (mPosX - wall_coordinates.get(i).getmWX() <= 50 && mPosX>wall_coordinates.get(i).getmWX()){
                        if (mPosY - wall_coordinates.get(i).getmWY() <= 50 && mPosY>wall_coordinates.get(i).getmWY()){
                            mIsRunning = false;
                        }
                        else if (wall_coordinates.get(i).getmWY() - mPosY <= 50 && mPosY<wall_coordinates.get(i).getmWY()){
                            mIsRunning = false;
                        }
                    }
                    if (wall_coordinates.get(i).getmWX() - mPosX <= 50 && mPosX<wall_coordinates.get(i).getmWX()){
                        if (wall_coordinates.get(i).getmWY() - mPosY <= 50 && mPosY<wall_coordinates.get(i).getmWY()){
                            mIsRunning = false;
                        }
                        else if (mPosY - wall_coordinates.get(i).getmWY() <= 50 && mPosY>wall_coordinates.get(i).getmWY()){
                            mIsRunning = false;
                        }
                    }
                }
            }else if (mPosX >= mScreenHeight*0.3 && mPosX < mScreenHeight*0.6){
                for (int i = (int) (wall_coordinates.size()*0.3); i < wall_coordinates.size()*0.6 ; i ++){
                    if (mPosX - wall_coordinates.get(i).getmWX() <= 50 && mPosX>wall_coordinates.get(i).getmWX()){
                        if (mPosY - wall_coordinates.get(i).getmWY() <= 50 && mPosY>wall_coordinates.get(i).getmWY()){
                            mIsRunning = false;
                        }
                        else if (wall_coordinates.get(i).getmWY() - mPosY <= 50 && mPosY<wall_coordinates.get(i).getmWY()){
                            mIsRunning = false;
                        }
                    }
                    if (wall_coordinates.get(i).getmWX() - mPosX <= 50 && mPosX<wall_coordinates.get(i).getmWX()){
                        if (wall_coordinates.get(i).getmWY() - mPosY <= 50 && mPosY<wall_coordinates.get(i).getmWY()){
                            mIsRunning = false;
                        }
                        else if (mPosY - wall_coordinates.get(i).getmWY() <= 50 && mPosY>wall_coordinates.get(i).getmWY()){
                            mIsRunning = false;
                        }
                    }
                }
            }else {
                for (int i = (int) (wall_coordinates.size()*0.6); i < wall_coordinates.size() ; i ++){
                    if (mPosX - wall_coordinates.get(i).getmWX() <= 50 && mPosX>wall_coordinates.get(i).getmWX()){
                        if (mPosY - wall_coordinates.get(i).getmWY() <= 50 && mPosY>wall_coordinates.get(i).getmWY()){
                            mIsRunning = false;
                        }
                        else if (wall_coordinates.get(i).getmWY() - mPosY <= 50 && mPosY<wall_coordinates.get(i).getmWY()){
                            mIsRunning = false;
                        }
                    }
                    if (wall_coordinates.get(i).getmWX() - mPosX <= 50 && mPosX<wall_coordinates.get(i).getmWX()){
                        if (wall_coordinates.get(i).getmWY() - mPosY <= 50 && mPosY<wall_coordinates.get(i).getmWY()){
                            mIsRunning = false;
                        }
                        else if (mPosY - wall_coordinates.get(i).getmWY() <= 50 && mPosY>wall_coordinates.get(i).getmWY()){
                            mIsRunning = false;
                        }
                    }
                }
            }
//            if (mPosX < mScreenHeight*0.3){
//                for (int i = 0 ; i < wall_coordinates.size()*0.3 ; i ++){
//                    if (mPosX - wall_coordinates.get(i).getmWX() <= 50 && mPosX>wall_coordinates.get(i).getmWX()){
//                        if (mPosY - wall_coordinates.get(i).getmWY() <= 50 && mPosY>wall_coordinates.get(i).getmWY()){
//                            mPosX = wall_coordinates.get(i).getmWX()+50;
//                        }
//                        else if (wall_coordinates.get(i).getmWY() - mPosY <= 50 && mPosY<wall_coordinates.get(i).getmWY()){
//                            mPosX = wall_coordinates.get(i).getmWX()+50;
//                        }
//                    }
//                    if (wall_coordinates.get(i).getmWX() - mPosX <= 50 && mPosX<wall_coordinates.get(i).getmWX()){
//                        if (wall_coordinates.get(i).getmWY() - mPosY <= 50 && mPosY<wall_coordinates.get(i).getmWY()){
//                            mPosX = wall_coordinates.get(i).getmWX()-50;
//                        }
//                        else if (mPosY - wall_coordinates.get(i).getmWY() <= 50 && mPosY>wall_coordinates.get(i).getmWY()){
//                            mPosX = wall_coordinates.get(i).getmWX()-50;
//                        }
//                    }
//                }
//                for (int j = 0 ; j < wall_coordinates.size()*0.3 ; j ++){
//                    if (mPosY - wall_coordinates.get(j).getmWY() <= 50 && mPosY>wall_coordinates.get(j).getmWY()){
//                        if (mPosX - wall_coordinates.get(j).getmWX() <= 50 && mPosX>wall_coordinates.get(j).getmWX()){
//                            mPosY = wall_coordinates.get(j).getmWY()+50;
//                        }
//                        else if (wall_coordinates.get(j).getmWX() - mPosX <=50 && mPosX<wall_coordinates.get(j).getmWX()){
//                            mPosY = wall_coordinates.get(j).getmWY()+50;
//                        }
//                    }
//                    if (wall_coordinates.get(j).getmWY() - mPosY <= 50 && mPosY<wall_coordinates.get(j).getmWY()){
//                        if (mPosX - wall_coordinates.get(j).getmWX() <= 50 && mPosX>wall_coordinates.get(j).getmWX()){
//                            mPosY = wall_coordinates.get(j).getmWY()-50;
//                        }
//                        else if (wall_coordinates.get(j).getmWX() - mPosX <=50 && mPosX<wall_coordinates.get(j).getmWX()){
//                            mPosY = wall_coordinates.get(j).getmWY()-50;
//                        }
//                    }
//                }
//
//            }else if(mPosX >= mScreenHeight*0.3 && mPosX < mScreenHeight*0.6) {
//                for (int i = (int) (wall_coordinates.size()*0.3); i < wall_coordinates.size()*0.6 ; i ++){
//
//                    if (mPosX - wall_coordinates.get(i).getmWX() <= 50 && mPosX>wall_coordinates.get(i).getmWX()){
//                        if (mPosY - wall_coordinates.get(i).getmWY() <= 50 && mPosY>wall_coordinates.get(i).getmWY()){
//                            mPosX = wall_coordinates.get(i).getmWX()+50;
//                        }
//                        else if (wall_coordinates.get(i).getmWY() - mPosY <= 50 && mPosY<wall_coordinates.get(i).getmWY()){
//                            mPosX = wall_coordinates.get(i).getmWX()+50;
//                        }
//                    }
//                    if (wall_coordinates.get(i).getmWX() - mPosX <= 50 && mPosX<wall_coordinates.get(i).getmWX()){
//                        if (wall_coordinates.get(i).getmWY() - mPosY <= 50 && mPosY<wall_coordinates.get(i).getmWY()){
//                            mPosX = wall_coordinates.get(i).getmWX()-50;
//                        }
//                        else if (mPosY - wall_coordinates.get(i).getmWY() <= 50 && mPosY>wall_coordinates.get(i).getmWY()){
//                            mPosX = wall_coordinates.get(i).getmWX()-50;
//                        }
//                    }
//                }
//
//                for (int j = (int) (wall_coordinates.size()*0.3) ; j < wall_coordinates.size()*0.6 ; j ++){
//                    if (mPosY - wall_coordinates.get(j).getmWY() <= 50 && mPosY>wall_coordinates.get(j).getmWY()){
//                        if (mPosX - wall_coordinates.get(j).getmWX() <= 50 && mPosX>wall_coordinates.get(j).getmWX()){
//                            mPosY = wall_coordinates.get(j).getmWY()+50;
//                        }
//                        else if (wall_coordinates.get(j).getmWX() - mPosX <=50 && mPosX<wall_coordinates.get(j).getmWX()){
//                            mPosY = wall_coordinates.get(j).getmWY()+50;
//                        }
//                    }
//                    if (wall_coordinates.get(j).getmWY() - mPosY <= 50 && mPosY<wall_coordinates.get(j).getmWY()){
//                        if (mPosX - wall_coordinates.get(j).getmWX() <= 50 && mPosX>wall_coordinates.get(j).getmWX()){
//                            mPosY = wall_coordinates.get(j).getmWY()-50;
//                        }
//                        else if (wall_coordinates.get(j).getmWX() - mPosX <=50 && mPosX<wall_coordinates.get(j).getmWX()){
//                            mPosY = wall_coordinates.get(j).getmWY()-50;
//                        }
//                    }
//                }
//            }else {
//                for (int i = (int) (wall_coordinates.size()*0.6); i < wall_coordinates.size() ; i ++){
//
//                    if (mPosX - wall_coordinates.get(i).getmWX() <= 50 && mPosX>wall_coordinates.get(i).getmWX()){
//                        if (mPosY - wall_coordinates.get(i).getmWY() <= 50 && mPosY>wall_coordinates.get(i).getmWY()){
//                            mPosX = wall_coordinates.get(i).getmWX()+50;
//                        }
//                        else if (wall_coordinates.get(i).getmWY() - mPosY <= 50 && mPosY<wall_coordinates.get(i).getmWY()){
//                            mPosX = wall_coordinates.get(i).getmWX()+50;
//                        }
//                    }
//                    if (wall_coordinates.get(i).getmWX() - mPosX <= 50 && mPosX<wall_coordinates.get(i).getmWX()){
//                        if (wall_coordinates.get(i).getmWY() - mPosY <= 50 && mPosY<wall_coordinates.get(i).getmWY()){
//
//                            mPosX = wall_coordinates.get(i).getmWX()-50;
//                        }
//                        else if (mPosY - wall_coordinates.get(i).getmWY() <= 50 && mPosY>wall_coordinates.get(i).getmWY()){
//                            mPosX = wall_coordinates.get(i).getmWX()-50;
//                        }
//                    }
//                }
//                for (int j = (int) (wall_coordinates.size()*0.6) ; j < wall_coordinates.size() ; j ++){
//                    if (mPosY - wall_coordinates.get(j).getmWY() <= 50 && mPosY>wall_coordinates.get(j).getmWY()){
//                        if (mPosX - wall_coordinates.get(j).getmWX() <= 50 && mPosX>wall_coordinates.get(j).getmWX()){
//                            mPosY = wall_coordinates.get(j).getmWY()+50;
//                        }
//                        else if (wall_coordinates.get(j).getmWX() - mPosX <=50 && mPosX<wall_coordinates.get(j).getmWX()){
//                            mPosY = wall_coordinates.get(j).getmWY()+50;
//                        }
//                    }
//                    if (wall_coordinates.get(j).getmWY() - mPosY <= 50 && mPosY<wall_coordinates.get(j).getmWY()){
//                        if (mPosX - wall_coordinates.get(j).getmWX() <= 50 && mPosX>wall_coordinates.get(j).getmWX()){
//                            mPosY = wall_coordinates.get(j).getmWY()-50;
//                        }
//                        else if (wall_coordinates.get(j).getmWX() - mPosX <=50 && mPosX<wall_coordinates.get(j).getmWX()){
//                            mPosY = wall_coordinates.get(j).getmWY()-50;
//                        }
//                    }
//                }
//
//            }


//            for (int i = 0 ; i < wall_coordinates.size() ; i ++){
//                if (mPosX - wall_coordinates.get(i).getmWX() <= 35 && mPosX>wall_coordinates.get(i).getmWX()){
//                    if (mPosY - wall_coordinates.get(i).getmWY() <= 35 && mPosY>wall_coordinates.get(i).getmWY()){
//                        mPosX = wall_coordinates.get(i).getmWX()+35;
//                    }
//                    else if (wall_coordinates.get(i).getmWY() - mPosY <= 30 && mPosY<wall_coordinates.get(i).getmWY()){
//                        mPosX = wall_coordinates.get(i).getmWX()+35;
//                    }
//                }
//                if (wall_coordinates.get(i).getmWX() - mPosX <= 30 && mPosX<wall_coordinates.get(i).getmWX()){
//                    if (wall_coordinates.get(i).getmWY() - mPosY <= 30 && mPosY<wall_coordinates.get(i).getmWY()){
//                        mPosX = wall_coordinates.get(i).getmWX()-30;
//                    }
//                    else if (mPosY - wall_coordinates.get(i).getmWY() <= 35 && mPosY>wall_coordinates.get(i).getmWY()){
//                        mPosX = wall_coordinates.get(i).getmWX()-30;
//                    }
//                }
//            }

            /*小球游戏结束*/
            if (mIsRunning == false){
                mCanvas.drawText("游戏结束！！！",500,200,mPaint);

            }

//            mCanvas.drawText("分数"+game_Score,20,50,mPaint);
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

//                /*小球吃闪电*/
//                for (int j = 0 ; j < Lightning ; j++){
//                    if (Math.abs((mPosX+30)-lightning_coordinates.get(j).getmLX())<=mK){
//                        if (Math.abs((mPosY+40)-lightning_coordinates.get(j).getmLY())<=mK){
//                            Draw();
//                        }
//                    }
//                }
//
//                /*小球得分*/
//                for (int i = 0 ; i<Soced ; i++){
//                    if (Math.abs((mPosX+30)-score_coordinates.get(i).getmSX())<=mK){
//                        if (Math.abs((mPosY+40)-score_coordinates.get(i).getmSY())<=mK){
//                           Draw();
//                        }
//                    }
//                }
//
//                /*小球吃雷*/
//                for (int k =  0 ; k < Boom ; k++){
//                    if (Math.abs((mPosX+30)-boom_coordinates.get(k).getmBX())<=mK){
//                        if (Math.abs((mPosY+40)-boom_coordinates.get(k).getmBY())<=mK){
//                            surfaceDestroyed(mSurfaceHolder);
////                            Draw();
//                        }
//                    }
//                }


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
            mPosX-=mGX * 1;
            mPosY+=mGY * 1;

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
