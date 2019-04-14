package com.tsj.mydemo.activity;

import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.Toast;

import com.tsj.mydemo.broadcastreceiver.NetBroadcastReceiver;
import com.tsj.mydemo.utils.ActivityUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * BaseActivity是所有Activity的基类
 * 把一些公共的方法放到里面，基础样式设置，权限封装，网络状态监听等
 *
 * **/

public abstract class BaseActivity extends AppCompatActivity implements NetBroadcastReceiver.NetChangeListener {

    public static final String TAG = "BaseActivity";
    private long exitTime = 0;

    private Unbinder unbinder;

    // 网络状态改变监听事件
    public static NetBroadcastReceiver.NetChangeListener mNetChangeListener;

    public static NetBroadcastReceiver netWorkStateReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNetChangeListener = this;

        //setActionBarStatus();

        // 添加到Activity工具类
        ActivityUtil.getInstance().addActivity(this);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //必须在加载布局(setContentView)之后使用
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerNetWorkReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unRegisterNetWorkReceiver();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbinder.unbind();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Activity销毁时，提示系统回收
        // System.gc();
        mNetChangeListener = null;
        // 移除Activity
        ActivityUtil.getInstance().removeActivity(this);
    }

    /**
     * 动态注册网络状态
     * Android 7.0之后把android.net.conn.CONNECTIVITY_CHANGE这个广播从静态注册给去掉了
     * 所以要在这里动态注册这个广播
     *
     * */
    private void registerNetWorkReceiver(){
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.LOLLIPOP){
            if(netWorkStateReceiver == null){
                netWorkStateReceiver = new NetBroadcastReceiver();
            }
            IntentFilter filter = new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(netWorkStateReceiver, filter);
        }
    }

    private void unRegisterNetWorkReceiver(){
        if(netWorkStateReceiver != null){
            unregisterReceiver(netWorkStateReceiver);
            netWorkStateReceiver = null;
        }
    }

    // 抽象 - 初始化方法，可以对数据进行初始化
    protected abstract void init();

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 点击手机上的返回键，返回上一层
        /*if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 移除Activity
            ActivityUtil.getInstance().removeActivity(this);
            this.finish();
        }*/

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 5000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_LONG).show();
                exitTime = System.currentTimeMillis();
            } else {
                ActivityUtil.getInstance().exitSystem();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onNetChange(boolean netWorkState) {
        //TODO
    }

    /**
     * 标题栏状态栏显示隐藏方法
     *
     * */
    private void setActionBarStatus(){
        // 隐藏标题栏
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        // 沉浸效果
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }
    /**
     * 更改字体大小
     * */
    private void changedTextSize(int size){
        Resources resources = this.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.fontScale = size;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }



    /**
     * 权限检查方法
     * @return false 代表没有该权限
     * @return true  代表有该权限
     */
    public boolean hasPermission(String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    /**
     * 权限请求方法
     * @param code 请求吗
     * @param permissions  权限组
     */
    public void requestPermission(int code, String... permissions) {
        ActivityCompat.requestPermissions(this, permissions, code);
    }

    /**
     * 处理请求权限结果事件
     *
     * @param requestCode  请求码
     * @param permissions  权限组
     * @param grantResults 结果集
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doRequestPermissionsResult(requestCode, grantResults);
    }

    /**
     * 处理请求权限结果事件
     *
     * @param requestCode  请求码
     * @param grantResults 结果集
     */
    public void doRequestPermissionsResult(int requestCode, @NonNull int[] grantResults) {
    }
}
