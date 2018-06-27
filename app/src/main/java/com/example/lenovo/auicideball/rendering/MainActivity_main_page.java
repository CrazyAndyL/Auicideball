package com.example.lenovo.auicideball.rendering;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.lenovo.auicideball.R;
import com.example.lenovo.auicideball.backstage.Remember_User;
import com.example.lenovo.auicideball.backstage.User_data;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;


public class MainActivity_main_page extends AppCompatActivity {
    private Button mStart_game_button;
    private Button mSetting_button;
    private ImageButton mImageButton;
    private Button mRank_list_Burron;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_mainpage);
        mStart_game_button = (Button) findViewById(R.id.start_game_button);
        mStart_game_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(MainActivity_main_page.this,MainActivity_game_page.class);
               startActivity(intent);
            }
        });
        LitePal.getDatabase();
        Remember_User remember_user = new Remember_User();
        remember_user.setUser_name("");
        remember_user.save();
        //TODO  判断是否第一次下载游戏 if yes 跳转注册页面
        Remember_User firstremember_users = DataSupport.findFirst(Remember_User.class);

            if (firstremember_users.getUser_name().equals("")){
                    Intent intent = new Intent(MainActivity_main_page.this,MainActivity_register_login_page.class);
                    startActivity(intent);
            }


        mRank_list_Burron = (Button) findViewById(R.id.rank_list_button);
        mRank_list_Burron.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity_main_page.this,MainActivity_Rank_List_page.class);
                startActivity(intent);
            }
        });


        mSetting_button = (Button) findViewById(R.id.setting_button);
        mSetting_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity_main_page.this,MainActivity_game_setting_page.class);
                startActivity(intent);
            }
        });

        mImageButton = (ImageButton) findViewById(R.id.Image_button);
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity_main_page.this,MainActivity_personal_information.class);
                startActivity(intent);
            }
        });


    }
}
