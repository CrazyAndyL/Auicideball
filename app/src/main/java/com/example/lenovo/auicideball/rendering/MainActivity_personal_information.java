package com.example.lenovo.auicideball.rendering;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.auicideball.R;

import org.w3c.dom.Text;

public class MainActivity_personal_information extends AppCompatActivity {

    private ImageView mUser_imageView;
    private TextView mUser_name;
    private ImageView mUser_game_imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_personal_information);

        mUser_imageView = (ImageView) findViewById(R.id.user_imageView);
        mUser_name = (TextView) findViewById(R.id.user_name);
        mUser_game_imageView = (ImageView) findViewById(R.id.user_game_imageView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }
//
//    public void exitbutton1(View v){
//        this.finish();
//    }
//
//    public void exitbutton0(View v){
//        this.finish();
//
//    }
}
