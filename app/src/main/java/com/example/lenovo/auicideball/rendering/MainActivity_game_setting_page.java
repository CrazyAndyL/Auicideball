package com.example.lenovo.auicideball.rendering;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.lenovo.auicideball.R;

public class MainActivity_game_setting_page extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{

    private Switch mSwitch4,mSwitch5,mSwitch6;
    private MediaPlayer mp;
    private SoundPool sp;
    int soundID1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game_setting_page);

        sp = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        soundID1 = sp.load(getApplicationContext(), R.raw.ls, 1);

        mSwitch4 = (Switch) findViewById(R.id.switch4);
        mSwitch5 = (Switch) findViewById(R.id.switch5);
        mSwitch6 = (Switch) findViewById(R.id.switch6);

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        mSwitch4.setOnCheckedChangeListener(this);
        mSwitch5.setOnCheckedChangeListener(this);
        mSwitch6.setOnCheckedChangeListener(this);
    }
        Vibrator vibrator;
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.switch4:
                if (isChecked){
                    if (mp!=null){
                        mp.release();
                    }
                    mp=MediaPlayer.create(MainActivity_game_setting_page.this,R.raw.yinyue);
                    mp.start();
                    mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {

                        }
                    });
                }else {
//                    mp.stop();
                }
                break;
            case R.id.switch5:
                if (isChecked){
                    sp.play(soundID1,0.8f,0.8f,1,0,1.0f);
                }else {
                    sp.stop(soundID1);
                }
                break;
            case R.id.switch6:
                if (isChecked){
                    vibrator.vibrate(300);
                }else {
                    vibrator.cancel();
                }
        }
    }
    @Override
    protected void onDestroy() {
        mp.stop();
        mp.release();
        super.onDestroy();
    }

}
