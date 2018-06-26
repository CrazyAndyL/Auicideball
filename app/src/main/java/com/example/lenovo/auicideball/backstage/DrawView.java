package com.example.lenovo.auicideball.backstage;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class DrawView extends View {
    public float currentX = 50;
    public float currentY = 50;

    public DrawView(Context context) {
        super(context);
    }

    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        Paint mpaint = new Paint();
        mpaint.setColor(Color.RED);
        canvas.drawCircle(currentX,currentY,10,mpaint);
    }
}
