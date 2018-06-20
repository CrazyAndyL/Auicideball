package com.example.lenovo.auicideball.rendering;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lenovo.auicideball.R;

public class MainActivity_Choose_game_page extends AppCompatActivity {

    private Button mGame_easy_button;
    private Button mGame_middle_button;
    private Button mGame_hard_button;
    private int game_easy = 1;
    private int game_middle = 2 ;
    private int game_hard = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_choose_game_page);

        mGame_easy_button = (Button) findViewById(R.id.game_easy_button);
        mGame_easy_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity_game_page.newIntent(MainActivity_Choose_game_page.this,game_easy);
                startActivity(intent);
            }
        });

        mGame_middle_button = (Button) findViewById(R.id.game_middle_button);
        mGame_middle_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity_game_page.newIntent(MainActivity_Choose_game_page.this,game_middle);
                startActivity(intent);
            }
        });
        mGame_hard_button = (Button) findViewById(R.id.game_hard_button);
        mGame_hard_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity_game_page.newIntent(MainActivity_Choose_game_page.this,game_hard);
                startActivity(intent);
            }
        });

    }
}
