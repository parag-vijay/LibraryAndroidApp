package com.android.cmpe277project.module.cart;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.android.cmpe277project.R;
import com.android.cmpe277project.base.BaseActivity;
import com.android.cmpe277project.model.Book;
import com.android.cmpe277project.model.Cart;
import com.android.cmpe277project.model.User;
import com.android.cmpe277project.module.util.Bakery;
import com.android.cmpe277project.service.patron.PatronPresenter;
import com.android.cmpe277project.service.patron.PatronPresenterImpl;
import com.android.cmpe277project.service.patron.PatronViewInteractor;
import com.android.cmpe277project.util.UserPreference;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnPageChange;

public class CartActivity extends BaseActivity implements PatronViewInteractor {

    @BindView(R.id.home_tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.home_viewpager)
    ViewPager viewpager;
    @BindView(R.id.onboard_progressBar)
    ProgressBar progressBar;
    @BindView(R.id.rootLayout)
    RelativeLayout rootLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private static int ONBOARD_PAGE_NUM = 0;
    private UserPreference userPreference;
    private PatronPresenter patronPresenter;
    private User user;
    private Bakery bakery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);

        bakery = new Bakery(this);
        patronPresenter = new PatronPresenterImpl(this);
        patronPresenter.attachViewInteractor(this);

        loadFragments();
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnPageChange(R.id.home_viewpager)
    public void onPageSelected(int position){
        ONBOARD_PAGE_NUM = position;
    }

    private void loadFragments() {
        viewpager.setAdapter(new CartFragmentPagerAdapter(getSupportFragmentManager(), this));
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewpager);
            }
        });
        viewpager.setCurrentItem(ONBOARD_PAGE_NUM);
    }

    public void renewBook(List<Cart> cartList) {
        patronPresenter.renewBook(cartList);
    }

    public void returnBook(List<Cart> cartList) {
        patronPresenter.returnBook(cartList);
    }

    public void issueBook(List<Cart> cartList) {
        patronPresenter.issueBook(cartList);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onSuccess(String message) {
        bakery.snackShort(rootLayout, message);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        },2000);
    }

    @Override
    public void onResult(List<Book> books) {

    }
}
