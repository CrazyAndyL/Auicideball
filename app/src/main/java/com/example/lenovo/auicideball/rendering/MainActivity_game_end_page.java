package com.example.lenovo.auicideball.rendering;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.auicideball.R;
import com.example.lenovo.auicideball.backstage.Remember_User;
import com.example.lenovo.auicideball.backstage.User_data;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;

public class MainActivity_game_end_page extends AppCompatActivity {

    private ImageView mUser_imageView;
    private TextView mUser_name , mUser_score;
    private Button mreturn_button , mgame_again_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game_end_page);

        mUser_imageView = (ImageView) findViewById(R.id.user_imageView);

        mUser_name = (TextView) findViewById(R.id.user_name);

        mUser_score = (TextView) findViewById(R.id.user_score);

        LitePal.getDatabase();
        Remember_User first = DataSupport.findFirst(Remember_User.class);
        Bitmap bitmap = BitmapFactory.decodeFile(first.getHead_portrait());
        mUser_imageView.setImageBitmap(bitmap);
        mUser_name.setText(first.getUser_name());
        mUser_score.setText(first.getScore()+"");
//        List<User_data> user_datas = DataSupport.findAll(User_data.class);
//        for (User_data user_data : user_datas){
//            if (user_data.getUser_name().equals(first.getUser_name())){
//                Remember_User remember_user = new Remember_User();
//                remember_user.setScore(user_data.getScore());
//                remember_user.updateAll("user_name = ?",first.getUser_name());
//            }
//        }

        mgame_again_button = (Button)findViewById(R.id.game_again);
        mgame_again_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity_game_end_page.this,MainActivity_game_page.class);
                startActivity(intent);
                finish();
            }
        });

        mreturn_button = (Button) findViewById(R.id.return_button);
        mreturn_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity_game_end_page.this,MainActivity_main_page.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
