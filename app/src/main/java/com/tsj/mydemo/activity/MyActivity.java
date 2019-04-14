package com.tsj.mydemo.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tsj.mydemo.fragment.ClassifyFragment;
import com.tsj.mydemo.fragment.ContentFragment;
import com.tsj.mydemo.fragment.MoreFragment;
import com.tsj.mydemo.model.Tab;

import com.tsj.mydemo.R;

import java.util.ArrayList;
import java.util.List;

public class MyActivity extends FragmentActivity {

    private FragmentTabHost mTabHost;
    private LayoutInflater mInflater;
    private List<Tab> mTabs = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        initData();
    }

    private void initData(){
        mInflater = LayoutInflater.from(this);

        mTabHost = (FragmentTabHost) findViewById(R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        Tab tab_content = new Tab(R.string.tab_content_text, R.mipmap.ic_launcher, ContentFragment.class);
        Tab tab_classify = new Tab(R.string.tab_classify_text, R.mipmap.ic_launcher,
                ClassifyFragment.class);
        Tab tab_more = new Tab(R.string.tab_more_text, R.mipmap.ic_launcher,
                MoreFragment.class);

        mTabs.add(tab_content);
        mTabs.add(tab_classify);
        mTabs.add(tab_more);

        for (int i = 0; i < mTabs.size(); i++) {
            View view = mInflater.inflate(R.layout.fragment_tab, null);
            TextView tex = (TextView) view.findViewById(R.id.text_indicator);

            tex.setText(mTabs.get(i).getTitle());
            ImageView img = (ImageView) view.findViewById(R.id.imageView);
            img.setBackgroundResource(mTabs.get(i).getIcon());

            mTabHost.addTab(
                    mTabHost.newTabSpec(getString(mTabs.get(i).getTitle()))
                            .setIndicator(view), mTabs.get(i).getFragment(),
                    null);
        }
        mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        mTabHost.setCurrentTab(0);
    }
}
