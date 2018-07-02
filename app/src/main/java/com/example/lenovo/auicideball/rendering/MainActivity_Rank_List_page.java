package com.example.lenovo.auicideball.rendering;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lenovo.auicideball.R;
import com.example.lenovo.auicideball.backstage.CacheActivity;
import com.example.lenovo.auicideball.backstage.User_data;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;

public class MainActivity_Rank_List_page extends AppCompatActivity {

    private TextView mRank_list_user_name;
    private TextView mRank_list_user_score;
    private Button mExit_Button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__rank__list_page);
        /*Activity加入缓存池内*/
        if (!CacheActivity.activityArrayList.contains(MainActivity_Rank_List_page.this)){
            CacheActivity.addActivity(MainActivity_Rank_List_page.this);
        }
        //TODO 做一个recyclerView 竖向滑动

        LitePal.getDatabase();
        User_data first = DataSupport.findFirst(User_data.class);

        mRank_list_user_name = (TextView) findViewById(R.id.User_name);
        mRank_list_user_name.setText(first.getUser_name());

        mRank_list_user_score = (TextView) findViewById(R.id.User_Score);
        mRank_list_user_score.setText(first.getScore()+"");

        mExit_Button = (Button) findViewById(R.id.exit_button);
        mExit_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CacheActivity.finishSingleActivity(MainActivity_Rank_List_page.this);
            }
        });

    }

}