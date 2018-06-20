package com.example.lenovo.auicideball.rendering;

import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.lenovo.auicideball.R;

public class MainActivity_game_setting_page extends AppCompatActivity{

    private Switch mSwitch4,mSwitch5,mSwitch6;

    Vibrator vibrator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game_setting_page);

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        mSwitch4 = (Switch) findViewById(R.id.switch4);  //背景音乐
        mSwitch4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    //TODO
                }
            }
        });


        mSwitch5 = (Switch) findViewById(R.id.switch5);  //背景音效
        mSwitch5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //TODO
            }
        });

        mSwitch6 = (Switch) findViewById(R.id.switch6);  //震动模式
        mSwitch6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    vibrator.vibrate(300);
                }else {
                    vibrator.cancel();
                }

            }
        });
    }

}
