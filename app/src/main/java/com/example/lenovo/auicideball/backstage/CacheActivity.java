package com.example.lenovo.auicideball.backstage;

import android.app.Activity;

import java.util.ArrayList;

public class CacheActivity {
    public static ArrayList<Activity> activityArrayList = new ArrayList<Activity>();
    public CacheActivity(){}

    /*添加Activity到缓存池里*/
    public static void addActivity(Activity activity){
        if (!activityArrayList.contains(activity)){
            activityArrayList.add(activity);
        }
    }

    /*遍历所有的Activity并finish*/
    public static void finishActivity(){
        for (Activity activity : activityArrayList){
            activity.finish();
        }
    }

    /*结束指定的Activity*/
    public static void finishSingleActivity(Activity activity){
        if (activity !=null){
            if (activityArrayList.contains(activity)){
                activityArrayList.remove(activity);
            }
            activity.finish();
            activity = null;
        }
    }

    /*结束指定类名的Activity*/
    public static void finishSingleActivityByClass(Class<?> cls){
        Activity tempActivity = null;
        for (Activity activity : activityArrayList){
            if (activity.getClass().equals(cls)){
                tempActivity = activity;
            }
        }

        finishSingleActivity(tempActivity);
    }
}
