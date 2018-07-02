package com.example.lenovo.auicideball.rendering;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.auicideball.R;
import com.example.lenovo.auicideball.backstage.CacheActivity;
import com.example.lenovo.auicideball.backstage.DrawView;

import java.util.ArrayList;

public class MainActivity_game1_page extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game1_page);
        /*Activity加入缓存池内*/
        if (!CacheActivity.activityArrayList.contains(MainActivity_game1_page.this)){
            CacheActivity.addActivity(MainActivity_game1_page.this);
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        linearLayoutManager = new LinearLayoutManager(MainActivity_game1_page.this);
        mRecyclerView.setLayoutManager(linearLayoutManager);

    }

    public void Ada(ArrayList<String> _list){
        mAdapter = new RecyclerViewAdapter(_list);
        mRecyclerView.setAdapter(mAdapter);
    }

    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private ArrayList<String> List;

        public RecyclerViewAdapter(ArrayList<String> _list) {
            List = _list;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }


}
