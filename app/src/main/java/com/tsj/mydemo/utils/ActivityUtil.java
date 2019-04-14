package com.tsj.mydemo.utils;

import android.app.Activity;
import android.os.Process;

import java.util.ArrayList;
import java.util.List;
/**
 * activity工具类
 *
 * */
public class ActivityUtil {

    private List<Activity> activityList = new ArrayList<>();
    private static volatile ActivityUtil instance;

    // 单例模式中获取唯一的ExitApplication实例
    public static ActivityUtil getInstance() {
        if (null == instance) {
            synchronized (ActivityUtil.class){
                if(null ==instance){
                    instance = new ActivityUtil();
                }
            }
        }
        return instance;
    }

    // 添加Activity到容器中
    public void addActivity(Activity activity) {
        if (activityList == null)
            activityList = new ArrayList<>();
        activityList.add(activity);
    }

    // 移除Activity
    public void removeActivity(Activity activity) {
        if (activityList != null)
            activityList.remove(activity);
    }

    // 遍历所有Activity并finish
    public void exitSystem() {
        for (Activity activity : activityList) {
            if (activity != null)
                activity.finish();
        }
        // 退出进程
        Process.killProcess(Process.myPid());
        System.exit(0);
    }

}
