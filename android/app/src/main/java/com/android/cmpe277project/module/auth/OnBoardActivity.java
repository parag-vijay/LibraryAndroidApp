package com.android.cmpe277project.module.auth;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.android.cmpe277project.R;
import com.android.cmpe277project.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnPageChange;

public class OnBoardActivity extends BaseActivity {

    @BindView(R.id.home_tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.home_viewpager)
    ViewPager viewpager;
    @BindView(R.id.onboard_progressBar)
    ProgressBar progressBar;
    @BindView(R.id.rootLayout)
    RelativeLayout rootLayout;

    private static int ONBOARD_PAGE_NUM = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFragments();
    }

    @OnPageChange(R.id.home_viewpager)
    public void onPageSelected(int position){
        ONBOARD_PAGE_NUM = position;
    }

    private void loadFragments() {
        viewpager.setAdapter(new LoginFragmentPagerAdpater(getSupportFragmentManager(), this));
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewpager);
            }
        });
        viewpager.setCurrentItem(ONBOARD_PAGE_NUM);
    }

}
