package com.example.lenovo.auicideball.backstage;

public class SingleTon {

    boolean ischeck;

    boolean isFinish;

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

    public void saveFinish(boolean _isFinish){
        isFinish = _isFinish;
    }

    public boolean getFinish(){
        return isFinish;
    }
}
