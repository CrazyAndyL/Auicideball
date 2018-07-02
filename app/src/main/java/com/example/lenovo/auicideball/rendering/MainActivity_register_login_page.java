package com.example.lenovo.auicideball.rendering;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lenovo.auicideball.R;
import com.example.lenovo.auicideball.backstage.CacheActivity;

public class MainActivity_register_login_page extends AppCompatActivity {

    private Button mRegister_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_register_login_page);

        if (!CacheActivity.activityArrayList.contains(MainActivity_register_login_page.this)){
            CacheActivity.addActivity(MainActivity_register_login_page.this);
        }

        mRegister_button = (Button) findViewById(R.id.Register_button);
        mRegister_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity_register_login_page.this,MainActivity_Register_page.class);
                startActivity(intent);
            }
        });
    }
}