package com.example.lenovo.auicideball.backstage;

public class SingleTon {

    boolean ischeck;
    private SingleTon(){}

    public static SingleTon getOurInstance(){
        return ourInstance;
    }
    private static final SingleTon ourInstance = new SingleTon();

    public void save (boolean _isCheck){
        ischeck = _isCheck;
    }

    public boolean getCheck(){
        return ischeck;
    }
}
