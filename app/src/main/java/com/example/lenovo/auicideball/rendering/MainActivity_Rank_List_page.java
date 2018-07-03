package com.example.lenovo.auicideball.rendering;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lenovo.auicideball.R;
import com.example.lenovo.auicideball.backstage.CacheActivity;
import com.example.lenovo.auicideball.backstage.User_data;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class MainActivity_Rank_List_page extends AppCompatActivity {

    private TextView mRank_list_user_name;
    private TextView mRank_list_user_score;
    private Button mExit_Button;

    private RecyclerView mView;
    private List<Picture> mList=new ArrayList<Picture>();
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

//        mRank_list_user_name = (TextView) findViewById(R.id.User_name);
//        mRank_list_user_name.setText(first.getUser_name());
//
//        mRank_list_user_score = (TextView) findViewById(R.id.User_Score);
//        mRank_list_user_score.setText(first.getScore()+"");

        mExit_Button = (Button) findViewById(R.id.exit_button);
        mExit_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CacheActivity.finishSingleActivity(MainActivity_Rank_List_page.this);
            }
        });

    }

    private void addData() {//TODO 添加图片
        for (int i = 0 ; i<1 ; i++){
            Picture itemVO = new Picture(R.drawable.ball,"");
            mList.add(itemVO);
            itemVO = null;
            Picture itemV1=new Picture(R.drawable.ball_skin,"");
            mList.add(itemV1);
            itemV1 = null;
            Picture itemV2=new Picture(R.drawable.bg,"");
            mList.add(itemV2);
            itemV2 = null;
            Picture itemV3=new Picture(R.drawable.boom,"");
            mList.add(itemV3);
            itemV3=null;
            //添加到数组


        }
    }

    private void bindID() {
        mView = (RecyclerView) findViewById(R.id.recycler_view);
    }

}