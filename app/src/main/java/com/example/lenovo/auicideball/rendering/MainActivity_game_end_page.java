package com.example.lenovo.auicideball.rendering;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.auicideball.R;
import com.example.lenovo.auicideball.backstage.Remember_User;
import com.example.lenovo.auicideball.backstage.User_data;

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

        mUser_score = (TextView) findViewById(R.id.User_Score);

        mgame_again_button = (Button)findViewById(R.id.game_again);

//        Remember_User first = DataSupport.findFirst(Remember_User.class);
//        List<User_data> user_datas = DataSupport.findAll(User_data.class);
//        for (User_data user_data:user_datas){
//            if (user_data.getUser_name().equals(first.getUser_name())){
////                mUser_name.setText();
//            }
//        }
    }
}
