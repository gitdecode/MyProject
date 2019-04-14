package com.tsj.mydemo.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.tsj.mydemo.R;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.textview)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        textView.setText("this is test");
    }

    @Override
    protected void init() {

    }

    @Override
    public void onNetChange(boolean netWorkState) {
        super.onNetChange(netWorkState);
    }
}
