package com.example.lenovo.auicideball.rendering;

import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.lenovo.auicideball.R;
import com.example.lenovo.auicideball.backstage.SingleTon;

public class MainActivity_game_setting_page extends AppCompatActivity{

    private Switch mSwitch4,mSwitch5,mSwitch6;
    private Button mReturn_Button,mButton_login;

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
                finish();
            }
        });

        //登录界面
        mButton_login = (Button) findViewById(R.id.button_Login);
        mButton_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity_game_setting_page.this,MainActivity_Login_page.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
