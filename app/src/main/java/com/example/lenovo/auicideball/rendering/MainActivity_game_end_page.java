package com.example.lenovo.auicideball.rendering;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.auicideball.R;
import com.example.lenovo.auicideball.backstage.CacheActivity;
import com.example.lenovo.auicideball.backstage.Remember_User;
import com.example.lenovo.auicideball.backstage.SingleTon;
import com.example.lenovo.auicideball.backstage.User_data;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;

public class MainActivity_game_end_page extends AppCompatActivity {

    private ImageView mUser_imageView;
    private TextView mUser_name , mUser_score;
    private Button mreturn_button , mgame_again_button;

    private Handler mActivityHandler;

    public void  setHandler(Handler _handler){
        mActivityHandler = _handler;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game_end_page);

        if(!CacheActivity.activityArrayList.contains(MainActivity_game_end_page.this)){
            CacheActivity.addActivity(MainActivity_game_end_page.this);
        }

        mUser_imageView = (ImageView) findViewById(R.id.user_imageView);

        mUser_name = (TextView) findViewById(R.id.user_name);

        mUser_score = (TextView) findViewById(R.id.user_score);

        Remember_User first = DataSupport.findFirst(Remember_User.class);
        Bitmap bitmap = BitmapFactory.decodeFile(first.getHead_portrait());
        mUser_imageView.setImageBitmap(bitmap);
        mUser_name.setText(first.getUser_name());
//        mUser_score.setText(first.getScore()+"");
        Intent intent = getIntent();
        String score = intent.getStringExtra("score");
        mUser_score.setText(score);

        mgame_again_button = (Button)findViewById(R.id.game_again);
        mgame_again_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CacheActivity.finishSingleActivityByClass(MainActivity_game_page.class);
                Intent intent = new Intent(MainActivity_game_end_page.this,MainActivity_game_page.class);
                startActivity(intent);
                CacheActivity.finishSingleActivity(MainActivity_game_end_page.this);
            }
        });

        mreturn_button = (Button) findViewById(R.id.return_button);
        mreturn_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CacheActivity.finishSingleActivityByClass(MainActivity_game_page.class);
                Intent intent = new Intent(MainActivity_game_end_page.this,MainActivity_main_page.class);
                startActivity(intent);
                CacheActivity.finishSingleActivity(MainActivity_game_end_page.this);
            }
        });
    }
}
