package com.example.lenovo.auicideball.rendering;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.lenovo.auicideball.R;
import com.example.lenovo.auicideball.backstage.CacheActivity;
import com.example.lenovo.auicideball.backstage.Remember_User;
import com.example.lenovo.auicideball.backstage.User_data;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;


public class MainActivity_main_page extends AppCompatActivity {
    private Button mStart_game_button;
    private Button mSetting_button;
    private ImageButton mImageButton;
    private Button mRank_list_Burron;
    private RecyclerView mView;
    private List<Picture> mList=new ArrayList<Picture>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_mainpage);

        /*清除所有缓存池内的Activity*/
        CacheActivity.finishActivity();
        //绑定id
        bindID();
        //添加数据
        addData();
        //创建LinearLayoutManager
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        //设置
        mView.setLayoutManager(manager);
        //实例化适配器
        Picture_adapter myAdapter = new Picture_adapter(mList);
        //设置适配器
        mView.setAdapter(myAdapter);



        /*开始游戏*/
        mStart_game_button = (Button) findViewById(R.id.start_game_button);
        mStart_game_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(MainActivity_main_page.this,MainActivity_game_page.class);
               startActivity(intent);
               finish();
            }
        });

        /*调出数据库*/
        LitePal.getDatabase();
        Remember_User remember_user = new Remember_User();
        remember_user.setUser_name("");
        remember_user.save();
        /*判断是否第一次下载游戏 if yes 跳转注册页面*/
        Remember_User firstremember_users = DataSupport.findFirst(Remember_User.class);
        if (firstremember_users.getUser_name().equals("")){
            Intent intent = new Intent(MainActivity_main_page.this,MainActivity_register_login_page.class);
            startActivity(intent);
            finish();
        }

        /*查看排行榜*/
        mRank_list_Burron = (Button) findViewById(R.id.rank_list_button);
        mRank_list_Burron.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity_main_page.this,MainActivity_Rank_List_page.class);
                startActivity(intent);
            }
        });

        /*游戏设置*/
        mSetting_button = (Button) findViewById(R.id.setting_button);
        mSetting_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity_main_page.this,MainActivity_game_setting_page.class);
                startActivity(intent);
            }
        });

        /*查看个人主页信息*/
        mImageButton = (ImageButton) findViewById(R.id.Image_button);
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity_main_page.this,MainActivity_personal_information.class);
                startActivity(intent);
            }
        });

        //TODO 添加recyclerView 横向滑动显示图片
    }

    private void addData() {//TODO 添加图片
        for (int i = 0 ; i<1 ; i++){
            Picture itemVO = new Picture(R.drawable.checkpoint_0,"");
            mList.add(itemVO);
            itemVO = null;
            Picture itemV1=new Picture(R.drawable.game_checkpoint,"");
            mList.add(itemV1);
            itemV1 = null;
            Picture itemV2=new Picture(R.drawable.checkpoint_1,"");
            mList.add(itemV2);
            itemV2 = null;
            Picture itemV3=new Picture(R.drawable.checkpoint_2,"");
            mList.add(itemV3);
            itemV3=null;
            Picture itemV4=new Picture(R.drawable.checkpoint_3,"");
            mList.add(itemV4);
            itemV4=null;
            Picture itemV5=new Picture(R.drawable.checkpoint_0,"");
            mList.add(itemV5);
            itemV5=null;
            //添加到数组


        }
    }

    private void bindID() {
        mView = (RecyclerView) findViewById(R.id.recycler_view1);
    }
}
