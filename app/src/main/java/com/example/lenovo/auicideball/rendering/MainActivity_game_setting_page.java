package com.example.lenovo.auicideball.rendering;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.lenovo.auicideball.R;
import com.example.lenovo.auicideball.backstage.CacheActivity;
import com.example.lenovo.auicideball.backstage.SingleTon;

public class MainActivity_game_setting_page extends AppCompatActivity{

    private Switch mSwitch4,mSwitch5,mSwitch6;
    private Button mReturn_Button,mButton_login;
    static boolean isPlay=true;
    private MediaPlayer mp;
    private SoundPool sp;
    int soundID1;

    Vibrator vibrator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game_setting_page);

        sp=new SoundPool(10, AudioManager.STREAM_MUSIC,0);
        soundID1=sp.load(getApplicationContext(),R.raw.ls,1);
        /*Activity加入缓存池内*/
        if (!CacheActivity.activityArrayList.contains(MainActivity_game_setting_page.this)){
            CacheActivity.addActivity(MainActivity_game_setting_page.this);
        }
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        PlayMusic();

        mSwitch4 = (Switch) findViewById(R.id.switch4);  //背景音乐
        mSwitch4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    if (isChecked){
                        if (isPlay==true){
                            if (mp!=null){
                                mp.stop();
                                isPlay=false;
                            }
                        }else {
                            PlayMusic();
                            isPlay=true;
                        }
                    }
                }
            }
        });


        mSwitch5 = (Switch) findViewById(R.id.switch5);  //背景音效
        mSwitch5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //TODO);
                if (isChecked){
                    sp.play(soundID1,0.8f,0.8f,1,0,1.0f);
                }else {
                    sp.stop(soundID1);
                }

    }
});
        mSwitch6 = (Switch) findViewById(R.id.switch6);  //震动模式
        mSwitch6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                /*单例*/
                SingleTon singleTon = SingleTon.getOurInstance();

                if (isChecked){
                    singleTon.save(true);
                    vibrator.vibrate(300);
                }else {
                    singleTon.save(true);
                    vibrator.cancel();
                }

            }
        });

        //退出按钮
        mReturn_Button = (Button) findViewById(R.id.Return_button);
        mReturn_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CacheActivity.finishSingleActivity(MainActivity_game_setting_page.this);
            }
        });

        //登录界面
        mButton_login = (Button) findViewById(R.id.button_Login);
        mButton_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity_game_setting_page.this,MainActivity_Login_page.class);
                startActivity(intent);
                CacheActivity.finishSingleActivity(MainActivity_game_setting_page.this);
            }
        });
    }

    private void PlayMusic() {
        mp=MediaPlayer.create(this,R.raw.yinyue);
        mp.setLooping(true);
        mp.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mp!=null){
            mp.stop();
            mp.release();
            mp=null;
        }
    }
}
