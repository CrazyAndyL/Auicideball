package com.example.lenovo.auicideball.rendering;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.lenovo.auicideball.R;
import com.example.lenovo.auicideball.backstage.User_data;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;

public class MainActivity_Rank_List_page extends AppCompatActivity {

    private TextView mRank_list_user_name;
    private TextView mRank_list_user_score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__rank__list_page);

        LitePal.getDatabase();
        User_data first = DataSupport.findFirst(User_data.class);

        mRank_list_user_name = (TextView) findViewById(R.id.User_name);
        mRank_list_user_name.setText(first.getUser_name());

        mRank_list_user_score = (TextView) findViewById(R.id.User_Score);
        mRank_list_user_score.setText(first.getScore()+"");
        //TODO

    }

}
