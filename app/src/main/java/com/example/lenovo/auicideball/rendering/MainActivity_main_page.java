package com.example.lenovo.auicideball.rendering;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.widget.Button;
import android.widget.ImageButton;

import com.example.lenovo.auicideball.R;

public class MainActivity_main_page extends AppCompatActivity {
    private Button mStart_game_button;
    private Button mSetting_button;
    private ImageButton mImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_mainpage);
        mStart_game_button = (Button) findViewById(R.id.start_game_button);
        mStart_game_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(MainActivity_main_page.this,MainActivity_Choose_game_page.class);
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
