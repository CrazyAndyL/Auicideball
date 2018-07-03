package com.example.lenovo.auicideball.rendering;

public class Picture {
    private int mImg;
    private String mName;

    public Picture(int mImg, String mName) {
        this.mImg = mImg;
        this.mName = mName;
    }

    public void setmImg(int mImg) {
        this.mImg = mImg;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getmImg() {
        return mImg;
    }

    public String getmName() {
        return mName;
    }
}

