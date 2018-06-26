package com.example.lenovo.auicideball.rendering;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.example.lenovo.auicideball.R;
import com.example.lenovo.auicideball.backstage.DrawView;

public class MainActivity_game1_page extends AppCompatActivity {

    public ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game1_page);
        constraintLayout = (ConstraintLayout) findViewById(R.id.root);

        final DrawView drawView = new DrawView(this);
        drawView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                drawView.currentX = event.getX();
                drawView.currentY = event.getY();
                drawView.invalidate();
                return true;
            }
        });
        constraintLayout.addView(drawView);
    }
}
