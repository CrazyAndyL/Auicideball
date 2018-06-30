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
    private Button mReturn_Button , mButton_Login;

    Vibrator vibrator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game_setting_page);

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        /*背景音乐*/
        mSwitch4 = (Switch) findViewById(R.id.switch4);
        mSwitch4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    //TODO
                }
            }
        });

        /*背景音效*/
        mSwitch5 = (Switch) findViewById(R.id.switch5);
        mSwitch5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //TODO
            }
        });

        /*震动模式*/
        mSwitch6 = (Switch) findViewById(R.id.switch6);
        mSwitch6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    SingleTon singleTon = SingleTon.getOurInstance();
                    singleTon.save(true);
                    vibrator.vibrate(300);
                }else {
                    SingleTon singleTon = SingleTon.getOurInstance();
                    singleTon.save(false);
                    vibrator.cancel();
                }

            }
        });

        /*返回主界面*/
        mReturn_Button = (Button) findViewById(R.id.Return_button);
        mReturn_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /*切换账号*/
        mButton_Login = (Button) findViewById(R.id.button_Login);
        mButton_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity_game_setting_page.this,MainActivity_Login_page.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
